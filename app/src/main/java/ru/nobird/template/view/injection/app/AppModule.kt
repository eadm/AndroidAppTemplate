package ru.nobird.template.view.injection.app

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.chibatching.kotpref.PreferencesOpener
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.plus
import net.sqlcipher.database.SupportFactory
import ru.nobird.android.view.injection.base.presentation.DaggerViewModelFactory
import ru.nobird.template.BuildConfig
import ru.nobird.template.cache.base.Cryptographer
import ru.nobird.template.cache.common.AppDatabase
import ru.nobird.template.cache.common.RoomConstants
import ru.nobird.template.presentation.main.base.ActionDispatcherOptions
import ru.nobird.template.remote.base.model.Config
import ru.nobird.template.view.injection.qualifiers.AppSharedPreferences
import java.security.SecureRandom

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
        internal fun provideActionDispatcherOptions(): ActionDispatcherOptions =
            ActionDispatcherOptions(
                // Dispatchers.Main is a simplest (however not the very best) way to avoid concurrency
                GlobalScope + Dispatchers.Main,
                GlobalScope + Dispatchers.Main,
            )

        @Provides
        @ApplicationScope
        internal fun provideDataBase(
            context: Context,
            @AppSharedPreferences
            sharedPreferences: SharedPreferences,
            cryptographer: Cryptographer
        ): AppDatabase {
            var k = sharedPreferences.getString("d", null)
            if (k == null) {
                k = String(generateBytes())
                val encK = cryptographer.encryptBase64(k)
                sharedPreferences.edit { putString("d", encK) }
            } else {
                k = cryptographer.decryptBase64(k)
            }

            val builder = Room
                .databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    RoomConstants.DB_BANE
                )
                .fallbackToDestructiveMigration()

            if (!BuildConfig.DEBUG) {
                builder.openHelperFactory(SupportFactory(k.toByteArray()))
            }

            return builder.build()
        }

        private fun generateBytes(): ByteArray =
            ByteArray(128).also { SecureRandom().nextBytes(it) }

        @Provides
        @JvmStatic
        @AppSharedPreferences
        @ApplicationScope
        internal fun provideSharedPreferences(context: Context, preferencesOpener: PreferencesOpener): SharedPreferences =
            preferencesOpener.openPreferences(context, "app_preferences", Context.MODE_PRIVATE)

        @Provides
        @JvmStatic
        @ApplicationScope
        internal fun provideSharedPreferenceOpener(): PreferencesOpener =
            object : PreferencesOpener {
                override fun openPreferences(
                    context: Context,
                    name: String,
                    mode: Int
                ): SharedPreferences =
                    if (BuildConfig.DEBUG) {
                        context.getSharedPreferences(name, Context.MODE_PRIVATE)
                    } else {
                        EncryptedSharedPreferences.create(
                            context,
                            name,
                            getMasterKey(context),
                            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                        )
                    }
            }

        private fun getMasterKey(context: Context): MasterKey =
            MasterKey
                .Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()

        @Provides
        @JvmStatic
        internal fun provideCryptographer(context: Context): Cryptographer =
            Cryptographer(context)
    }
}
