package com.chibisov.movieinfoapplication.viewmodels

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chibisov.movieinfoapplication.data.models.UiMovie
import com.chibisov.movieinfoapplication.domain.Communication
import com.chibisov.movieinfoapplication.domain.interactor.BaseInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FavoriteMovieListViewModel(
    val communication: Communication,
    private val interactor: BaseInteractor
) : BaseViewModel, ViewModel() {


    override fun observe(owner: LifecycleOwner, observer: Observer<List<UiMovie>>) {
        communication.observe(owner, observer)
    }

    override fun changeStatus(movie: UiMovie) {
        viewModelScope.launch(Dispatchers.IO) {
            interactor.changeStatus(movie)
            showList()
        }

    }


    override fun addCheckedItem(id: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            interactor.addCheckedItem(id)
            showList()
        }


    }

    override fun showList() {
        viewModelScope.launch(Dispatchers.Main) {
            communication.showUiMovieList(interactor.showFavoritesCr())
        }
    }


}