package ru.nobird.template.view.injection.main

import androidx.lifecycle.ViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import ru.nobird.app.presentation.redux.container.wrapWithViewContainer
import ru.nobird.app.presentation.redux.dispatcher.wrapWithActionDispatcher
import ru.nobird.app.presentation.redux.feature.ReduxFeature
import ru.nobird.android.view.injection.base.presentation.ViewModelKey
import ru.nobird.template.presentation.main.MainActionDispatcher
import ru.nobird.template.presentation.main.MainFeature
import ru.nobird.template.presentation.main.MainReducer
import ru.nobird.template.view.main.viewmodel.MainViewModel

@Module
internal object MainModule {
    @Provides
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal fun provideMainViewModel(
        reducer: MainReducer,
        actionDispatcher: MainActionDispatcher
    ): ViewModel =
        MainViewModel(
            ReduxFeature(MainFeature.State.Idle, reducer)
                .wrapWithActionDispatcher(actionDispatcher)
                .wrapWithViewContainer()
        )
}
