package ru.nobird.template.domain.base

sealed class RemoteResult<out DataType> {

    data class Data<DataType>(val data: DataType) : RemoteResult<DataType>()

    data class Failure<DataType>(
        val exception: Exception
    ) : RemoteResult<DataType>()
}
