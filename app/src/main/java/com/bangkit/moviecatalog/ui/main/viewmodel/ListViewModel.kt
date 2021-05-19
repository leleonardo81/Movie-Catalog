package com.bangkit.moviecatalog.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bangkit.moviecatalog.data.DataRepository
import com.bangkit.moviecatalog.data.source.local.entity.MovieModel
import kotlinx.coroutines.flow.Flow

class ListViewModel(private val dataRepository: DataRepository) : ViewModel() {

    private var currentResultMovie: Flow<PagingData<MovieModel>>? = null
    private var currentResultTv: Flow<PagingData<MovieModel>>? = null

    fun fetchMovie(type: String) : Flow<PagingData<MovieModel>> {
        val lastResult = if(type=="tv") currentResultTv else currentResultMovie

        if (lastResult != null) {
            return lastResult
        }

        val newResult = dataRepository.getAll(type)
            .cachedIn(viewModelScope)

        when (type) {
            "tv" -> currentResultTv = newResult
            "movie" -> currentResultMovie = newResult
        }
        return newResult
    }

}