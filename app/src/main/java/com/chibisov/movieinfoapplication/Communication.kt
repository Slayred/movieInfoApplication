package com.chibisov.movieinfoapplication

import com.chibisov.movieinfoapplication.data.Repository
import com.chibisov.movieinfoapplication.data.models.Movie

class Communication {

    fun getData(): List<Movie> = Repository.movieList

    fun changeStatus(movie: Movie) {
        Repository.movieList.find {

        }
    }


}