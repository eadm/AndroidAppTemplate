package ru.nobird.template.cache.sample.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Single
import ru.nobird.template.cache.common.RoomConstants
import ru.nobird.template.cache.sample.model.SampleDbEntry

/**
 * Created by Alexander Kolpakov (jquickapp@gmail.com) on 08-Jul-20
 * https://github.com/bitvale
 */
@Dao
interface SampleDao {
    @Query("SELECT * FROM ${RoomConstants.TABLE_SAMPLE}")
    fun getAll(): Single<List<SampleDbEntry>>

    @Query("SELECT * FROM ${RoomConstants.TABLE_SAMPLE} where id = :id")
    fun getById(id: Long): Single<SampleDbEntry>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(data: List<SampleDbEntry>): Completable

    @Query("DELETE FROM ${RoomConstants.TABLE_SAMPLE}")
    fun clear()
}
