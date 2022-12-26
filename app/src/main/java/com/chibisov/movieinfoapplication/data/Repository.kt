package com.chibisov.movieinfoapplication.data

import com.chibisov.movieinfoapplication.core.CallbackDataList
import com.chibisov.movieinfoapplication.core.CallbackStateMovie
import com.chibisov.movieinfoapplication.core.Error
import com.chibisov.movieinfoapplication.data.converter.MovieInfoConverter
import com.chibisov.movieinfoapplication.data.converter.MovieListConverter
import com.chibisov.movieinfoapplication.data.models.UiMovie
import io.reactivex.Observable
import java.net.UnknownHostException

class Repository(
    private val movieCacheDataSource: MovieCacheDataSource,
    private val movieNetDataSource: MovieNetDataSource,
    private val movieInfoConverter: MovieInfoConverter,
    private val movieListConverter: MovieListConverter
) {


    fun getNetList(callbackDataList: CallbackDataList) {
        movieNetDataSource.getNewList(callbackDataList)
    }

    fun getNetListRx(): Observable<List<UiMovie>> = movieNetDataSource.getMovieListRX().map { kinopoiskMovieResponse ->
        kinopoiskMovieResponse.films.map {
            it.toUiMovie()
        }
    }

//    fun getNetListRx(): Observable<List<UiMovie>> {
//        val movieList = try {
//            movieNetDataSource.getMovieListRX()
//        } catch (e: Throwable) {
//            throw when (e) {
//                is UnknownHostException -> Error.NoConnection
//                else -> e
//            }
//        }
//        return movieList.map { kinopoiskMovieResponse ->
//            kinopoiskMovieResponse.films.map {
//                it.toUiMovie()
//            }
//        }
//    }

    fun getStateMovieInfo(callbackStateMovie: CallbackStateMovie, id: Int) {
        movieNetDataSource.showStateMovieInfo(callbackStateMovie, id)
    }

    fun addFavorites(movie: UiMovie) {
        movieCacheDataSource.insertMovieItems(movieListConverter.toEntity(movie))

    }

    fun showFavorites() = movieCacheDataSource.getMovieItems().map {
        movieListConverter.toUi(it)
    }


    fun searchFavorites(movie: UiMovie): Boolean {
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