package com.chibisov.movieinfoapplication


import androidx.recyclerview.widget.DiffUtil
import com.chibisov.movieinfoapplication.adapter.util.MovieDiffUtil
import com.chibisov.movieinfoapplication.core.Observable
import com.chibisov.movieinfoapplication.core.Observer
import com.chibisov.movieinfoapplication.data.Repository
import com.chibisov.movieinfoapplication.data.models.Movie
import com.chibisov.movieinfoapplication.data.models.UiMovie
import java.util.*
import kotlin.collections.ArrayList

class Communication(private val repository: Repository): Observable {

    override val observers: ArrayList<Observer> = ArrayList()

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
    fun getUIMoviesList(): List<UiMovie> {
        setListOfMovies()
        return listOfMovies
    }

    fun setListOfMovies() {
        listOfMovies = repository.showMovies()
    }

    fun getFavMovies() = listOfFavMovies

    fun setListOfMovies(list: List<UiMovie>) {
        listOfFavMovies = list
    }

    fun showFavorites(list: List<UiMovie>): List<UiMovie>{
        val callback = MovieDiffUtil(getFavMovies(), list)
        //diffResult = DiffUtil.calculateDiff(callback)
        setdiffResult(callback)
        //listOfFavMovies = list
        setListOfMovies(list)
        return listOfFavMovies
    }


    fun setdiffResult(t: MovieDiffUtil) {
        diffResult = DiffUtil.calculateDiff(t)
    }

    fun getDiffResult(): DiffUtil.DiffResult {
        return diffResult
    }


}