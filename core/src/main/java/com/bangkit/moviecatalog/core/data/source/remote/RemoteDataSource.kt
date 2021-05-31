package com.bangkit.moviecatalog.core.data.source.remote

import android.util.Log
import com.bangkit.moviecatalog.core.data.source.remote.api.ApiResponse
import com.bangkit.moviecatalog.core.data.source.remote.api.ApiService
import com.bangkit.moviecatalog.core.data.source.remote.response.ResultsItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService){

    suspend fun getList(type: String): Flow<ApiResponse<List<ResultsItem>>> {
        return flow {
            val response = apiService.getList(type)
            val listData = response.results
            try {
                if (!listData.isNullOrEmpty()) emit(ApiResponse.Success(listData))
                else emit(ApiResponse.Empty)
            } catch (e : Exception){
                emit(ApiResponse.Error(e))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}
