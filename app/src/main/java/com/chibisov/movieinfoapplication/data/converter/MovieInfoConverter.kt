package com.chibisov.movieinfoapplication.data.converter

import com.chibisov.movieinfoapplication.data.local.entity.MovieInfoEntity
import com.chibisov.movieinfoapplication.data.models.UiMovie

interface MovieInfoConverter {

    fun toUi(entity: MovieInfoEntity): UiMovie

    fun toEntity(movie: UiMovie): MovieInfoEntity

}