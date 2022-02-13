package com.chibisov.movieinfoapplication

import com.chibisov.movieinfoapplication.data.Repository
import com.chibisov.movieinfoapplication.data.models.Movie
import com.chibisov.movieinfoapplication.data.models.UiMovie

class Communication(private val repository: Repository) {

    fun getData(): List<UiMovie> = repository.showMovies()

    fun changeStatus(movie: Movie) {
        //repository.
    }


}