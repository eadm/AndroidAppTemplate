package ru.nobird.template.presentation.main

interface MainFeature {
    sealed class State {
        object Idle : State()
    }

    sealed class Message {
        object Init : Message()
    }

    sealed class Action {
        object FetchSampleVal : Action()
        sealed class ViewAction : Action()
    }
}