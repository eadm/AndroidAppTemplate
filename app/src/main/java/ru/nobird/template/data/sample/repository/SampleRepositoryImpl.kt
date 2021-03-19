package ru.nobird.template.data.sample.repository

import ru.nobird.template.data.base.RepositoryDelegate
import ru.nobird.template.data.sample.source.SampleCacheDataSource
import ru.nobird.template.data.sample.source.SampleRemoteDataSource
import ru.nobird.template.domain.base.DataSourceType
import ru.nobird.template.domain.base.RemoteResult
import ru.nobird.template.domain.sample.model.SampleEntry
import ru.nobird.template.domain.sample.repository.SampleRepository
import javax.inject.Inject

class SampleRepositoryImpl
@Inject
constructor(
    private val remoteDataSource: SampleRemoteDataSource,
    private val cacheDataSource: SampleCacheDataSource
) : SampleRepository {

    val delegate = RepositoryDelegate<Unit, String>(
        { remoteDataSource.getSampleVal() },
        { cacheDataSource.getSampleVal() },
        { _, value -> cacheDataSource.saveSampleVal(value) }
    )

    override suspend fun getSampleVal(primaryDataSource: DataSourceType): RemoteResult<String> =
        delegate.get(Unit, primaryDataSource, true)

    override suspend fun saveSampleEntries(data: List<SampleEntry>) {
        cacheDataSource.saveSampleEntries(data)
    }

    override suspend fun getSampleEntries(): List<SampleEntry> =
        cacheDataSource.getSampleEntries()

    override suspend fun getSampleEntry(id: Long): SampleEntry? =
        cacheDataSource.getSampleEntry(id)
}
