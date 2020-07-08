package ru.nobird.template.cache.sample

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import ru.nobird.template.cache.sample.dao.SampleDao
import ru.nobird.template.cache.sample.mapper.SampleDbMapper
import ru.nobird.template.data.sample.source.SampleCacheDataSource
import ru.nobird.template.domain.sample.model.SampleEntry
import javax.inject.Inject

class SampleCacheDataSourceImpl
@Inject
constructor(
    private val sampleStorage: SampleStorage,
    private val sampleDao: SampleDao
) : SampleCacheDataSource {
    override fun getSampleVal(): Maybe<String> =
        Maybe.fromCallable { sampleStorage.sampleVal }

    override fun saveSampleVal(sampleVal: String): Completable =
        Completable.fromAction {
            sampleStorage.sampleVal = sampleVal
        }

    override fun saveSampleEntries(data: List<SampleEntry>): Completable =
        sampleDao.save(data.map(SampleDbMapper::toDb))

    override fun getSampleEntries(): Single<List<SampleEntry>> =
        sampleDao.getAll().map(SampleDbMapper::fromDb)

    override fun getSampleEntry(id: Long): Single<SampleEntry> =
        sampleDao.getById(id).map(SampleDbMapper::fromDb)
}
