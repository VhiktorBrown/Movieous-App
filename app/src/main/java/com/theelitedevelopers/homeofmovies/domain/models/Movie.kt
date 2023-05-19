package com.theelitedevelopers.homeofmovies.domain.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: Int?,
    val homepage: String,
    val genres: List<Genre>,
    @SerializedName("original_language")
    val originalLanguage: String,
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
    val backdropPath: String?,
    val revenue: Int,
    val status: String,
    val tagline: String,
    val runtime: Int,
    val productionCompanies: List<ProductionCompany>,
    ) : Parcelable {

    }