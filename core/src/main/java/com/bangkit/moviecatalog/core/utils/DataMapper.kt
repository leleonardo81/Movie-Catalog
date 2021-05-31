package com.bangkit.moviecatalog.core.utils

import com.bangkit.moviecatalog.core.BuildConfig
import com.bangkit.moviecatalog.core.data.source.local.entity.MovieEntity
import com.bangkit.moviecatalog.core.data.source.remote.response.ResultsItem
import com.bangkit.moviecatalog.core.domain.model.MovieModel

object DataMapper {
    fun mapEntitiesToDomain(input: List<MovieEntity>) : List<MovieModel> =
        input.map {
            mapEntityToDomain(it)
        }

    fun mapResponseToEntities(input: List<ResultsItem>, type: String) : List<MovieEntity> =
        input.map {
            MovieEntity(
                id = it.id,
                name = it.title ?: it.name,
                desc = it.overview,
                voteAverage = it.voteAverage,
                posterUrl = BuildConfig.POSTER_PREFIX + it.posterPath,
                type = type
            )
        }

    fun mapEntityToDomain(item: MovieEntity): MovieModel =
        MovieModel(
            id = item.id,
            type = item.type ?: "movie",
            posterUrl = item.posterUrl ?: "",
            voteAverage = item.voteAverage ?: 0.0,
            name = item.name ?: "",
            desc = item.desc ?: "",
            isFavorite = item.isFavorite
        )

    fun mapDomainToEntity(item: MovieModel): MovieEntity =
        MovieEntity(
            id = item.id,
            type = item.type,
            posterUrl = item.posterUrl,
            voteAverage = item.voteAverage,
            name = item.name,
            desc = item.desc,
            isFavorite = item.isFavorite
        )
}