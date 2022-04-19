package com.chibisov.movieinfoapplication.data.converter

import com.chibisov.movieinfoapplication.data.local.entity.MovieListEntity
import com.chibisov.movieinfoapplication.data.models.UiMovie

interface MovieListConverter {

    fun toUi(entity: MovieListEntity): UiMovie

    fun toEntity(movie: UiMovie): MovieListEntity
}