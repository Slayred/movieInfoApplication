package com.chibisov.movieinfoapplication.viewmodels

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.chibisov.movieinfoapplication.data.models.UiMovie
import com.chibisov.movieinfoapplication.domain.BaseInteractor
import com.chibisov.movieinfoapplication.domain.Communication

class FavoriteMovieListViewModel(
    val communication: Communication,
    private val interactor: BaseInteractor
): BaseViewModel, ViewModel() {

    override fun observe(owner: LifecycleOwner, observer: Observer<List<UiMovie>>) {
        communication.observe(owner, observer)
    }

    override fun changeStatus(movie: UiMovie) {
        interactor.changeStatus(movie)
        showList()
    }

    override fun showList() {
        communication.showUiMovieList(interactor.showFavorites())
    }

    override fun addCheckedItem(movie: UiMovie) {
        interactor.addCheckedItem(movie)
        showList()
    }
}