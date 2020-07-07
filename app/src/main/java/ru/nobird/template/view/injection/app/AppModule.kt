package ru.nobird.template.view.injection.app

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import ru.nobird.template.remote.base.model.Config
import ru.nobird.template.view.injection.qualifiers.AppSharedPreferences
import ru.nobird.template.view.injection.qualifiers.BackgroundScheduler
import ru.nobird.template.view.injection.qualifiers.MainScheduler
import dagger.Binds
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.nobird.android.view.injection.base.presentation.DaggerViewModelFactory

@Module
abstract class AppModule {
    @Binds
    internal abstract fun bindViewModelFactory(daggerViewModelFactory: DaggerViewModelFactory): ViewModelProvider.Factory

    @Module
    companion object {

        @Provides
        @JvmStatic
        internal fun provideConfig(): Config =
            Config(
                baseUrl = "http://ysport.sprojects.xyz:8400/api/"
            )

        @Provides
        @JvmStatic
        @MainScheduler
        internal fun provideAndroidScheduler(): Scheduler =
            AndroidSchedulers.mainThread()

        @Provides
        @JvmStatic
        @BackgroundScheduler
        internal fun provideBackgroundScheduler(): Scheduler =
            Schedulers.io()

        @Provides
        @JvmStatic
        @AppSharedPreferences
        internal fun provideSharedPreferences(context: Context): SharedPreferences =
            context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
    }
}