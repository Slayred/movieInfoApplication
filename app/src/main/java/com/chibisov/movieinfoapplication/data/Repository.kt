package com.chibisov.movieinfoapplication.data

import com.chibisov.movieinfoapplication.data.Favorites
import com.chibisov.movieinfoapplication.data.Movies
import com.chibisov.movieinfoapplication.data.models.UiMovie

class Repository {

    private val netDataSource = Movies
    private val cacheDataSource = MoviesCache
    private val favorites = Favorites()

    fun showMovies() = netDataSource.movieList.map { it.to() }

    fun showFavorites() = MoviesCache.favoriteList
    fun addFavorites(movie: UiMovie) = MoviesCache.favoriteList.add(movie)
    fun removeFavorites(movie: UiMovie) = MoviesCache.favoriteList.remove(movie)


    fun checkedList() = MoviesCache.checkedList
    fun addCheckedList(movie: UiMovie) = MoviesCache.checkedList.add(CheckedMovie(movie.id))
    fun removeCheckedList(movie: UiMovie) = MoviesCache.checkedList.remove(CheckedMovie(movie.id))
    fun searchChecked(movie: UiMovie) = MoviesCache.checkedList.find {
        it.id == movie.id
    }
}