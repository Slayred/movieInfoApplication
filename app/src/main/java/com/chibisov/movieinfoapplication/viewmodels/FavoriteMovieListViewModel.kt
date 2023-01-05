package com.chibisov.movieinfoapplication.viewmodels

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chibisov.movieinfoapplication.data.models.UiMovie
import com.chibisov.movieinfoapplication.domain.interactor.BaseInteractor
import com.chibisov.movieinfoapplication.domain.Communication
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FavoriteMovieListViewModel(
    val communication: Communication,
    private val interactor: BaseInteractor
): BaseViewModel, ViewModel() {

    private val compositeDisposable = CompositeDisposable()


    override fun observe(owner: LifecycleOwner, observer: Observer<List<UiMovie>>) {
        communication.observe(owner, observer)
    }

    override fun changeStatus(movie: UiMovie) {
        compositeDisposable.add(Observable.fromCallable {
            interactor.changeStatus(movie)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                showList()
            }
        )
    }

    override fun showList() {
        compositeDisposable.add(Observable.fromCallable {
            interactor.showFavorites()
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                communication.showUiMovieList(it)
            }
        )
    }

    fun showFavorites(){
        viewModelScope.launch (Dispatchers.Main) {
            communication.showUiMovieList(interactor.showFavoritesCr())
        }
    }



    override fun addCheckedItem(movie: UiMovie) {
        interactor.addCheckedItem(movie)
        showList()
    }

    override fun showListCr() {
        TODO("Not yet implemented")
    }


}