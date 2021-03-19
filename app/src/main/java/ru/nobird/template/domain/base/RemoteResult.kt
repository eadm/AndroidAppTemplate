package ru.nobird.template.domain.base

sealed class RemoteResult<out DataType> {

    companion object {
        fun <DataType> invalidResponse(details: String? = null): Failure<DataType> =
            Failure(Failure.Subtype.INVALID_RESPONSE, details)
    }

    data class Data<DataType>(val data: DataType) : RemoteResult<DataType>()

    data class Failure<DataType>(
        val subtype: Subtype,
        val details: String? = null,
        val causedBy: Throwable? = null
    ) : RemoteResult<DataType>() {

        enum class Subtype {
            NO_CONNECTION,
            CONNECTION_ERROR,
            NOT_AUTHORIZED,
            REJECTED,
            INVALID_RESPONSE
        }
    }
}
