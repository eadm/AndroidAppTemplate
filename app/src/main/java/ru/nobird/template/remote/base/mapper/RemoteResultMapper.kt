package ru.nobird.template.remote.base.mapper

import ru.nobird.template.domain.base.RemoteResult

interface RemoteResultMapper<DataType, MappedDataType> {

    fun mapToRemoteResult(data: DataType): RemoteResult<MappedDataType> {
        val resultData = tryMapData(data)
        return resultData?.let { RemoteResult.Data(it) }
            ?: mapToFailure(data)
    }

    /**
     * @return null when [data] is invalid (can't be mapped to output result)
     */
    fun tryMapData(data: DataType): MappedDataType?

    fun mapToFailure(data: DataType): RemoteResult.Failure<MappedDataType> =
        RemoteResult.Failure(RemoteResult.Failure.Subtype.INVALID_RESPONSE)
}
