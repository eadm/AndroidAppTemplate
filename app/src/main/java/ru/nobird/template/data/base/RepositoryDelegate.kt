package ru.nobird.template.data.base

import ru.nobird.template.domain.base.DataSourceType
import ru.nobird.template.domain.base.RemoteResult

class RepositoryDelegate<CallArgument, Item>(
    private val remoteCall: suspend (CallArgument) -> RemoteResult<Item>,
    private val cacheRead: suspend (CallArgument) -> Item?,
    private val cacheWrite: suspend (CallArgument, Item) -> Unit
) {
    suspend fun get(
        callArgument: CallArgument,
        primarySourceType: DataSourceType,
        allowFallback: Boolean
    ): RemoteResult<Item> =
        when (primarySourceType) {
            DataSourceType.CACHE -> {
                cacheRead(callArgument)?.let { RemoteResult.Data(it) }
                    ?: if (allowFallback) {
                        get(callArgument, DataSourceType.REMOTE, false)
                    } else {
                        RemoteResult.invalidResponse(null)
                    }
            }

            DataSourceType.REMOTE -> {
                val remoteResult = remoteCall(callArgument)
                when (remoteResult) {
                    is RemoteResult.Data -> {
                        cacheWrite(callArgument, remoteResult.data)
                        remoteResult
                    }

                    is RemoteResult.Failure -> {
                        when (remoteResult.subtype) {
                            RemoteResult.Failure.Subtype.NO_CONNECTION,
                            RemoteResult.Failure.Subtype.CONNECTION_ERROR,
                            RemoteResult.Failure.Subtype.INVALID_RESPONSE -> {
                                if (allowFallback) {
                                    val result = get(callArgument, DataSourceType.CACHE, false)
                                    when (result) {
                                        is RemoteResult.Data -> result
                                        is RemoteResult.Failure -> remoteResult
                                    }
                                } else {
                                    remoteResult
                                }
                            }

                            RemoteResult.Failure.Subtype.NOT_AUTHORIZED,
                            RemoteResult.Failure.Subtype.REJECTED -> remoteResult
                        }
                    }
                }
            }
        }
}
