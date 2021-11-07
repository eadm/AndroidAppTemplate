package ru.nobird.template.cache.common

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.nobird.template.cache.sample.dao.SampleDao
import ru.nobird.template.cache.sample.model.SampleDbEntry

/**
 * Created by Alexander Kolpakov (jquickapp@gmail.com) on 08-Jul-20
 * https://github.com/bitvale
 */
@Database(
    entities = [
        SampleDbEntry::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    internal abstract fun getSampleDaoDao(): SampleDao
}
