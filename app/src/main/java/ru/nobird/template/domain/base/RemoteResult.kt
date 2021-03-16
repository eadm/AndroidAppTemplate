package ru.nobird.template.domain.base

sealed class RemoteResult<out DataType> {

    data class Data<DataType>(val data: DataType) : RemoteResult<DataType>()

    data class Failure<DataType>(val subtype: Subtype, val details: String? = null) : RemoteResult<DataType>() {

        enum class Subtype {
            NO_CONNECTION,
            TEMPORARY_CONNECTIVITY_ERROR,
            NOT_AUTHORIZED,
            REJECTED,
            INVALID_SERVER_RESPONSE
        }
    }
}
