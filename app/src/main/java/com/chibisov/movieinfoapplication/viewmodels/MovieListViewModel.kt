package com.chibisov.movieinfoapplication.viewmodels

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.chibisov.movieinfoapplication.data.models.UiMovie
import com.chibisov.movieinfoapplication.domain.BaseInteractor
import com.chibisov.movieinfoapplication.domain.Communication
import com.chibisov.movieinfoapplication.core.CallbackDataList

class MovieListViewModel(
    val communication: Communication,
    private val interactor: BaseInteractor
) : ViewModel(), BaseViewModel {


    override fun observe(owner: LifecycleOwner, observer: Observer<List<UiMovie>>){
        communication.observe(owner, observer)
    }

    override fun changeStatus(movie: UiMovie) {
        interactor.changeStatus(movie)
        showList()
    }

    override fun showList() {
        communication.showUiMovieList(interactor.showUIList())
    }

    fun showNetList() {
        interactor.showNetList(object : CallbackDataList {
            override fun provideData(uiMovieList: ArrayList<UiMovie>) {
                communication.showUiMovieList(uiMovieList)
            }

        })
    }


    override fun addCheckedItem(movie: UiMovie) {
        interactor.addCheckedItem(movie)
    }


}