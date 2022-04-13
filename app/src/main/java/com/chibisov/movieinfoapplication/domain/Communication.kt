package com.chibisov.movieinfoapplication.domain


import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import com.chibisov.movieinfoapplication.adapter.util.MovieDiffUtil
import com.chibisov.movieinfoapplication.core.Observable
import com.chibisov.movieinfoapplication.core.Observer
import com.chibisov.movieinfoapplication.data.models.UiMovie
import kotlin.collections.ArrayList

class Communication : Observable {

    override val observers: ArrayList<Observer> = ArrayList()

    private lateinit var diffResult: DiffUtil.DiffResult

    private var listOfMoviesLiveData = MutableLiveData<ArrayList<UiMovie>>()

    fun observe(owner: LifecycleOwner, observer: androidx.lifecycle.Observer<List<UiMovie>> ) {
        listOfMoviesLiveData.observe(owner, observer)
    }

    fun getUIMoviesList(): ArrayList<UiMovie> {
        return listOfMoviesLiveData.value?: arrayListOf()
    }

    private fun setUIMoviesList(list: ArrayList<UiMovie>) {
        listOfMoviesLiveData.value = list
    }

    fun showUiMovieList(list: ArrayList<UiMovie>) {
        val callback = MovieDiffUtil(getUIMoviesList(), list)
        diffResult = DiffUtil.calculateDiff(callback)
        setUIMoviesList(list)
    }

    fun getDiffResult(): DiffUtil.DiffResult {
        return diffResult
    }

}