package com.bangkit.moviecatalog.detail.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bangkit.moviecatalog.core.domain.model.MovieModel
import com.bangkit.moviecatalog.core.domain.usecase.MovieTvUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val useCase: MovieTvUseCase) : ViewModel() {
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
        return useCase.getDetail(type, id).asLiveData()
    }

    fun setFavorite(state: Boolean) {
        Log.d("1", state.toString())
        data.value?.let {
            useCase.setFavorite(it, state)
        }
    }


}
