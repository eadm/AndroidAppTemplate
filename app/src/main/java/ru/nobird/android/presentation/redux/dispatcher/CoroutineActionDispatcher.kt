package ru.nobird.android.presentation.redux.dispatcher

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

@Suppress("unused")
abstract class CoroutineActionDispatcher<Action, Message>(
    coroutineScopeConfig: ScopeConfig
) : ActionDispatcher<Action, Message> {

    protected val actionScope: CoroutineScope = coroutineScopeConfig.actionScope
    protected val messageScope: CoroutineScope = coroutineScopeConfig.messageScope

    private var cancelled: Boolean = false
    private var messageListener: ((Message) -> Unit)? = null

    /**
     * Default implementation of [handleAction] which uses [doSuspendableAction] for all actions.
     */
    override fun handleAction(action: Action) {
        if (cancelled) {
            return
        }

        actionScope.launch {
            doSuspendableAction(action)
        }
    }

    /**
     * Executes specified [action] on [actionScope].
     * Default implementation of [handleAction] calls [doSuspendableAction] for all actions.
     */
    protected open suspend fun doSuspendableAction(action: Action) {
    }

    override fun setListener(listener: (message: Message) -> Unit) {
        if (cancelled) {
            return
        }

        messageListener = listener
    }

    override fun cancel() {
        cancelled = true
        actionScope.cancel("cancelled by ActionDispatcher.cancel()")
        messageScope.cancel("cancelled by ActionDispatcher.cancel()")
        messageListener = null
    }

    /**
     * Posts specified [message] on [messageScope].
     */
    protected fun onNewMessage(message: Message) {
        messageScope.launch {
            messageListener?.invoke(message)
        }
    }

    interface ScopeConfig {
        val actionScope: CoroutineScope
        val messageScope: CoroutineScope
    }

    interface ScopeConfigOptions {

        /**
         * See [createConfig] for details
         */
        val actionParentScope: CoroutineScope?

        /**
         * See [createConfig] for details
         */
        val actionScopeExceptionHandler: CoroutineExceptionHandler

        /**
         * See [createConfig] for details
         */
        val messageParentScope: CoroutineScope?

        /**
         * See [createConfig] for details
         */
        val messageScopeExceptionHandler: CoroutineExceptionHandler

        fun createConfig(): ScopeConfig {
            // default dispatcher is Dispatchers.Main
            val actionScope = (actionParentScope ?: MainScope()).let { scope ->
                scope + SupervisorJob(scope.coroutineContext[Job]) + actionScopeExceptionHandler
            }

            // default dispatcher is Dispatchers.Main
            val messageScope = (messageParentScope ?: MainScope()).let { scope ->
                scope + SupervisorJob(scope.coroutineContext[Job]) + messageScopeExceptionHandler
            }

            return object : ScopeConfig {
                override val actionScope: CoroutineScope = actionScope
                override val messageScope: CoroutineScope = messageScope
            }
        }
    }
}
