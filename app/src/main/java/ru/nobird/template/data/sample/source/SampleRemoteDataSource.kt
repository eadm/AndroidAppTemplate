package ru.nobird.template.data.sample.source

import io.reactivex.Single

interface SampleRemoteDataSource {
    fun getSampleVal(): Single<String>
}
