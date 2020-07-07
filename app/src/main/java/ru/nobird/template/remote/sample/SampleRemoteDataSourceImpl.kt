package ru.nobird.template.remote.sample

import io.reactivex.Single
import ru.nobird.template.data.sample.source.SampleRemoteDataSource
import ru.nobird.template.remote.sample.model.SampleResponse
import ru.nobird.template.remote.sample.service.SampleService
import io.reactivex.functions.Function
import javax.inject.Inject

class SampleRemoteDataSourceImpl
@Inject
constructor(
    private val sampleService: SampleService
) : SampleRemoteDataSource {
    private val responseMapper =
        Function<SampleResponse, String>(SampleResponse::sampleVal)

    override fun getSampleVal(): Single<String> =
        sampleService
            .getSampleVal()
            .map(responseMapper)
}