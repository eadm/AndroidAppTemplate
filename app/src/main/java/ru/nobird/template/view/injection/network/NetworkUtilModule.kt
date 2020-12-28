package ru.nobird.template.view.injection.network

import dagger.Module
import dagger.Provides
import dagger.multibindings.ElementsIntoSet
import okhttp3.Interceptor
import ru.nobird.template.view.debug.DebugTools
import ru.nobird.template.view.injection.qualifiers.DebugInterceptors

@Module
abstract class NetworkUtilModule {
    @Module
    companion object {
        @Provides
        @JvmStatic
        @ElementsIntoSet
        @DebugInterceptors
        fun provideInterceptors(): Set<Interceptor> =
            DebugTools.getDebugInterceptors()
    }
}
