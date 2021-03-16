package ru.nobird.android.presentation.redux.dispatcher

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

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
        messageListener = listener
    }

    override fun cancel() {
        cancelled = true
        //coroutineScope.cancel() -- do we really need to cancel the scope? Probably not.
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
}
