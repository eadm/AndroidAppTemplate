package ru.nobird.template.remote.sample.mapper

import ru.nobird.template.remote.base.mapper.RemoteResultMapper
import ru.nobird.template.remote.sample.model.SampleResponse

object SampleResponseMapper : RemoteResultMapper<SampleResponse, String> {
    override fun tryMapData(data: SampleResponse): String =
        data.sampleVal
}