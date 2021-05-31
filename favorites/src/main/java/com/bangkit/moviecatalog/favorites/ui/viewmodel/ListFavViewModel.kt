package com.bangkit.moviecatalog.favorites.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bangkit.moviecatalog.core.domain.model.MovieModel
import com.bangkit.moviecatalog.core.domain.usecase.MovieTvUseCase
import kotlinx.coroutines.flow.Flow

//@HiltViewModel
class ListFavViewModel(private val useCase: MovieTvUseCase) : ViewModel() {

    private var currentResultMovie: Flow<PagingData<MovieModel>>? = null
    private var currentResultTv: Flow<PagingData<MovieModel>>? = null

    fun fetchMovie(type: String) : Flow<PagingData<MovieModel>> {
        val lastResult = if(type=="tv") currentResultTv else currentResultMovie
        if (lastResult != null) {
            return lastResult
        }

        val newResult = useCase.getFavorites(type).cachedIn(viewModelScope)

        when (type) {
            "tv" -> currentResultTv = newResult
            "movie" -> currentResultMovie = newResult
        }
        return newResult
    }
}