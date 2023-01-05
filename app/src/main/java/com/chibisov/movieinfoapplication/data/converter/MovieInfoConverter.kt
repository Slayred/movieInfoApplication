package com.chibisov.movieinfoapplication.data.converter

import com.chibisov.movieinfoapplication.data.local.entity.MovieInfoEntity
import com.chibisov.movieinfoapplication.data.models.KinopoiskMovieInfoModel
import com.chibisov.movieinfoapplication.data.models.UiMovie
import io.reactivex.Flowable

interface MovieInfoConverter {

    fun fromEntityToUi(entity: MovieInfoEntity): UiMovie

    fun fromUiToEntity(movie: UiMovie): MovieInfoEntity

    fun fromModelToEntity(movie: KinopoiskMovieInfoModel): MovieInfoEntity

    fun fromModelToUi(movieInfo: KinopoiskMovieInfoModel): UiMovie

    fun setUiMovieChecked(movie: UiMovie)
}