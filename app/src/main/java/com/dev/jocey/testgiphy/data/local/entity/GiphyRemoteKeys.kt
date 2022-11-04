package com.dev.jocey.testgiphy.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dev.jocey.testgiphy.core.util.Constants.DB_GIPHY_REMOTE_KEYS

@Entity(tableName = DB_GIPHY_REMOTE_KEYS)
data class GiphyRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val prevPage: Int?,
    val nextPage: Int?
)
