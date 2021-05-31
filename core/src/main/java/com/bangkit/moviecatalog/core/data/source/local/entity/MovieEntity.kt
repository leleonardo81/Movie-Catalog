package com.bangkit.moviecatalog.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movietv")
data class MovieEntity (
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "type")
    val type: String?,

    @ColumnInfo(name = "posterurl")
    val posterUrl: String?,

    @ColumnInfo(name = "voteaverage")
    val voteAverage: Double?,

    @ColumnInfo(name = "name")
    val name: String?,

    @ColumnInfo(name = "desc")
    val desc : String?,

    @ColumnInfo(name = "favorite")
    var isFavorite: Boolean = false
)