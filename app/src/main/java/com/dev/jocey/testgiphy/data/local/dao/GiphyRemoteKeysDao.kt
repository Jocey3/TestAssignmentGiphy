package com.dev.jocey.testgiphy.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dev.jocey.testgiphy.data.local.entity.GiphyRemoteKeys

@Dao
interface GiphyRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys: List<GiphyRemoteKeys>)

    @Query("SELECT * FROM DB_GIPHY_REMOTE_KEYS WHERE id = :id")
    suspend fun getRemoteKeys(id: String): GiphyRemoteKeys

    @Query("DELETE FROM DB_GIPHY_REMOTE_KEYS")
    suspend fun deleteRemoteKeys()
}