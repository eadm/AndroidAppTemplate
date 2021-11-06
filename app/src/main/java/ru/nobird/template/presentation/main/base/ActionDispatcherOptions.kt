package ru.nobird.template.presentation.main.base

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import ru.nobird.android.presentation.redux.dispatcher.CoroutineActionDispatcher

/**
 * Null scope means ActionDispatcher can decide for itself
 * (default behavior: MainDispatcher() will be used for both actionParentScope and messageParentScope)
 */
class ActionDispatcherOptions(
    override val actionParentScope: CoroutineScope? = null,
    override val messageParentScope: CoroutineScope? = null
) : CoroutineActionDispatcher.ScopeConfigOptions {

    override val actionScopeExceptionHandler: CoroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            Log.e(
                "ActionScopeException",
                "ActionDispatcher: exception on action scope",
                throwable
            )
        }

    override val messageScopeExceptionHandler: CoroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            Log.e(
                "MessageScopeException",
                "ActionDispatcher: exception on message scope",
                throwable
            )
            throw throwable // rethrow
        }
}
