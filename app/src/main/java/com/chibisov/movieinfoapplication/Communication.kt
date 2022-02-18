package com.chibisov.movieinfoapplication


import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import com.chibisov.movieinfoapplication.adapter.util.MovieDiffUtil
import com.chibisov.movieinfoapplication.core.Observable
import com.chibisov.movieinfoapplication.core.Observer
import com.chibisov.movieinfoapplication.data.Repository
import com.chibisov.movieinfoapplication.data.models.Movie
import com.chibisov.movieinfoapplication.data.models.UiMovie
import java.util.*
import kotlin.collections.ArrayList

class Communication: Observable {

    override val observers: ArrayList<Observer> = ArrayList()

    private lateinit var diffResult: DiffUtil.DiffResult

   private var listOfMovies = emptyList<UiMovie>()

    fun getUIMoviesList(): List<UiMovie> {
        return listOfMovies
    }

    fun setUIMoviesList(list: List<UiMovie>) {
        this.listOfMovies = list
        Log.d("TAG", "Communication list was set")
    }

    fun showUiMovieList(list: List<UiMovie>) {
        val callback = MovieDiffUtil(getUIMoviesList(), list)
        diffResult = DiffUtil.calculateDiff(callback)
        setUIMoviesList(list)
        //listOfMovies = list
        sendUpdateEvent()
    }


    fun setdiffResult(t: MovieDiffUtil) {
        diffResult = DiffUtil.calculateDiff(t)
    }

    fun getDiffResult(): DiffUtil.DiffResult {
        return diffResult
    }


}