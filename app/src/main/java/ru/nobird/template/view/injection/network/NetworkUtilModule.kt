package ru.nobird.template.view.injection.network

import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import ru.nobird.template.view.debug.DebugTools
import ru.nobird.template.view.injection.qualifiers.DebugInterceptors

@Module
abstract class NetworkUtilModule {
    @Module
    companion object {
        @Provides
        @JvmStatic
        @DebugInterceptors
        fun provideInterceptors(): List<Interceptor> =
            DebugTools.getDebugInterceptors()
    }
}
