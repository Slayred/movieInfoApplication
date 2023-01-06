package com.chibisov.movieinfoapplication.viewmodels

import android.util.Log
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
import kotlinx.coroutines.withContext

class MovieListViewModel(
    val communication: Communication,
    private val interactor: BaseInteractor
) : ViewModel(), BaseViewModel {


    override fun observe(owner: LifecycleOwner, observer: Observer<List<UiMovie>>) {
        communication.observe(owner, observer)
    }

    override fun changeStatus(movie: UiMovie) {
        viewModelScope.launch(Dispatchers.IO) {
            interactor.changeStatus(movie)
        }


    }

    override fun addCheckedItem(id: Int) {
        viewModelScope.launch(Dispatchers.Main){
            interactor.addCheckedItem(id)
        }
    }


    override fun showList() {
        viewModelScope.launch(Dispatchers.Main) {
            communication.showUiMovieList(interactor.showNetListCr())
        }
    }


    override fun onCleared() {
        super.onCleared()
    }

}