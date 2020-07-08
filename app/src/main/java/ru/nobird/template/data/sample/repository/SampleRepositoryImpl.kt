package ru.nobird.template.data.sample.repository

import io.reactivex.Completable
import io.reactivex.Single
import ru.nobird.android.domain.rx.doCompletableOnSuccess
import ru.nobird.template.data.sample.source.SampleCacheDataSource
import ru.nobird.template.data.sample.source.SampleRemoteDataSource
import ru.nobird.template.domain.base.DataSourceType
import ru.nobird.template.domain.sample.model.SampleEntry
import ru.nobird.template.domain.sample.repository.SampleRepository
import javax.inject.Inject

class SampleRepositoryImpl
@Inject
constructor(
    private val remoteDataSource: SampleRemoteDataSource,
    private val cacheDataSource: SampleCacheDataSource
) : SampleRepository {
    override fun getSampleVal(primaryDataSource: DataSourceType): Single<String> {
        val remoteSource = remoteDataSource
            .getSampleVal()
            .doCompletableOnSuccess(cacheDataSource::saveSampleVal)

        val cacheSource = cacheDataSource.getSampleVal()

        return when (primaryDataSource) {
            DataSourceType.CACHE ->
                cacheSource
                    .switchIfEmpty(remoteSource)

            DataSourceType.REMOTE ->
                remoteSource
                    .onErrorResumeNext(cacheSource.toSingle())
        }
    }

    override fun saveSampleEntries(data: List<SampleEntry>): Completable =
        cacheDataSource.saveSampleEntries(data)

    override fun getSampleEntries(): Single<List<SampleEntry>> =
        cacheDataSource.getSampleEntries()

    override fun getSampleEntry(id: Long): Single<SampleEntry> =
        cacheDataSource.getSampleEntry(id)
}
