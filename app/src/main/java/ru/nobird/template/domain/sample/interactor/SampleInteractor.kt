package ru.nobird.template.domain.sample.interactor

import javax.inject.Inject
import ru.nobird.template.domain.sample.model.SampleEntry
import ru.nobird.template.domain.sample.repository.SampleRepository

class SampleInteractor
@Inject
constructor(
    private val sampleRepository: SampleRepository
) {
    suspend fun getSampleVal(): String =
        sampleRepository.getSampleVal()

    suspend fun saveSampleEntries(data: List<SampleEntry>) {
        sampleRepository.saveSampleEntries(data)
    }

    suspend fun getSampleEntries(): List<SampleEntry> =
        sampleRepository.getSampleEntries()

    suspend fun getSampleEntry(id: Long): SampleEntry? =
        sampleRepository.getSampleEntry(id)
}
