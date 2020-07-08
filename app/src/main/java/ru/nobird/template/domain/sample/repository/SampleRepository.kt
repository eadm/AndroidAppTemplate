package ru.nobird.template.domain.sample.repository

import io.reactivex.Completable
import io.reactivex.Single
import ru.nobird.template.domain.base.DataSourceType
import ru.nobird.template.domain.sample.model.SampleEntry

interface SampleRepository {
    fun getSampleVal(primaryDataSource: DataSourceType = DataSourceType.REMOTE): Single<String>
    fun saveSampleEntries(data: List<SampleEntry>): Completable
    fun getSampleEntries(): Single<List<SampleEntry>>
    fun getSampleEntry(id: Long): Single<SampleEntry>
}
