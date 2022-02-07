package com.chibisov.movieinfoapplication.domain

import com.chibisov.movieinfoapplication.data.models.Movie
import com.chibisov.movieinfoapplication.data.models.UiMovie

class BaseInteractor {

    fun changeStatus(movie: UiMovie) {
        movie.status  = !movie.status
    }
}