package com.bangkit.moviecatalog.api

import com.bangkit.moviecatalog.BuildConfig
import com.bangkit.moviecatalog.data.source.remote.response.Response
import com.bangkit.moviecatalog.data.source.remote.response.ResultsItem
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("discover/{type}?api_key=${BuildConfig.API_KEY}")
    suspend fun getList(@Path("type") type: String) : Response

    @GET("{type}/{id}?api_key=${BuildConfig.API_KEY}")
    suspend fun getDetail(@Path("type") type: String, @Path("id") id: Int) : ResultsItem
}