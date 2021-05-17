package com.bangkit.moviecatalog.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import com.bangkit.moviecatalog.data.source.local.entity.MovieModel
import com.bangkit.moviecatalog.data.source.local.room.DataDao

class LocalDataSource private constructor(private val mDataDao: DataDao) {
    companion object {
        private val INSTANCE: LocalDataSource? = null

        fun getInstance(dataDao: DataDao): LocalDataSource = INSTANCE ?: LocalDataSource(dataDao)
    }

    fun getList(type: String): PagingSource<Int, MovieModel> = mDataDao.getDatas(type)

    fun getDetail(type: String, id: Int): LiveData<MovieModel> = mDataDao.getDetail(type, id)

    suspend fun insertDatas(listData: List<MovieModel>) = mDataDao.insertDatas(listData)

    fun getFavoriteList(type: String): PagingSource<Int, MovieModel> = mDataDao.getFavorite(type)

    fun setFavorite(movieModel: MovieModel, state: Boolean) {
        movieModel.isFavorite = state
        mDataDao.updateData(movieModel)
    }
}