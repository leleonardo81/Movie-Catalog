package com.bangkit.moviecatalog.ui.detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.moviecatalog.data.DataRepository
import com.bangkit.moviecatalog.data.source.local.entity.MovieModel

class DetailViewModel(private val dataRepository: DataRepository) : ViewModel() {
    val loading = MutableLiveData(false)
    val data = MutableLiveData<MovieModel>()

    fun setLoading(state: Boolean) {
        loading.postValue(state)
    }

    fun setData(movieModel: MovieModel) {
        data.postValue(movieModel)
    }

    fun fetchData(type: String, id: Int): LiveData<MovieModel> {
        setLoading(true)
        return dataRepository.getDetail(type, id)
    }

    fun setFavorite(state: Boolean) {
        data.value?.let {
            dataRepository.setFavorite(it, state)
        }
    }


}
