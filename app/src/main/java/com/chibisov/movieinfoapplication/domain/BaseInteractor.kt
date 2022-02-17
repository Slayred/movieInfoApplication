package com.chibisov.movieinfoapplication.domain

import androidx.recyclerview.widget.DiffUtil
import com.chibisov.movieinfoapplication.data.Repository
import com.chibisov.movieinfoapplication.data.models.Movie
import com.chibisov.movieinfoapplication.data.models.UiMovie

class BaseInteractor(private val repository: Repository) {


    fun changeStatus(movie: UiMovie) {
       if( repository.searchFavorites(movie)){
            movie.status = false
            repository.removeFavorites(movie)
       } else {
           movie.status = true
           repository.addFavorites(movie)
       }
    }

    fun showUIList() = repository.showMovies()

    fun showFavorites() = repository.showFavorites()
}