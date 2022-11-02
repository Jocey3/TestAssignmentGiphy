package com.dev.jocey.testgiphy.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dev.jocey.testgiphy.data.local.dao.GiphyDao
import com.dev.jocey.testgiphy.data.local.dao.GiphyRemoteKeysDao
import com.dev.jocey.testgiphy.data.local.entity.GiphyEntity
import com.dev.jocey.testgiphy.data.local.entity.GiphyRemoteKeys

@Database(entities = [GiphyEntity::class, GiphyRemoteKeys::class], version = 1)
abstract class GiphyDataBase : RoomDatabase() {
    abstract fun giphyDao(): GiphyDao
    abstract fun giphyRemoteKeysDao(): GiphyRemoteKeysDao
}