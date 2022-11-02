package com.dev.jocey.testgiphy.data.remote.dto

data class SearchResultDto(
    val `data`: List<Data>,
    val meta: Meta,
    val pagination: Pagination
)