package com.chibisov.movieinfoapplication.data

import com.chibisov.movieinfoapplication.core.CallbackData
import com.chibisov.movieinfoapplication.data.models.UiMovie
import com.chibisov.movieinfoapplication.core.CallbackDataList
import com.chibisov.movieinfoapplication.core.CallbackStateMovie
import com.chibisov.movieinfoapplication.data.converter.MovieInfoConverter
import com.chibisov.movieinfoapplication.data.converter.MovieListConverter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlin.collections.ArrayList

class Repository(
    private val movieCacheDataSource: MovieCacheDataSource,
    private val movieNetDataSource: MovieNetDataSource,
    private val movieInfoConverter: MovieInfoConverter,
    private val movieListConverter: MovieListConverter
) {

    private val compositeDisposable = CompositeDisposable()

//    fun showMovies(): ArrayList<UiMovie> {
//        val listOfMovies = netDataSource.getList()
//        for (movie in listOfMovies) {
//            if (searchFavorites(movie)) {
//                movie.changeStatus()
//            }
//        }
//        for (movie in listOfMovies) {
//            if (searchCheckedItem(movie)) {
//                movie.checked = true
//            }
//        }
//        return listOfMovies
//    }
    fun getNetList(callbackDataList: CallbackDataList) {
        movieNetDataSource.getNewList(callbackDataList)
    }

    fun getStateMovieInfo(callbackStateMovie: CallbackStateMovie, id: Int) {
        movieNetDataSource.showStateMovieInfo(callbackStateMovie, id)
    }

    fun addFavorites(movie: UiMovie) {
        movieCacheDataSource.insertMovieItems(movieListConverter.toEntity(movie))

//        compositeDisposable.add(Observable.fromCallable { movieCacheDataSource.insertMovieItems(movieListConverter.toEntity(movie)) }
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe()
//        )
//        compositeDisposable.dispose()
    }

    fun showFavorites() = movieCacheDataSource.getMovieItems().map {
        movieListConverter.toUi(it)
    }

    fun searchFavorites(movie: UiMovie):Boolean {
        return movieCacheDataSource.searchItem(movieListConverter.toEntity(movie)) != null

    }

    fun removeFavorites(movie: UiMovie) {
        movieCacheDataSource.deleteFavItem(movieListConverter.toEntity(movie))
    }

//    fun showFavorites() = cacheDataSource.getList()
//    fun addFavorites(movie: UiMovie) = cacheDataSource.addFavItem(movie)
//    fun removeFavorites(movie: UiMovie) = cacheDataSource.deleteFavItem(movie)
//    fun searchFavorites(movie: UiMovie) = cacheDataSource.searchFavItem(movie)
//    fun addCheckedItem(movie: UiMovie) = cacheDataSource.addCheckedItem(movie)
//    fun searchCheckedItem(movie: UiMovie) = cacheDataSource.searchCheckedItem(movie)

}