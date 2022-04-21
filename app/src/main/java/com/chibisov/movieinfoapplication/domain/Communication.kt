package com.chibisov.movieinfoapplication.domain


import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import com.chibisov.movieinfoapplication.presentation.adapter.util.MovieDiffUtil
import com.chibisov.movieinfoapplication.core.Observable
import com.chibisov.movieinfoapplication.core.Observer
import com.chibisov.movieinfoapplication.data.models.UiMovie
import kotlin.collections.ArrayList

class Communication {

    private lateinit var diffResult: DiffUtil.DiffResult

    private var listOfMoviesLiveData = MutableLiveData<List<UiMovie>>()

    private var listOfMoviesState = MutableLiveData<List<StateMovie>>()

    fun observe(owner: LifecycleOwner, observer: androidx.lifecycle.Observer<List<UiMovie>> ) {
        listOfMoviesLiveData.observe(owner, observer)
    }

    fun getUIMoviesList(): List<UiMovie> {
        return listOfMoviesLiveData.value?: emptyList()
    }

    private fun setUIMoviesList(list: List<UiMovie>) {
        listOfMoviesLiveData.value = list
    }

    fun showUiMovieList(list: List<UiMovie>) {
        val callback = MovieDiffUtil(getUIMoviesList(), list)
        diffResult = DiffUtil.calculateDiff(callback)
        setUIMoviesList(list)
    }

    fun getDiffResult(): DiffUtil.DiffResult {
        return diffResult
    }


}