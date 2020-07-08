package ru.nobird.template.view.injection.network

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.nobird.template.remote.base.model.Config
import ru.nobird.template.remote.base.serialization.UTCDateAdapter
import java.util.Date

@Module(includes = [NetworkUtilModule::class])
abstract class NetworkModule {
    @Module
    companion object {
        @Provides
        @JvmStatic
        fun provideRetrofit(
            config: Config,
//            @StethoInterceptor
//            stethoInterceptor: Interceptor,
            gsonConverterFactory: GsonConverterFactory
        ): Retrofit {
            val okHttpClient = OkHttpClient.Builder()
//                .addNetworkInterceptor(stethoInterceptor)
                .build()
            return Retrofit.Builder()
                .baseUrl(config.baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(gsonConverterFactory)
                .client(okHttpClient)
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
