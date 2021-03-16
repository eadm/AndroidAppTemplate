package ru.nobird.template.remote.base

import ru.nobird.template.domain.base.RemoteResult
import ru.nobird.template.remote.base.mapper.RemoteResultMapper

open class RemoteDataSource {

    protected suspend fun <ServerDataType, ResultDataType> call(
        mapper: RemoteResultMapper<ServerDataType, ResultDataType>,
        remoteCall: suspend () -> ServerDataType
    ): RemoteResult<ResultDataType> =
        try {
            val serverResult = remoteCall()
            mapper.mapToRemoteResult(serverResult)
        } catch (e: Exception) {
            // you can check troubles here
            // for example
            RemoteResult.Failure(RemoteResult.Failure.Subtype.TEMPORARY_CONNECTIVITY_ERROR)
        }
}
