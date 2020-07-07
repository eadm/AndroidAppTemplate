package ru.nobird.template.domain.sample.repository

import io.reactivex.Single
import ru.nobird.template.domain.base.DataSourceType

interface SampleRepository {
    fun getSampleVal(primaryDataSource: DataSourceType = DataSourceType.REMOTE): Single<String>
}
