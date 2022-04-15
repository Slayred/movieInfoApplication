package com.chibisov.movieinfoapplication.nonuse

import com.chibisov.movieinfoapplication.data.models.UiMovie

interface CallbackData {
    fun provideData(uiMovieList: ArrayList<UiMovie>)
}