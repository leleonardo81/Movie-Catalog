package com.bangkit.moviecatalog.utils

import com.bangkit.moviecatalog.core.data.source.local.entity.MovieEntity
import com.bangkit.moviecatalog.core.data.source.remote.response.Response
import com.bangkit.moviecatalog.core.data.source.remote.response.ResultsItem
import com.bangkit.moviecatalog.core.domain.model.MovieModel


object DataDummy {
    fun getListMovie() : List<MovieModel> {
        val listMovie = ArrayList<MovieModel>()

        for (i in 1..15) {
            listMovie.add(MovieModel(1243, "movie", "/", 1.2, "Name", "aaaa", false))
        }

        return listMovie
    }

    fun getListMovieEntity() : List<MovieEntity> {
        val listMovie = ArrayList<MovieEntity>()

        for (i in 1..15) {
            listMovie.add(MovieEntity(1243, "movie", "/", 1.2, "Name", "aaaa"))
        }

        return listMovie
    }

    fun getResponse() : Response {
        val resultItem = ArrayList<ResultsItem>()
        for (i in 1..15) {
            resultItem.add(ResultsItem(5.2, "aaa", null, 1243, "Name Title", "/"))
        }
        return Response(resultItem)
    }
}