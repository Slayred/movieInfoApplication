package com.chibisov.movieinfoapplication.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.chibisov.movieinfoapplication.data.models.UiMovie
import com.chibisov.movieinfoapplication.domain.StateMovie
import com.chibisov.movieinfoapplication.domain.interactor.MovieInfoInteractor
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class MovieInfoViewModel(
    private val interactor: MovieInfoInteractor
) : ViewModel() {

    private var movieId = 0

    private var movieInfoStateLiveDataRx = MutableLiveData<StateMovie>(StateMovie.Progress())

    private val compositeDisposable = CompositeDisposable()

    fun setMovieID(id: Int) {
        this.movieId = id
    }


    fun observeStateMovieRx(owner: LifecycleOwner, observer: Observer<StateMovie>) {
        movieInfoStateLiveDataRx.observe(owner, observer)
    }


    fun showMovieInfoCr(id: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            movieInfoStateLiveDataRx.value = StateMovie.Progress()
            try {
                val movieInfo = interactor.showMovieInfoCr(id)
                movieInfo.checked = true
                movieInfoStateLiveDataRx.value =
                    StateMovie.Successful(movieInfo)
                saveToDbMovieInfoCR(movieInfo)
            } catch (e: UnknownHostException) {
                Log.d("ERROR", "Error is ${e.message}")
                movieInfoStateLiveDataRx.value = StateMovie.Fail("No Internet")
            } catch (e: Exception) {
                Log.d("ERROR", "Error is ${e.message}")
                movieInfoStateLiveDataRx.value = StateMovie.Fail("Something gone wrong")
            }
        }
    }



    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun changeStatus(id: Int, status: Boolean) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                interactor.changeMovieStatus(id, status)
                Log.d("DB", "Change Status")
            }
        } catch (e: Exception) {
            Log.d("DB", "ERROR message: ${e.message}")
        }


    }

    fun saveToDbMovieInfoCR(movie: UiMovie) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                Log.d("DB", "SAVE TO DB: ${movie.toString()}")
                interactor.saveToDbCr(movie)
            }
        } catch (e: Exception){
            Log.d("DB", "Something go wrond, ${e.message}")
        }

    }

}