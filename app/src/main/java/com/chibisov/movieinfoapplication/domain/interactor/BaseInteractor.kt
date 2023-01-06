package com.chibisov.movieinfoapplication.domain.interactor

import com.chibisov.movieinfoapplication.data.models.UiMovie
import com.chibisov.movieinfoapplication.data.repositories.impl.Repository


class BaseInteractor(private val repository: Repository) {


    fun changeStatus(movie: UiMovie) {
        if (repository.searchFavorites(movie)) {
            repository.removeFavorites(movie)
        } else {
            repository.addFavorites(movie)
        }

    }


    suspend fun showFavoritesCr(): List<UiMovie> {
        return repository.getFavoritesCr()
    }


    suspend fun addCheckedItem(id: Int) {
        repository.addCheckedItem(id)
    }

    suspend fun showNetListCr(): List<UiMovie> {
        return repository.getNetListCr()
    }

}