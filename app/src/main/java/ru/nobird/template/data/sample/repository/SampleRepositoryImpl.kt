package ru.nobird.template.data.sample.repository

import ru.nobird.template.data.sample.source.SampleCacheDataSource
import ru.nobird.template.data.sample.source.SampleRemoteDataSource
import ru.nobird.template.domain.base.DataSourceType
import ru.nobird.template.domain.base.extension.doCompletableOnSuccess
import ru.nobird.template.domain.sample.repository.SampleRepository
import io.reactivex.Single
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
}