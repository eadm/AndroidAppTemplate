package ru.nobird.template.remote.sample

import ru.nobird.template.data.sample.source.SampleRemoteDataSource
import ru.nobird.template.remote.sample.service.SampleService
import javax.inject.Inject

class SampleRemoteDataSourceImpl
@Inject
constructor(
    private val sampleService: SampleService,
) : SampleRemoteDataSource {

    override suspend fun getSampleVal(): String =
        sampleService.getSampleVal().sampleVal
}
