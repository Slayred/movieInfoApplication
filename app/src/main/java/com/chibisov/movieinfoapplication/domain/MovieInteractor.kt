package com.chibisov.movieinfoapplication.domain

import com.chibisov.movieinfoapplication.core.CallbackStateMovie
import com.chibisov.movieinfoapplication.data.Repository

class MovieInteractor(private val repository: Repository) {


    fun showStateMovieInfo(callbackStateMovie: CallbackStateMovie, id: Int) {
        repository.getStateMovieInfo(callbackStateMovie, id)
    }

}