package com.dev.jocey.testgiphy.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dev.jocey.testgiphy.data.local.entity.GiphyEntity

@Dao
interface GiphyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllGifs(gifs: List<GiphyEntity>)

    @Query("SELECT * FROM DB_GIPHY_TABLE WHERE blocked = 0")
     fun getAllNotBlockedGifs(): PagingSource<Int, GiphyEntity>

    @Query("UPDATE DB_GIPHY_TABLE SET blocked = 1 WHERE id = :id")
    suspend fun markGifAsBlocked(id: String)

    @Query("DELETE FROM DB_GIPHY_TABLE")
    suspend fun deleteAllGifs()
}