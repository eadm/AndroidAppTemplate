package ru.nobird.template.view.injection.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import ru.nobird.template.remote.base.model.Config
import ru.nobird.template.remote.base.serialization.UTCDateSerializer
import ru.nobird.template.view.injection.app.ApplicationScope
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
            json: Json
        ): Retrofit {
            val okHttpBuilder = OkHttpClient.Builder()
            debugInterceptors.forEach { okHttpBuilder.addNetworkInterceptor(it) }

            return Retrofit.Builder()
                .baseUrl(config.baseUrl)
                .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
                .client(okHttpBuilder.build())
                .build()
        }

        @Provides
        @JvmStatic
        @ApplicationScope
        fun provideJson(): Json =
            Json {
                coerceInputValues = true
                ignoreUnknownKeys = true
                serializersModule = SerializersModule {
                    contextual(UTCDateSerializer())
                }
            }
    }
}
