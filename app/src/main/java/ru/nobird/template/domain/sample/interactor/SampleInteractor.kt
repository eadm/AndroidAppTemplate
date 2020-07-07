package ru.nobird.template.domain.sample.interactor

import ru.nobird.template.domain.sample.repository.SampleRepository
import io.reactivex.Single
import javax.inject.Inject

class SampleInteractor
@Inject
constructor(
    private val sampleRepository: SampleRepository
) {
    fun getSampleVal(): Single<String> =
        sampleRepository
            .getSampleVal()
}