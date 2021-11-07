package ru.nobird.template.remote.sample

import javax.inject.Inject
import ru.nobird.template.data.sample.source.SampleRemoteDataSource
import ru.nobird.template.remote.sample.service.SampleService

class SampleRemoteDataSourceImpl
@Inject
constructor(
    private val sampleService: SampleService,
) : SampleRemoteDataSource {

    override suspend fun getSampleVal(): String =
        sampleService.getSampleVal().sampleVal
}
