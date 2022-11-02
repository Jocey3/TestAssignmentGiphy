package com.dev.jocey.testgiphy.data.remote

import com.dev.jocey.testgiphy.data.remote.dto.SearchResultDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiGiphy {
    @GET("search")
    suspend fun search(
        @Query("q") searchQuery: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Response<SearchResultDto>

}