package com.chibisov.movieinfoapplication.data

import com.chibisov.movieinfoapplication.data.models.UiMovie

class Repository {

    private val netDataSource = Movies
    private val cacheDataSource = MoviesCache

    fun showMovies() = netDataSource.movieList.map { it.to() }

    fun showFavorites() = cacheDataSource.favoriteList
    fun addFavorites(movie: UiMovie) = cacheDataSource.favoriteList.add(movie)
    fun removeFavorites(movie: UiMovie) = cacheDataSource.favoriteList.remove(movie)
    fun searchFavorites(movie: UiMovie) = cacheDataSource.favoriteList.find {
        it.id == movie.id
    }


    fun checkedList() = MoviesCache.checkedList
    fun addCheckedList(movie: UiMovie) = MoviesCache.checkedList.add(CheckedMovie(movie.id))
    fun removeCheckedList(movie: UiMovie) = MoviesCache.checkedList.remove(CheckedMovie(movie.id))
    fun searchChecked(movie: UiMovie) = MoviesCache.checkedList.find {
        it.id == movie.id
    }
}