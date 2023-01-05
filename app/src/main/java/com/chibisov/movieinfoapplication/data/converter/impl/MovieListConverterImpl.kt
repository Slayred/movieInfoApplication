package com.chibisov.movieinfoapplication.data.converter.impl

import com.chibisov.movieinfoapplication.data.converter.MovieListConverter
import com.chibisov.movieinfoapplication.data.local.entity.MovieListEntity
import com.chibisov.movieinfoapplication.data.models.UiMovie

class MovieListConverterImpl : MovieListConverter {

    override fun toUi(entity: MovieListEntity): UiMovie {
        return UiMovie(
            entity.kinopoiskId,
            entity.name,
            "descr",
            entity.posterPreviewPath,
            entity.status,
            entity.checked
        )
    }

    override fun toEntity(movie: UiMovie): MovieListEntity {
        return MovieListEntity(
            movie.id,
            movie.posterPath,
            movie.name,
            movie.status,
            movie.checked
        )
    }
}