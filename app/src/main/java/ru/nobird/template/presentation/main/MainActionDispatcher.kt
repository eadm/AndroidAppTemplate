package ru.nobird.template.presentation.main

import com.github.terrakok.cicerone.Router
import ru.nobird.android.presentation.redux.dispatcher.CoroutineActionDispatcher
import ru.nobird.template.domain.sample.interactor.SampleInteractor
import ru.nobird.template.presentation.main.base.ActionDispatcherOptions
import javax.inject.Inject

class MainActionDispatcher
@Inject
constructor(
    private val router: Router,
    private val sampleInteractor: SampleInteractor,
    config: ActionDispatcherOptions
) : CoroutineActionDispatcher<MainFeature.Action, MainFeature.Message>(config.createConfig()) {

    override suspend fun doSuspendableAction(action: MainFeature.Action) {
        when (action) {
            is MainFeature.Action.FetchSampleVal -> {
                val sampleValue = try {
                    val sampleValue = sampleInteractor.getSampleVal()
                    // onNewMessage(FetchSampleValSuccess(sampleValue))
                } catch (e: Exception) {
                    // onNewMessage(FetchSampleValFailed(e))
                }
            }
        }
    }
}
