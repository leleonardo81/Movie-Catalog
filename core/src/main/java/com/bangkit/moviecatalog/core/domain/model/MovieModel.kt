package com.bangkit.moviecatalog.core.domain.model
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieModel (
    val id: Int,

    val type: String,

    val posterUrl: String,

    val voteAverage: Double,

    val name: String,

    val desc : String,

    var isFavorite: Boolean
) : Parcelable