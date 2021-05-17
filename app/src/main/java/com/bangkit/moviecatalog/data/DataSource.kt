package com.bangkit.moviecatalog.data

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.bangkit.moviecatalog.data.source.local.entity.MovieModel
import kotlinx.coroutines.flow.Flow

interface DataSource {
    fun getAll(type: String): Flow<PagingData<MovieModel>>

    fun getDetail(type: String, id: Int): LiveData<MovieModel>

    fun getFavorites(type: String): Flow<PagingData<MovieModel>>

    fun setFavorite(movieModel: MovieModel, state: Boolean)
}