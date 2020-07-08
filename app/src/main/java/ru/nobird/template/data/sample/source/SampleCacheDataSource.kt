package ru.nobird.template.data.sample.source

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import ru.nobird.template.domain.sample.model.SampleEntry

interface SampleCacheDataSource {
    fun getSampleVal(): Maybe<String>
    fun saveSampleVal(sampleVal: String): Completable
    fun saveSampleEntries(data: List<SampleEntry>): Completable
    fun getSampleEntries(): Single<List<SampleEntry>>
    fun getSampleEntry(id: Long): Single<SampleEntry>
}
