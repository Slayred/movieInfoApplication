package com.chibisov.movieinfoapplication.viewmodels

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.chibisov.movieinfoapplication.core.CallbackData
import com.chibisov.movieinfoapplication.core.CallbackStateMovie
import com.chibisov.movieinfoapplication.data.models.UiMovie
import com.chibisov.movieinfoapplication.domain.MovieInteractor
import com.chibisov.movieinfoapplication.domain.StateMovie

class SharedMovieViewModel(
    private val interactor: MovieInteractor
) : ViewModel() {

    private var movieStateLiveData = MutableLiveData<StateMovie>(StateMovie.Progress())
    private var movieId = 0

    fun setMovieID(id: Int) {
        this.movieId = id
    }

    fun observeStateMovie(owner: LifecycleOwner, observer: Observer<StateMovie>) {
        movieStateLiveData.observe(owner, observer)
    }

    fun showStateMovieInfo(id: Int = movieId) {
        movieStateLiveData.value = StateMovie.Progress()
        interactor.showStateMovieInfo(object : CallbackStateMovie{
            override fun provideStateData(stateMovie: StateMovie) {
                movieStateLiveData.value = stateMovie
            }

        }, id)
    }

}