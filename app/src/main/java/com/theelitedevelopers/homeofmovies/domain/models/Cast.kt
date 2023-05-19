package com.theelitedevelopers.homeofmovies.domain.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cast(
    val adult: Boolean,
    val gender: Int,
    val id: Int,
    @SerializedName("known_for_department")
    val knownFor: String,
    val name: String,
    val popularity: Double,
    @SerializedName("profile_path")
    val profilePath: String,
    @SerializedName("cast_id")
    val castId: Int,
    val character: String,
) : Parcelable {

}