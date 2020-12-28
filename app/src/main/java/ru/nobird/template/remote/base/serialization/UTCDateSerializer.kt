package ru.nobird.template.remote.base.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.jetbrains.annotations.Contract
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class UTCDateSerializer : KSerializer<Date> {
    companion object {
        private const val UTC_ISO_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
        private const val UTC_ISO_FORMAT_SIMPLE = "yyyy-MM-dd'T'HH:mm:ss"
        private const val UTC_ISO_FORMAT_SIMPLE_LENGTH = UTC_ISO_FORMAT_SIMPLE.length - 2 // exclude 2 single quotes around T

        private fun createDateFormat(pattern: String): SimpleDateFormat =
            SimpleDateFormat(pattern, Locale.getDefault())
                .apply {
                    timeZone = TimeZone.getTimeZone("UTC")
                }
    }

    private val serializeDateFormat = createDateFormat(UTC_ISO_FORMAT)
    private val deserializeDateFormat = createDateFormat(UTC_ISO_FORMAT_SIMPLE)

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("Date", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Date) {
        encoder.encodeString(requireNotNull(dateToString(value)))
    }

    override fun deserialize(decoder: Decoder): Date =
        requireNotNull(stringToDate(decoder.decodeString()))

    @Contract("null -> null; !null -> !null", pure = true)
    fun dateToString(date: Date?): String? =
        date?.let {
            synchronized(serializeDateFormat) {
                serializeDateFormat.format(it)
            }
        }

    @Contract("null -> null; !null -> !null", pure = true)
    fun stringToDate(date: String?): Date? =
        date?.let {
            synchronized(deserializeDateFormat) {
                deserializeDateFormat.parse(it.take(UTC_ISO_FORMAT_SIMPLE_LENGTH))
            }
        }
}
