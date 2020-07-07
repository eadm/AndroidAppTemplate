package ru.nobird.template.view.injection.main

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.nobird.android.view.injection.base.presentation.ViewModelKey
import ru.nobird.template.presentation.main.MainPresenter

@Module
abstract class MainModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainPresenter::class)
    internal abstract fun bindMainPresenter(mainPresenter: MainPresenter): ViewModel
}
