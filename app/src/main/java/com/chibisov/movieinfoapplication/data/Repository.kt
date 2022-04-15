package com.chibisov.movieinfoapplication.data

import com.chibisov.movieinfoapplication.data.models.UiMovie
import com.chibisov.movieinfoapplication.nonuse.CallbackData
import kotlin.collections.ArrayList

class Repository(
    private val cacheDataSource: CacheDataSource,
    private val netDataSource: NetDataSource,
    private val movieNetDataSource: MovieNetDataSource
) {

    fun showMovies(): ArrayList<UiMovie> {
        val listOfMovies = netDataSource.getList()
        for (movie in listOfMovies) {
            if (searchFavorites(movie)) {
                movie.changeStatus()
            }
        }
        for (movie in listOfMovies) {
            if (searchCheckedItem(movie)) {
                movie.checked = true
            }
        }
        return listOfMovies
    }
    fun getNetList(callbackData: CallbackData) {
        movieNetDataSource.getNewList(callbackData)
    }

    fun showFavorites() = cacheDataSource.getList()
    fun addFavorites(movie: UiMovie) = cacheDataSource.addFavItem(movie)
    fun removeFavorites(movie: UiMovie) = cacheDataSource.deleteFavItem(movie)
    fun searchFavorites(movie: UiMovie) = cacheDataSource.searchFavItem(movie)
    fun addCheckedItem(movie: UiMovie) = cacheDataSource.addCheckedItem(movie)
    fun searchCheckedItem(movie: UiMovie) = cacheDataSource.searchCheckedItem(movie)

}