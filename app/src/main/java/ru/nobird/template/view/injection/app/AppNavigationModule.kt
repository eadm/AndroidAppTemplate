package ru.nobird.template.view.injection.app

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.Module
import dagger.Provides

@Module
internal class AppNavigationModule {
    private val cicerone: Cicerone<Router> = Cicerone.create()

    @ApplicationScope
    @Provides
    internal fun provideRouter(): Router =
        cicerone.router

    @ApplicationScope
    @Provides
    internal fun provideNavigatorHolder(): NavigatorHolder =
        cicerone.getNavigatorHolder()
}
