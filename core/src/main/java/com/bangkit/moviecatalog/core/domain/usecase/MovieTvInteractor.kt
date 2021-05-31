package com.bangkit.moviecatalog.core.domain.usecase

import android.util.Log
import androidx.paging.PagingData
import com.bangkit.moviecatalog.core.domain.model.MovieModel
import com.bangkit.moviecatalog.core.domain.repository.IDataRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieTvInteractor @Inject constructor(private val dataRepository: IDataRepository) : MovieTvUseCase {
    override fun getAll(type: String): Flow<PagingData<MovieModel>> =
        dataRepository.getAll(type)
    override fun getDetail(type: String, id: Int): Flow<MovieModel> =
        dataRepository.getDetail(type, id)
    override fun getFavorites(type: String): Flow<PagingData<MovieModel>> =
        dataRepository.getFavorites(type)
    override fun setFavorite(movieModel: MovieModel, state: Boolean) {
        Log.d("2", movieModel.toString())
        dataRepository.setFavorite(movieModel, state)
    }
}