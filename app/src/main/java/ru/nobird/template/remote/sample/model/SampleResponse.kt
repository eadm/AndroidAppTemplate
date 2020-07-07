package ru.nobird.template.remote.sample.model

import com.google.gson.annotations.SerializedName

data class SampleResponse(
    @SerializedName("sample")
    val sampleVal: String
)