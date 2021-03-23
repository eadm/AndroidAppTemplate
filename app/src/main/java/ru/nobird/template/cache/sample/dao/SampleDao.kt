package ru.nobird.template.cache.sample.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.nobird.template.cache.common.RoomConstants
import ru.nobird.template.cache.sample.model.SampleDbEntry

/**
 * Created by Alexander Kolpakov (jquickapp@gmail.com) on 08-Jul-20
 * https://github.com/bitvale
 */
@Dao
interface SampleDao {
    @Query("SELECT * FROM ${RoomConstants.TABLE_SAMPLE}")
    suspend fun getAll(): List<SampleDbEntry>

    @Query("SELECT * FROM ${RoomConstants.TABLE_SAMPLE} where id = :id")
    suspend fun getById(id: Long): SampleDbEntry

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(data: List<SampleDbEntry>)

    @Query("DELETE FROM ${RoomConstants.TABLE_SAMPLE}")
    fun clear()
}
