package com.dev.jocey.testgiphy.data.remote.dto

import com.dev.jocey.testgiphy.data.local.entity.GiphyEntity

data class SearchResultDto(
    val `data`: List<Data>,
    val meta: Meta,
    val pagination: Pagination
){

}