package ru.nobird.template.view.injection.network

import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import ru.nobird.template.view.injection.qualifiers.StethoInterceptor
import ru.nobird.template.view.util.StethoHelper

@Module
abstract class NetworkUtilModule {
    @Module
    companion object {
        @Provides
        @JvmStatic
        @StethoInterceptor
        fun provideStethoInterceptor(): Interceptor =
            StethoHelper.getInterceptor()
    }
}