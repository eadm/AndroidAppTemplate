package ru.nobird.template.data.sample.repository

import javax.inject.Inject
import ru.nobird.template.data.sample.source.SampleCacheDataSource
import ru.nobird.template.data.sample.source.SampleRemoteDataSource
import ru.nobird.template.domain.base.DataSourceType
import ru.nobird.template.domain.sample.model.SampleEntry
import ru.nobird.template.domain.sample.repository.SampleRepository

class SampleRepositoryImpl
@Inject
constructor(
    private val remoteDataSource: SampleRemoteDataSource,
    private val cacheDataSource: SampleCacheDataSource
) : SampleRepository {

    override suspend fun getSampleVal(primaryDataSource: DataSourceType): String =
        when (primaryDataSource) {
            DataSourceType.CACHE -> {
                cacheDataSource.getSampleVal()
                    ?: remoteDataSource.getSampleVal()
            }

            DataSourceType.REMOTE -> {
                try {
                    remoteDataSource.getSampleVal()
                } catch (e: Exception) {
                    cacheDataSource.getSampleVal()
                        ?: throw e
                }
            }
        }

    override suspend fun saveSampleEntries(data: List<SampleEntry>) {
        cacheDataSource.saveSampleEntries(data)
    }

    override suspend fun getSampleEntries(): List<SampleEntry> =
        cacheDataSource.getSampleEntries()

    override suspend fun getSampleEntry(id: Long): SampleEntry? =
        cacheDataSource.getSampleEntry(id)
}
