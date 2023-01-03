package com.chibisov.movieinfoapplication.viewmodels

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel

class HomeViewModel(): ViewModel() {


    val currentName: MutableLiveData<String> = MutableLiveData<String>()

    init {
        Log.d("BACKSTACK", "WIEWMODEL IS INIT")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("BACKSTACK", "WIEWMODEL IS CLEARED")
    }

}