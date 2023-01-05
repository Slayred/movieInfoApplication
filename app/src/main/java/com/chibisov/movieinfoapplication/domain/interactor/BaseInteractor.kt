package com.chibisov.movieinfoapplication.domain.interactor

import com.chibisov.movieinfoapplication.data.repositories.impl.Repository
import com.chibisov.movieinfoapplication.data.models.UiMovie
import io.reactivex.Observable


class BaseInteractor(private val repository: Repository) {


    fun changeStatus(movie: UiMovie) {
        if (repository.searchFavorites(movie)) {
            repository.removeFavorites(movie)
        } else {
            repository.addFavorites(movie)
        }

    }

    fun showNetListRX(): Observable<List<UiMovie>> {
        return repository.getNetListRx()
    }


    fun showFavorites(): List<UiMovie> {
        return repository.showFavoritesRx()
    }

    suspend fun showFavoritesCr(): List<UiMovie> {
        return repository.getFavoritesCr()
    }


    fun addCheckedItem(movie: UiMovie) {
//        repository.addCheckedItem(movie)
    }

    suspend fun showNetListCr(): List<UiMovie> {
        return repository.getNetListCr()
    }

}