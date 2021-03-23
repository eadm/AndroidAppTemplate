package ru.nobird.template.remote.sample

import ru.nobird.template.data.sample.source.SampleRemoteDataSource
import ru.nobird.template.domain.base.RemoteResult
import ru.nobird.template.remote.base.RemoteDataSource
import ru.nobird.template.remote.sample.service.SampleService
import javax.inject.Inject

class SampleRemoteDataSourceImpl
@Inject
constructor(
    private val sampleService: SampleService,
) : RemoteDataSource(), SampleRemoteDataSource {

    override suspend fun getSampleVal(): RemoteResult<String> =
        call {
            sampleService.getSampleVal().sampleVal
        }
}
