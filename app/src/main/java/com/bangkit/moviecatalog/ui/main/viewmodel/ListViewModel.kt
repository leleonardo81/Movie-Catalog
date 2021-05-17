package com.bangkit.moviecatalog.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bangkit.moviecatalog.data.DataRepository
import com.bangkit.moviecatalog.data.source.local.entity.MovieModel
import kotlinx.coroutines.flow.Flow

class ListViewModel(private val dataRepository: DataRepository) : ViewModel() {

    val loading = MutableLiveData(false)
    private var currentSearchResult: Flow<PagingData<MovieModel>>? = null

    fun setLoading(state: Boolean) {
        loading.postValue(state)
    }

    fun fetchMovie(type: String) : Flow<PagingData<MovieModel>> {
        val lastResult = currentSearchResult
        if (lastResult != null) return lastResult

        setLoading(true)
        val newResult = dataRepository.getAll(type)
            .cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }
}