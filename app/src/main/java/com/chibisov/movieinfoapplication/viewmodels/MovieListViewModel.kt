package com.chibisov.movieinfoapplication.viewmodels

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.chibisov.movieinfoapplication.data.models.UiMovie
import com.chibisov.movieinfoapplication.domain.BaseInteractor
import com.chibisov.movieinfoapplication.domain.Communication

class MovieListViewModel(
    val communication: Communication,
    private val interactor: BaseInteractor
) : ViewModel(), BaseViewModel {


    override fun observe(owner: LifecycleOwner, observer: Observer<List<UiMovie>>){
        communication.observe(owner, observer)
    }

    override fun changeStatus(movie: UiMovie) {
        interactor.changeStatus(movie)
        communication.showUiMovieList(interactor.showUIList())
    }

    override fun showUIList() {
        communication.showUiMovieList(interactor.showUIList())
    }

    override fun showFavorites() {
        communication.getUIMoviesList()
    }


    override fun addCheckedItem(movie: UiMovie) {
        interactor.addCheckedItem(movie)
    }


}