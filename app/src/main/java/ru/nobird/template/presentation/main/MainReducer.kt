package ru.nobird.template.presentation.main

import javax.inject.Inject
import ru.nobird.app.presentation.redux.reducer.StateReducer
import ru.nobird.template.presentation.main.MainFeature.Action
import ru.nobird.template.presentation.main.MainFeature.Message
import ru.nobird.template.presentation.main.MainFeature.State

class MainReducer
@Inject
constructor() : StateReducer<State, Message, Action> {
    override fun reduce(state: State, message: Message): Pair<State, Set<Action>> =
        when (message) {
            is Message.Init ->
                state to setOf(Action.FetchSampleVal)
        }
}
