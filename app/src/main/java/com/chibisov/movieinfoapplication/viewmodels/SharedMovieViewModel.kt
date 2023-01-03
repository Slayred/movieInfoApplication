package com.chibisov.movieinfoapplication.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.chibisov.movieinfoapplication.data.models.UiMovie
import com.chibisov.movieinfoapplication.domain.StateMovie
import com.chibisov.movieinfoapplication.domain.interactor.MovieInfoInteractor
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SharedMovieViewModel(
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


    fun shoMovieInfoRx(id: Int) {
        movieInfoStateLiveDataRx.value = StateMovie.Progress()
        compositeDisposable.add(
            interactor.showMovieInfoRx(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    movieInfoStateLiveDataRx.value = StateMovie.Successful(result)
                    writeToDb(result)
                }, { result ->
                    movieInfoStateLiveDataRx.value = StateMovie.Fail(result.message.toString()).also {
                        Log.d("DB","${result.message}")
                    }
                }
                )
        )
    }

    fun showMovieInfoCr(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            movieInfoStateLiveDataRx.value = StateMovie.Progress()

        }
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    private fun writeToDb(uiMovie: UiMovie) {
        Observable.fromCallable {
            interactor.saveToDbRx(uiMovie)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe( {
                Log.d("BD", "SUCCESSFUL WRITE")
            },{
                Log.d("BD", "FAIL IS ${it.message}")
            }
            )
    }
}