package ru.nobird.template.data.sample.source

import ru.nobird.template.domain.sample.model.SampleEntry

interface SampleCacheDataSource {
    suspend fun getSampleVal(): String?
    suspend fun saveSampleVal(sampleVal: String)
    suspend fun saveSampleEntries(data: List<SampleEntry>)
    suspend fun getSampleEntries(): List<SampleEntry>
    suspend fun getSampleEntry(id: Long): SampleEntry?
}
