package ru.nobird.template.remote.sample.service

import io.reactivex.Single
import retrofit2.http.GET
import ru.nobird.template.remote.sample.model.SampleResponse

interface SampleService {
    @GET("sample_url")
    fun getSampleVal(): Single<SampleResponse>
}