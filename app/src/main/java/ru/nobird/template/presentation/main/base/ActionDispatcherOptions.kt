package ru.nobird.template.presentation.main.base

import kotlinx.coroutines.CoroutineScope
import ru.nobird.android.presentation.redux.dispatcher.CoroutineActionDispatcher

class ActionDispatcherOptions(
    override val actionScope: CoroutineScope,
    override val messageScope: CoroutineScope
) : CoroutineActionDispatcher.ScopeConfig
