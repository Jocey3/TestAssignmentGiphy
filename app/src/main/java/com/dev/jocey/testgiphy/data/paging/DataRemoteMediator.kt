package com.dev.jocey.testgiphy.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.dev.jocey.testgiphy.data.local.db.GiphyDataBase
import com.dev.jocey.testgiphy.data.local.entity.GiphyEntity
import com.dev.jocey.testgiphy.data.local.entity.GiphyRemoteKeys
import com.dev.jocey.testgiphy.data.remote.ApiGiphy
import com.dev.jocey.testgiphy.util.Constants.ITEMS_PER_PAGE
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class DataRemoteMediator(
    private val db: GiphyDataBase,
    private val apiService: ApiGiphy,
    private val searchQuery: String
) : RemoteMediator<Int, GiphyEntity>() {

    private val giphyDao = db.giphyDao()
    private val giphyRemoteKeysDao = db.giphyRemoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, GiphyEntity>
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            val response = apiService.search(
                searchQuery = searchQuery,
                offset = currentPage,
                limit = ITEMS_PER_PAGE
            )
            val endOfPaginationReached = response.data.isEmpty() ?: true

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    giphyDao.deleteAllGifs()
                    giphyRemoteKeysDao.deleteRemoteKeys()
                }
                val keys = response.data.map { unsplashImage ->
                    GiphyRemoteKeys(
                        id = unsplashImage.id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                keys.let { giphyRemoteKeysDao.addAllRemoteKeys(remoteKeys = it) }
                response.data.let { it ->
                    giphyDao.addAllGifs(
                        gifs = it.map { giphyDto ->
                            giphyDto.toGiphyEntity()
                        })
                }
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, GiphyEntity>
    ): GiphyRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                giphyRemoteKeysDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, GiphyEntity>
    ): GiphyRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { unsplashImage ->
                giphyRemoteKeysDao.getRemoteKeys(id = unsplashImage.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, GiphyEntity>
    ): GiphyRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { unsplashImage ->
                giphyRemoteKeysDao.getRemoteKeys(id = unsplashImage.id)
            }
    }

}