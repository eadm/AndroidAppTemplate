package ru.nobird.template.view.injection.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import ru.nobird.template.remote.base.model.Config
import ru.nobird.template.view.injection.qualifiers.DebugInterceptors

@Module(includes = [NetworkUtilModule::class])
abstract class NetworkModule {
    @Module
    companion object {
        @Provides
        @JvmStatic
        fun provideRetrofit(
            config: Config,
            @DebugInterceptors
            debugInterceptors: Set<@JvmSuppressWildcards Interceptor>,
            jsonConverterFactory: Converter.Factory
        ): Retrofit {
            val okHttpBuilder = OkHttpClient.Builder()
            debugInterceptors.forEach { okHttpBuilder.addNetworkInterceptor(it) }

            return Retrofit.Builder()
                .baseUrl(config.baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(jsonConverterFactory)
                .client(okHttpBuilder.build())
                .build()
        }

        @Provides
        @JvmStatic
        fun provideJsonFactory(): Converter.Factory =
            Json { coerceInputValues = true } // todo add UTCAdapter
                .asConverterFactory(MediaType.get("application/json"))
    }
}
