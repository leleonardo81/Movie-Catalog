package com.bangkit.moviecatalog.favorites.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.moviecatalog.core.domain.usecase.MovieTvUseCase
import com.bangkit.moviecatalog.favorites.ui.viewmodel.ListFavViewModel
import javax.inject.Inject

class ViewModelFactory @Inject constructor(private val useCase: MovieTvUseCase)
    : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(ListFavViewModel::class.java) -> {
                ListFavViewModel(useCase) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}