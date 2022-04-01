package com.chibisov.movieinfoapplication.core

import android.util.Log

interface Observable {
    val observers: ArrayList<Observer>

    fun add(observer: Observer) {
        observers.add(observer)
        Log.d("TAG", "Add observer $observer")
    }

    fun remove(observer: Observer) {
        observers.remove(observer)
        Log.d("TAG", "Remove observer $observer")
    }

    fun sendUpdateEvent() {
        observers.forEach {
            it.update()
        }
    }
}