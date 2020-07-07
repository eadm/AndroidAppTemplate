package ru.nobird.template.view.injection.app

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.nobird.template.view.injection.main.MainComponent
import ru.nobird.template.view.injection.network.NetworkModule

@ApplicationScope
@Component(
    modules = [
        AppModule::class,
        AppNavigationModule::class,
        NetworkModule::class
    ]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        fun build(): AppComponent

        @BindsInstance
        fun context(context: Context): Builder
    }

    fun mainComponentBuilder(): MainComponent.Builder
}