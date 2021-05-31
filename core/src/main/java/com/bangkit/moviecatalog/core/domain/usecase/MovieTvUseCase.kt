package com.bangkit.moviecatalog.core.domain.usecase

import androidx.paging.PagingData
import com.bangkit.moviecatalog.core.domain.model.MovieModel
import kotlinx.coroutines.flow.Flow

interface MovieTvUseCase {
    fun getAll(type: String): Flow<PagingData<MovieModel>>

    fun getDetail(type: String, id: Int): Flow<MovieModel>

    fun getFavorites(type: String): Flow<PagingData<MovieModel>>

    fun setFavorite(movieModel: MovieModel, state: Boolean)
}