package ru.nobird.template.remote.sample.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SampleResponse(
    @SerialName("sample")
    val sampleVal: String
)
