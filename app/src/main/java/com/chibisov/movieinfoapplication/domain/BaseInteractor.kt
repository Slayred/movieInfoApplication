package com.chibisov.movieinfoapplication.domain

import com.chibisov.movieinfoapplication.data.Repository
import com.chibisov.movieinfoapplication.data.models.Movie
import com.chibisov.movieinfoapplication.data.models.UiMovie

class BaseInteractor(val repository: Repository) {

    fun changeStatus(movie: UiMovie) {
        movie.status  = !movie.status
    }

    fun changeChecked(movie: UiMovie){
        movie.checked = !movie.checked
    }
}