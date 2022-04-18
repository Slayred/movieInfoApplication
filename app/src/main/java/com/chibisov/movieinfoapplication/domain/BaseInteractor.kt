package com.chibisov.movieinfoapplication.domain

import com.chibisov.movieinfoapplication.data.Repository
import com.chibisov.movieinfoapplication.data.models.UiMovie
import com.chibisov.movieinfoapplication.core.CallbackDataList

class BaseInteractor(private val repository: Repository) {


    fun changeStatus(movie: UiMovie) {
        if (repository.searchFavorites(movie)) {
            repository.removeFavorites(movie)
        } else {
            repository.addFavorites(movie)
        }
    }

    fun showNetList(callbackDataList: CallbackDataList) {
        repository.getNetList(callbackDataList)
    }

//    fun showUIList(): ArrayList<UiMovie> {
//        return repository.showMovies()
//    }

    fun showFavorites(): ArrayList<UiMovie> {
        return repository.showFavorites()
    }

    fun addCheckedItem(movie: UiMovie) {
        repository.addCheckedItem(movie)
    }

}