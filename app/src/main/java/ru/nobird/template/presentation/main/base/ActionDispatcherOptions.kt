package ru.nobird.template.presentation.main.base

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import ru.nobird.android.presentation.redux.dispatcher.CoroutineActionDispatcher

class ActionDispatcherOptions : CoroutineActionDispatcher.ScopeConfigOptions {
    override val actionParentScope: CoroutineScope? = null
    override val actionScopeExceptionHandler: CoroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e("ActionScopeException", "ActionDispatcher: exception on action scope", throwable)
    }

    override val messageParentScope: CoroutineScope? = null
    override val messageScopeExceptionHandler: CoroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e("MessageScopeException", "ActionDispatcher: exception on message scope", throwable)
        throw throwable // rethrow
    }
}
