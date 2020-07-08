package ru.nobird.template.view.injection.app

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.nobird.android.view.injection.base.RxScheduler
import ru.nobird.android.view.injection.base.presentation.DaggerViewModelFactory
import ru.nobird.template.cache.common.AppDatabase
import ru.nobird.template.cache.common.RoomConstants
import ru.nobird.template.remote.base.model.Config
import ru.nobird.template.view.injection.qualifiers.AppSharedPreferences

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
        @RxScheduler.Main
        internal fun provideAndroidScheduler(): Scheduler =
            AndroidSchedulers.mainThread()

        @Provides
        @JvmStatic
        @RxScheduler.Background
        internal fun provideBackgroundScheduler(): Scheduler =
            Schedulers.io()

        @Provides
        @JvmStatic
        @AppSharedPreferences
        internal fun provideSharedPreferences(context: Context): SharedPreferences =
            context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)

        @Provides
        internal fun provideDataBase(context: Context): AppDatabase =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                RoomConstants.DB_BANE
            )
                .fallbackToDestructiveMigration()
                .build()
    }
}
