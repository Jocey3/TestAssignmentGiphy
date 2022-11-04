package com.dev.jocey.testgiphy.data.local.entity


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dev.jocey.testgiphy.core.util.Constants.DB_GIPHY_TABLE
import kotlinx.parcelize.Parcelize

@Entity(tableName = DB_GIPHY_TABLE)
@Parcelize
data class GiphyEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val title: String,
    val url: String,
    val searchQuery: String,
    val blocked: Boolean
) : Parcelable
