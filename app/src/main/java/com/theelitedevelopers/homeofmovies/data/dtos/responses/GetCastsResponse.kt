package com.theelitedevelopers.homeofmovies.data.dtos.responses

import com.google.gson.annotations.SerializedName
import com.theelitedevelopers.homeofmovies.domain.models.Cast

data class GetCastsResponse(
    val id: String,
    @SerializedName("cast")
    val casts: List<Cast>,
) {
}