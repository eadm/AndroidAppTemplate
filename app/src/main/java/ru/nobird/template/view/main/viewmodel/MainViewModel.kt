package ru.nobird.template.view.main.viewmodel

import ru.nobird.android.presentation.redux.container.ReduxViewContainer
import ru.nobird.android.view.redux.viewmodel.ReduxViewModel
import ru.nobird.template.presentation.main.MainFeature

internal class MainViewModel(
    viewContainer: ReduxViewContainer<MainFeature.State, MainFeature.Message, MainFeature.Action.ViewAction>
) : ReduxViewModel<MainFeature.State, MainFeature.Message, MainFeature.Action.ViewAction>(viewContainer)
