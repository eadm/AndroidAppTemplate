package ru.nobird.template.data.sample.source

import ru.nobird.template.domain.base.RemoteResult

interface SampleRemoteDataSource {
    suspend fun getSampleVal(): RemoteResult<String>
}
