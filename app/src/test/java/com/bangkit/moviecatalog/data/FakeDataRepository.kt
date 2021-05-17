package com.bangkit.moviecatalog.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bangkit.moviecatalog.BuildConfig
import com.bangkit.moviecatalog.data.source.local.entity.MovieModel
import com.bangkit.moviecatalog.data.source.remote.RemoteDataSource
import com.bangkit.moviecatalog.data.source.remote.response.Response
import com.bangkit.moviecatalog.data.source.remote.response.ResultsItem
import retrofit2.Call

class FakeDataRepository constructor(private val remoteDataSource: RemoteDataSource): DataSource {

    override fun getAll(type: String): LiveData<List<MovieModel>> {
        val listItem = MutableLiveData<List<MovieModel>>()
        remoteDataSource.getList(type, object : RemoteDataSource.LoadListCallback {
            override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                if (response.isSuccessful) {
                    val listData = response.body()?.results
                    val newList = ArrayList<MovieModel>()
                    listData?.forEach { resultsItem -> resultsItem?.let {
                      newList.add(
                          MovieModel(
                              id = it.id,
                              name = it.title ?: it.name,
                              desc = it.overview,
                              voteAverage = it.voteAverage,
                              posterUrl = BuildConfig.POSTER_PREFIX + it.posterPath,
                              type = type
                          )
                      )
                    } }
                    listItem.postValue(newList)
                } else {
                    Log.e("DataRepository", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Response>, t: Throwable) {
                Log.e("DataRepository", "onFailure: ${t.message.toString()}")
            }

        })
        return listItem
    }

    override fun getDetail(type: String, id: Int): LiveData<MovieModel> {
        val itemDetail = MutableLiveData<MovieModel>()
        remoteDataSource.getDetail(type, id, object : RemoteDataSource.LoadDetailCallback {
            override fun onResponse(
                call: Call<ResultsItem>,
                response: retrofit2.Response<ResultsItem>
            ) {
                if (response.isSuccessful) {
                    val itemResponse = response.body()
                    itemResponse?.let {
                        itemDetail.postValue(
                            MovieModel(
                            id = it.id,
                            name = it.title ?: it.name,
                            desc = it.overview,
                            voteAverage = it.voteAverage,
                            posterUrl = BuildConfig.POSTER_PREFIX + it.posterPath,
                            type = type
                        )
                        )
                    }
                } else {
                    Log.e("DataRepository", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResultsItem>, t: Throwable) {
                Log.e("DataRepository", "onFailure: ${t.message.toString()}")
            }

        })
        return itemDetail
    }
}