package com.bangkit.moviecatalog.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.moviecatalog.data.DataRepository
import com.bangkit.moviecatalog.di.Injection
import com.bangkit.moviecatalog.ui.detail.viewmodel.DetailViewModel
import com.bangkit.moviecatalog.ui.main.viewmodel.ListFavViewModel
import com.bangkit.moviecatalog.ui.main.viewmodel.ListViewModel

class ViewModelFactory private constructor(private val mDataRepository: DataRepository) : ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                ViewModelFactory(Injection.provideRepository(context)).apply { instance = this }
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ListViewModel::class.java) -> {
                ListViewModel(mDataRepository) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(mDataRepository) as T
            }
            modelClass.isAssignableFrom(ListFavViewModel::class.java) -> {
                ListFavViewModel(mDataRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }
}