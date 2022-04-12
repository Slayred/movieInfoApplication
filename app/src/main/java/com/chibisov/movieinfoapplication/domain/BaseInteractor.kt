package com.chibisov.movieinfoapplication.domain

import com.chibisov.movieinfoapplication.data.Repository
import com.chibisov.movieinfoapplication.data.models.UiMovie

class BaseInteractor(private val repository: Repository) {


    fun changeStatus(movie: UiMovie) {
        if (repository.searchFavorites(movie)) {
            repository.removeFavorites(movie)
        } else {
            repository.addFavorites(movie)
        }
    }

    fun showUIList(): ArrayList<UiMovie> {
        return repository.showMovies()
    }

    fun showFavorites(): ArrayList<UiMovie> {
        return repository.showFavorites()
    }

    fun addCheckedItem(movie: UiMovie) {
        repository.addCheckedItem(movie)
    }

    fun searchCheckedItem(movie: UiMovie) = repository.searchCheckedItem(movie)
}