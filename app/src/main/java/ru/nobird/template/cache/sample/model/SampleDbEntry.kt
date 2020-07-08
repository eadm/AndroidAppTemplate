package ru.nobird.template.cache.sample.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.nobird.template.cache.common.RoomConstants

/**
 * Created by Alexander Kolpakov (jquickapp@gmail.com) on 08-Jul-20
 * https://github.com/bitvale
 */
@Entity(tableName = RoomConstants.TABLE_SAMPLE)
data class SampleDbEntry(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long
)
