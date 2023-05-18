package com.theelitedevelopers.homeofmovies.data.dtos.responses

import com.google.gson.annotations.SerializedName
import com.theelitedevelopers.homeofmovies.domain.models.Movie

data class GetMoviesResponse(
    val page: Int,
    val results: List<Movie>,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?
)