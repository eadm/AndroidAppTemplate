package ru.nobird.template.domain.sample.interactor

import io.reactivex.Completable
import io.reactivex.Single
import ru.nobird.template.domain.sample.model.SampleEntry
import ru.nobird.template.domain.sample.repository.SampleRepository
import javax.inject.Inject

class SampleInteractor
@Inject
constructor(
    private val sampleRepository: SampleRepository
) {
    fun getSampleVal(): Single<String> =
        sampleRepository
            .getSampleVal()

    fun saveSampleEntries(data: List<SampleEntry>): Completable =
        sampleRepository.saveSampleEntries(data)

    fun getSampleEntries(): Single<List<SampleEntry>> =
        sampleRepository.getSampleEntries()

    fun getSampleEntry(id: Long): Single<SampleEntry> =
        sampleRepository.getSampleEntry(id)
}
