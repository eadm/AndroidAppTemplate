package ru.nobird.template.cache.sample.mapper

import ru.nobird.template.cache.sample.model.SampleDbEntry
import ru.nobird.template.domain.sample.model.SampleEntry

/**
 * Created by Alexander Kolpakov (jquickapp@gmail.com) on 08-Jul-20
 * https://github.com/bitvale
 */
object SampleDbMapper {
    fun toDb(data: SampleEntry): SampleDbEntry =
        SampleDbEntry(
            id = data.id
        )

    fun fromDb(data: List<SampleDbEntry>): List<SampleEntry> =
        data.map(::fromDb)

    fun fromDb(data: SampleDbEntry): SampleEntry =
        SampleEntry(
            id = data.id
        )
}
