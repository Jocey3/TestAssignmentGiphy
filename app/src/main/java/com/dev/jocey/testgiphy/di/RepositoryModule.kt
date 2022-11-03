package com.dev.jocey.testgiphy.di

import com.dev.jocey.testgiphy.data.local.db.GiphyDataBase
import com.dev.jocey.testgiphy.data.remote.ApiGiphy
import com.dev.jocey.testgiphy.data.repository.GiphyRepositoryImpl
import com.dev.jocey.testgiphy.domain.repository.GiphyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideGiphyRepository(
        db: GiphyDataBase,
        apiService: ApiGiphy
    ) = GiphyRepositoryImpl(
        db,
        apiService
    ) as GiphyRepository
}