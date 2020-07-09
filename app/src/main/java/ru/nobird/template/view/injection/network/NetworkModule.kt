package ru.nobird.template.view.injection.network

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.nobird.template.remote.base.model.Config
import ru.nobird.template.remote.base.serialization.UTCDateAdapter
import ru.nobird.template.view.injection.qualifiers.DebugInterceptors
import java.util.Date

@Module(includes = [NetworkUtilModule::class])
abstract class NetworkModule {
    @Module
    companion object {
        @Provides
        @JvmStatic
        fun provideRetrofit(
            config: Config,
            @DebugInterceptors
            debugInterceptors: List<@JvmSuppressWildcards Interceptor>,
            gsonConverterFactory: GsonConverterFactory
        ): Retrofit {
            val okHttpBuilder = OkHttpClient.Builder()
            debugInterceptors.forEach { okHttpBuilder.addNetworkInterceptor(it) }

            return Retrofit.Builder()
                .baseUrl(config.baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(gsonConverterFactory)
                .client(okHttpBuilder.build())
                .build()
        }

        @Provides
        @JvmStatic
        fun provideJsonFactory(): GsonConverterFactory =
            GsonBuilder()
                .registerTypeAdapter(Date::class.java, UTCDateAdapter())
                .create()
                .let(GsonConverterFactory::create)
    }
}
