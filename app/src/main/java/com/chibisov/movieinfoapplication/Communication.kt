package com.chibisov.movieinfoapplication

class Communication {

    fun getData(): List<Movie> = Repository.movieList

    fun changeStatus(movie: Movie) {
        Repository.movieList.find {

        }
    }


}