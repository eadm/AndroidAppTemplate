package ru.nobird.template

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import ru.nobird.android.view.base.ui.extension.isMainProcess
import ru.nobird.template.view.injection.app.AppComponent
import ru.nobird.template.view.injection.app.ComponentManager
import ru.nobird.template.view.injection.app.DaggerAppComponent

class App : Application() {
    companion object {
        lateinit var application: App
            private set

        fun component(): AppComponent =
            application.component

        fun componentManager(): ComponentManager =
            application.componentManager
    }

    private lateinit var component: AppComponent
    private lateinit var componentManager: ComponentManager

    override fun onCreate() {
        super.onCreate()
        if (!isMainProcess) return

//        StethoHelper.initStetho(this)
        AndroidThreeTen.init(this)

        application = this
        component = DaggerAppComponent.builder()
            .context(application)
            .build()
        componentManager = ComponentManager(component)
    }
}
