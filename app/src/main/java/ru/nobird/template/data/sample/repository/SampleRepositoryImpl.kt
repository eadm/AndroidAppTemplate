package ru.nobird.template.data.sample.repository

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
    override suspend fun getSampleVal(primaryDataSource: DataSourceType): RemoteResult<String> =
        when (primaryDataSource) {
            DataSourceType.CACHE -> {
                cacheDataSource.getSampleVal()?.let { RemoteResult.Data(it) }
                    ?: remoteDataSource.getSampleVal()
            }

            DataSourceType.REMOTE -> {
                val remoteResult = remoteDataSource.getSampleVal()
                when (remoteResult) {
                    is RemoteResult.Data -> remoteResult
                    is RemoteResult.Failure -> {
                        when (remoteResult.subtype) {
                            RemoteResult.Failure.Subtype.NO_CONNECTION,
                                RemoteResult.Failure.Subtype.TEMPORARY_CONNECTIVITY_ERROR,
                                RemoteResult.Failure.Subtype.INVALID_SERVER_RESPONSE -> {
                                    cacheDataSource.getSampleVal()?.let { RemoteResult.Data(it) }
                                        ?: remoteResult
                                }

                            RemoteResult.Failure.Subtype.NOT_AUTHORIZED,
                                RemoteResult.Failure.Subtype.REJECTED -> remoteResult
                        }
                    }
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
