package com.chibisov.movieinfoapplication.viewmodels

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.chibisov.movieinfoapplication.domain.Communication

class HomeViewModel(): ViewModel() {

    var currentFragment = Fragment()::class.java.simpleName

    private val currentName: MutableLiveData<String> = MutableLiveData<String>(Fragment()::class.java.simpleName)

    fun observe(owner: LifecycleOwner, observer: Observer<String>){
        currentName.observe(owner, observer)
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("BACKSTACK", "WIEWMODEL IS CLEARED")
    }

}