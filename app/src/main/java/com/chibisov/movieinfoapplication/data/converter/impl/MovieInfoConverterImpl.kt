package com.chibisov.movieinfoapplication.data.converter.impl

import com.chibisov.movieinfoapplication.data.converter.MovieInfoConverter
import com.chibisov.movieinfoapplication.data.local.entity.MovieInfoEntity
import com.chibisov.movieinfoapplication.data.models.KinopoiskMovieInfoModel
import com.chibisov.movieinfoapplication.data.models.UiMovie
import io.reactivex.Flowable

class MovieInfoConverterImpl : MovieInfoConverter {

    override fun fromEntityToUi(entity: MovieInfoEntity): UiMovie {
        return UiMovie(entity.kinopoiskId,
        entity.name, entity.description, entity.posterPath
        ,entity.status, entity.checked)


    }

    override fun fromUiToEntity(movie: UiMovie): MovieInfoEntity {
        return MovieInfoEntity(movie.id, movie.name, movie.description,
        movie.posterPath, movie.status, movie.checked)
    }

    override fun fromModelToEntity(movie: KinopoiskMovieInfoModel): MovieInfoEntity {
        return MovieInfoEntity(movie.kinopoiskId,movie.nameRu, movie
            .description, movie.posterUrl, status = false, checked = false)
    }

    override fun fromModelToUi(movieInfo: KinopoiskMovieInfoModel): UiMovie {
        return UiMovie(movieInfo.kinopoiskId,movieInfo.nameRu, movieInfo.description, movieInfo.posterUrl,status = false,
        checked = false)
    }

    override fun setUiMovieChecked(movie: UiMovie) {
        movie.checked = true
    }

}