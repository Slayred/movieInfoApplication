package com.chibisov.movieinfoapplication.viewmodels

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.chibisov.movieinfoapplication.domain.Communication
import com.chibisov.movieinfoapplication.presentation.MovieListFragment

class HomeViewModel(): ViewModel() {


    val currentName: MutableLiveData<Fragment> = MutableLiveData<Fragment>()

    fun observe(owner: LifecycleOwner, observer: Observer<Fragment>){
        currentName.observe(owner, observer)
    }

    init {
        Log.d("BACKSTACK", "WIEWMODEL IS INIT")
        currentName.postValue(MovieListFragment())
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("BACKSTACK", "WIEWMODEL IS CLEARED")
    }

}