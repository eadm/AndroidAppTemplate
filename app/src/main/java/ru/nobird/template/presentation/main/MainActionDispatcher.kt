package ru.nobird.template.presentation.main

import io.reactivex.Scheduler
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import ru.nobird.android.domain.rx.emptyOnErrorStub
import ru.nobird.android.presentation.redux.dispatcher.RxActionDispatcher
import ru.nobird.android.view.injection.base.RxScheduler
import ru.nobird.template.domain.sample.interactor.SampleInteractor
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class MainActionDispatcher
@Inject
constructor(
    private val router: Router,

    private val sampleInteractor: SampleInteractor,

    @RxScheduler.Background
    private val backgroundScheduler: Scheduler,
    @RxScheduler.Main
    private val mainScheduler: Scheduler
) : RxActionDispatcher<MainFeature.Action, MainFeature.Message>() {
    override fun handleAction(action: MainFeature.Action) {
        when (action) {
            is MainFeature.Action.FetchSampleVal ->
                compositeDisposable += sampleInteractor
                    .getSampleVal()
                    .subscribeOn(backgroundScheduler)
                    .observeOn(mainScheduler)
                    .subscribeBy(
                        onSuccess = { /* cool */ },
                        onError = emptyOnErrorStub
                    )
        }
    }
}