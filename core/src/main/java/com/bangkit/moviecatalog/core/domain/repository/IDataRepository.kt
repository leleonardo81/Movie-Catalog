package com.bangkit.moviecatalog.core.domain.repository

import androidx.paging.PagingData
import com.bangkit.moviecatalog.core.domain.model.MovieModel
import kotlinx.coroutines.flow.Flow

interface IDataRepository {

    fun getAll(type: String): Flow<PagingData<MovieModel>>

    fun getDetail(type: String, id: Int): Flow<MovieModel>

    fun getFavorites(type: String): Flow<PagingData<MovieModel>>

    fun setFavorite(movieModel: MovieModel, state: Boolean)
}