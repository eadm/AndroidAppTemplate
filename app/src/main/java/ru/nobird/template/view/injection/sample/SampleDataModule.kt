package ru.nobird.template.view.injection.sample

import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.nobird.template.cache.sample.SampleCacheDataSourceImpl
import ru.nobird.template.data.sample.repository.SampleRepositoryImpl
import ru.nobird.template.data.sample.source.SampleCacheDataSource
import ru.nobird.template.data.sample.source.SampleRemoteDataSource
import ru.nobird.template.domain.sample.repository.SampleRepository
import ru.nobird.template.remote.sample.SampleRemoteDataSourceImpl
import ru.nobird.template.remote.sample.service.SampleService

@Module
abstract class SampleDataModule {

    @Binds
    internal abstract fun bindSampleRepository(
        sampleRepositoryImpl: SampleRepositoryImpl
    ): SampleRepository

    @Binds
    internal abstract fun bindSampleRemoteDataSource(
        sampleRemoteDataSourceImpl: SampleRemoteDataSourceImpl
    ): SampleRemoteDataSource

    @Binds
    internal abstract fun bindSampleCacheDataSource(
        sampleCacheDataSourceImpl: SampleCacheDataSourceImpl
    ): SampleCacheDataSource

    @Module
    companion object {
        @JvmStatic
        @Provides
        fun provideSampleService(retrofit: Retrofit): SampleService =
            retrofit.create(SampleService::class.java)
    }
}
