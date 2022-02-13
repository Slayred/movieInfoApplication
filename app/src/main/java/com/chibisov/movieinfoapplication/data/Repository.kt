package com.chibisov.movieinfoapplication.data

import com.chibisov.movieinfoapplication.data.Favorites
import com.chibisov.movieinfoapplication.data.Movies

class Repository {

    private val netDataSource = Movies.movieList
    private val favorites = Favorites()

    fun showMovies() = netDataSource.map { it.to() }

    fun showFavorites() = Favorites().showFavorites()

}