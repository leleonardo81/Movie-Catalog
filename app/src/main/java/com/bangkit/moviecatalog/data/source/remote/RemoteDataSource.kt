package com.bangkit.moviecatalog.data.source.remote

import com.bangkit.moviecatalog.api.ApiConfig
import com.bangkit.moviecatalog.data.source.remote.response.Response
import com.bangkit.moviecatalog.data.source.remote.response.ResultsItem
import retrofit2.Callback

class RemoteDataSource private constructor(){
    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource().apply { instance = this }
            }
    }

    suspend fun getList(type: String): Response {
        return ApiConfig.getApiService().getList(type)
//        val client = ApiConfig.getApiService().getList(type)
//        val result = MutableLiveData<ApiResponse<Response>>()
//        EspressoIdlingResource.increment()
//        client.enqueue(object : LoadListCallback {
//            override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
//                EspressoIdlingResource.decrement()
//                if (response.isSuccessful) {
//                    response.body()?.let {
//                        result.postValue(ApiResponse.success(it))
//                    }
//                } else {
//                    result.postValue(ApiResponse.error(response.message(), Response()))
//                }
//            }
//            override fun onFailure(call: Call<Response>, t: Throwable) {
//                EspressoIdlingResource.decrement()
//                Log.e("RemoteDataSource", "onFailure: ${t.message.toString()}")
//                result.postValue(ApiResponse.error(t.message.toString(), Response() ))
//            }
//        })
//        return result
    }

//    suspend fun getDetail(type: String, id: Int): LiveData<ApiResponse<ResultsItem>> {
//        val client = ApiConfig.getApiService().getDetail(type, id)
//        val result = MutableLiveData<ApiResponse<ResultsItem>>()
//        EspressoIdlingResource.increment()
//        client.enqueue(object : LoadDetailCallback {
//            override fun onResponse(
//                call: Call<ResultsItem>,
//                response: retrofit2.Response<ResultsItem>
//            ) {
//                EspressoIdlingResource.decrement()
//                if (response.isSuccessful) {
//                    response.body()?.let {
//                        result.postValue(ApiResponse.success(it))
//                    }
//                } else {
//                    result.postValue(ApiResponse.error(response.message(), ResultsItem()))
//                }
//            }
//            override fun onFailure(call: Call<ResultsItem>, t: Throwable) {
//                EspressoIdlingResource.decrement()
//                Log.e("RemoteDataSource", "onFailure: ${t.message.toString()}")
//                result.postValue(ApiResponse.error(t.message.toString(), ResultsItem()))
//            }
//        })
//        return result
//    }


    interface LoadListCallback : Callback<Response> {

    }

    interface LoadDetailCallback : Callback<ResultsItem> {

    }

}