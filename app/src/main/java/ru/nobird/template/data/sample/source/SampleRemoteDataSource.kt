package ru.nobird.template.data.sample.source

interface SampleRemoteDataSource {
    suspend fun getSampleVal(): String
}
