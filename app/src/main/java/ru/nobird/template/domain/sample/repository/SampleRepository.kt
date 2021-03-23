package ru.nobird.template.domain.sample.repository

import ru.nobird.template.domain.base.DataSourceType
import ru.nobird.template.domain.sample.model.SampleEntry

interface SampleRepository {
    suspend fun getSampleVal(primaryDataSource: DataSourceType = DataSourceType.REMOTE): String
    suspend fun saveSampleEntries(data: List<SampleEntry>)
    suspend fun getSampleEntries(): List<SampleEntry>
    suspend fun getSampleEntry(id: Long): SampleEntry?
}
