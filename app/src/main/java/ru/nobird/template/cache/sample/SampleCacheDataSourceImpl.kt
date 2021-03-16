package ru.nobird.template.cache.sample

import ru.nobird.template.cache.sample.dao.SampleDao
import ru.nobird.template.cache.sample.mapper.SampleDbMapper
import ru.nobird.template.data.sample.source.SampleCacheDataSource
import ru.nobird.template.domain.sample.model.SampleEntry
import javax.inject.Inject

class SampleCacheDataSourceImpl
@Inject
constructor(
    private val sampleStorage: SampleStorage,
    private val sampleDao: SampleDao,
    private val mapper: SampleDbMapper
) : SampleCacheDataSource {
    override suspend fun getSampleVal(): String? =
        sampleStorage.sampleVal

    override suspend fun saveSampleVal(sampleVal: String) {
        sampleStorage.sampleVal = sampleVal
    }

    override suspend fun saveSampleEntries(data: List<SampleEntry>) {
        sampleDao.save(data.map(mapper::toDb))
    }

    override suspend fun getSampleEntries(): List<SampleEntry> =
        sampleDao.getAll().map(mapper::fromDb)

    override suspend fun getSampleEntry(id: Long): SampleEntry =
        mapper.fromDb(sampleDao.getById(id))
}
