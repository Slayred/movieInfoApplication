package com.chibisov.movieinfoapplication.domain

import androidx.lifecycle.LiveData
import com.chibisov.movieinfoapplication.data.Repository
import com.chibisov.movieinfoapplication.data.models.UiMovie
import com.chibisov.movieinfoapplication.core.CallbackDataList
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

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

    fun showNetListRx() = repository.getNetListRx()



    fun showFavorites(): List<UiMovie> {
        return repository.showFavorites()
    }



    fun addCheckedItem(movie: UiMovie) {
//        repository.addCheckedItem(movie)
    }

}