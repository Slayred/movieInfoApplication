package com.chibisov.movieinfoapplication.data

import com.chibisov.movieinfoapplication.data.Favorites
import com.chibisov.movieinfoapplication.data.Movies

class Repository {

    private val netDataSource = Movies
    private val cacheDataSource = MoviesCache
    private val favorites = Favorites()

    fun showMovies() = netDataSource.movieList.map { it.to() }

    fun showFavorites() = MoviesCache.favoriteList

    fun checkedList() = MoviesCache.checkedList

}