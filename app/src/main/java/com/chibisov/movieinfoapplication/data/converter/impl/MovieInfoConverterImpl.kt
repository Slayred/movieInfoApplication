package com.chibisov.movieinfoapplication.data.converter.impl

import com.chibisov.movieinfoapplication.data.converter.MovieInfoConverter
import com.chibisov.movieinfoapplication.data.local.entity.MovieInfoEntity
import com.chibisov.movieinfoapplication.data.models.UiMovie

class MovieInfoConverterImpl : MovieInfoConverter {

    override fun toUi(entity: MovieInfoEntity): UiMovie {
        return UiMovie(entity.kinopoiskId,
        entity.name, entity.description, entity.posterPath
        ,entity.status, entity.checked)
    }

    override fun toEntity(movie: UiMovie): MovieInfoEntity {
        return MovieInfoEntity(movie.id, movie.name, movie.description,
        movie.posterPath, movie.status, movie.checked)
    }
}