package com.chibisov.movieinfoapplication.domain

import androidx.lifecycle.LiveData
import com.chibisov.movieinfoapplication.data.Repository
import com.chibisov.movieinfoapplication.data.models.UiMovie
import com.chibisov.movieinfoapplication.core.CallbackDataList
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception

class BaseInteractor(private val repository: Repository) {

    sealed class Result {
        data class Success(val movieList:  Observable<List<UiMovie>>): Result()
        data class Error(val e: Throwable): Result()
    }


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

    fun showNetListRx(): Result = try {
        repository.getNetListRx().let {
            Result.Success(it)
        }
    } catch (e: Exception){
        Result.Error(e)
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