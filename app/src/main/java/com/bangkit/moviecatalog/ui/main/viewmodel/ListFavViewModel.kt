package com.bangkit.moviecatalog.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bangkit.moviecatalog.data.DataRepository
import com.bangkit.moviecatalog.data.source.local.entity.MovieModel
import kotlinx.coroutines.flow.Flow

class ListFavViewModel(private val dataRepository: DataRepository) : ViewModel() {

    val loading = MutableLiveData(false)

    fun setLoading(state: Boolean) {
        loading.postValue(state)
    }

    fun fetchMovie(type: String) : Flow<PagingData<MovieModel>> {
        setLoading(true)
        return dataRepository.getFavorites(type).cachedIn(viewModelScope)
    }
}