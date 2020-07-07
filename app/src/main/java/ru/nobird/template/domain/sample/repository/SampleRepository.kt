package ru.nobird.template.domain.sample.repository

import ru.nobird.template.domain.base.DataSourceType
import io.reactivex.Single

interface SampleRepository {
    fun getSampleVal(primaryDataSource: DataSourceType = DataSourceType.REMOTE): Single<String>
}