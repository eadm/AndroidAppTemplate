package ru.nobird.template.view.injection.app

import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router

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
        cicerone.navigatorHolder
}