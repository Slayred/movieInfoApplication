package com.chibisov.movieinfoapplication.viewmodels

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.chibisov.movieinfoapplication.data.models.UiMovie

interface BaseViewModel {

    fun observe(owner: LifecycleOwner, observer: Observer<List<UiMovie>>)
    fun changeStatus(movie: UiMovie)
    fun showUIList()
    fun showFavorites()
    fun addCheckedItem(movie: UiMovie)

}
