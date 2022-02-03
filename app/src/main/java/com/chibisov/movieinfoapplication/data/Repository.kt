package com.chibisov.movieinfoapplication.data

import com.chibisov.movieinfoapplication.data.Favorites
import com.chibisov.movieinfoapplication.data.Movies

class Repository {


    fun showMovies() = Movies.movieList

    fun showFavorites() = Favorites().showFavorites()

}