package com.chibisov.movieinfoapplication.domain.interactor

import com.chibisov.movieinfoapplication.data.Repository
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
        return repository.showFavorites()
    }


    fun addCheckedItem(movie: UiMovie) {
//        repository.addCheckedItem(movie)
    }

}