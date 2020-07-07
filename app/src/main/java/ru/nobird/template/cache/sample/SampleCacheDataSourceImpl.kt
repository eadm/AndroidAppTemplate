package ru.nobird.template.cache.sample

import ru.nobird.template.data.sample.source.SampleCacheDataSource
import io.reactivex.Completable
import io.reactivex.Maybe
import javax.inject.Inject

class SampleCacheDataSourceImpl
@Inject
constructor(
    private val sampleStorage: SampleStorage
) : SampleCacheDataSource {
    override fun getSampleVal(): Maybe<String> =
        Maybe.fromCallable { sampleStorage.sampleVal }

    override fun saveSampleVal(sampleVal: String): Completable =
        Completable.fromAction {
            sampleStorage.sampleVal = sampleVal
        }
}