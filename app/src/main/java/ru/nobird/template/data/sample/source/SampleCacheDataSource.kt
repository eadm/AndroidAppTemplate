package ru.nobird.template.data.sample.source

import io.reactivex.Completable
import io.reactivex.Maybe

interface SampleCacheDataSource {
    fun getSampleVal(): Maybe<String>
    fun saveSampleVal(sampleVal: String): Completable
}
