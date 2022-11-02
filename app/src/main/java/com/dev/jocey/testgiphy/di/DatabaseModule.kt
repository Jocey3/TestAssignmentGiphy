package com.dev.jocey.testgiphy.di

import android.content.Context
import androidx.room.Room
import com.dev.jocey.testgiphy.data.local.db.GiphyDataBase
import com.dev.jocey.testgiphy.util.Constants.GIPHY_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): GiphyDataBase {
        return Room.databaseBuilder(context, GiphyDataBase::class.java, GIPHY_DATABASE).build()
    }
}