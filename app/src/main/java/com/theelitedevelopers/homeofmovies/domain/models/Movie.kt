package com.theelitedevelopers.homeofmovies.domain.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: Int?,
    @SerializedName("title")
    val title: String,
    @SerializedName("overview")
    val description: String?,
    val popularity: Double?,
    @SerializedName("poster_path")
    val image: String?,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("vote_average")
    val rating: Double?,
    @SerializedName("backdrop_path")
    val backdropPath: String?
    ) : Parcelable {

    }