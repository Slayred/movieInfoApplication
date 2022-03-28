package com.chibisov.movieinfoapplication


import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import com.chibisov.movieinfoapplication.adapter.util.MovieDiffUtil
import com.chibisov.movieinfoapplication.core.Observable
import com.chibisov.movieinfoapplication.core.Observer
import com.chibisov.movieinfoapplication.data.models.UiMovie
import kotlin.collections.ArrayList

class Communication: Observable {

    override val observers: ArrayList<Observer> = ArrayList()

    private lateinit var diffResult: DiffUtil.DiffResult

    private var listOfMovies = arrayListOf<UiMovie>()

    fun getUIMoviesList(): ArrayList<UiMovie> {
        return listOfMovies
    }


    private fun setUIMoviesList(list: ArrayList<UiMovie>) {
        this.listOfMovies = list
    }

    fun showUiMovieList(list: ArrayList<UiMovie>) {
        val callback = MovieDiffUtil(listOfMovies, list)
        setUIMoviesList(list)
        diffResult = DiffUtil.calculateDiff(callback)
        sendUpdateEvent()
    }

    fun getDiffResult(): DiffUtil.DiffResult {
        return diffResult
    }

}