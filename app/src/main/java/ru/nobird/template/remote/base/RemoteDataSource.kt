package ru.nobird.template.remote.base

import ru.nobird.template.domain.base.RemoteResult

open class RemoteDataSource {

    protected suspend fun <T> call(
        remoteCall: suspend () -> T
    ): RemoteResult<T> =
        try {
            val serverResult = remoteCall()
            RemoteResult.Data(serverResult)
        } catch (e: Exception) {
            RemoteResult.Failure(e)
        }
}
