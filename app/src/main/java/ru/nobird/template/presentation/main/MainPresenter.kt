package ru.nobird.template.presentation.main

import ru.nobird.template.domain.base.extension.emptyOnErrorStub
import ru.nobird.template.domain.sample.interactor.SampleInteractor
import ru.nobird.template.view.injection.qualifiers.BackgroundScheduler
import ru.nobird.template.view.injection.qualifiers.MainScheduler
import io.reactivex.Scheduler
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import ru.nobird.android.presentation.base.PresenterBase
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class MainPresenter
@Inject
constructor(
    private val router: Router,

    private val sampleInteractor: SampleInteractor,

    @BackgroundScheduler
    private val backgroundScheduler: Scheduler,
    @MainScheduler
    private val mainScheduler: Scheduler
) : PresenterBase<MainView>() {

    init {
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