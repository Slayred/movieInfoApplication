package com.chibisov.movieinfoapplication.domain

import com.chibisov.movieinfoapplication.core.CallbackData
import com.chibisov.movieinfoapplication.core.CallbackStateMovie
import com.chibisov.movieinfoapplication.data.Repository

class MovieInteractor(private val repository: Repository) {


    fun showMovieInfo(callbackData: CallbackData, id: Int){
        repository.getMovieInfo(callbackData, id)
    }

    fun showStateMovieInfo(callbackStateMovie: CallbackStateMovie, id: Int){
        repository.getStateMovieInfo(callbackStateMovie, id)
    }

}