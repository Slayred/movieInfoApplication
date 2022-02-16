package com.chibisov.movieinfoapplication

import androidx.recyclerview.widget.DiffUtil
import com.chibisov.movieinfoapplication.adapter.util.MovieDiffUtil
import com.chibisov.movieinfoapplication.data.Repository
import com.chibisov.movieinfoapplication.data.models.Movie
import com.chibisov.movieinfoapplication.data.models.UiMovie

class Communication(private val repository: Repository) {

    private lateinit var diffResult: DiffUtil.DiffResult

    private var listOfMovies = emptyList<UiMovie>()

    private var listOfFavMovies = emptyList<UiMovie>()

    fun showUIMovie(list: List<UiMovie>): List<UiMovie> {
        val callback = MovieDiffUtil(getUIMoviesList(), list)
        diffResult = DiffUtil.calculateDiff(callback)
        listOfMovies = repository.showMovies()
        return getUIMoviesList()
    }

    fun changeStatus(movie: Movie) {
        //repository.
    }
    fun getUIMoviesList() = listOfMovies

    fun getFavMovies() = listOfFavMovies

    fun showFavorites(list: List<UiMovie>): List<UiMovie>{
        val callback = MovieDiffUtil(getFavMovies(), list)
        diffResult = DiffUtil.calculateDiff(callback)
        listOfFavMovies = repository.showFavorites()
        return listOfMovies
    }


}