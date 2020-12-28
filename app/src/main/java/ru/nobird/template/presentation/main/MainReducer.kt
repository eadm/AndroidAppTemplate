package ru.nobird.template.presentation.main

import ru.nobird.android.presentation.redux.reducer.StateReducer
import ru.nobird.template.presentation.main.MainFeature.State
import ru.nobird.template.presentation.main.MainFeature.Message
import ru.nobird.template.presentation.main.MainFeature.Action
import javax.inject.Inject

class MainReducer
@Inject
constructor(): StateReducer<State, Message, Action> {
    override fun reduce(state: State, message: Message): Pair<State, Set<Action>> =
        when (message) {
            is Message.Init ->
                state to setOf(Action.FetchSampleVal)
        }
}