package com.chibisov.movieinfoapplication.core

import com.chibisov.movieinfoapplication.data.models.UiMovie
import com.chibisov.movieinfoapplication.domain.StateMovie

interface CallbackDataList {
    fun provideData(uiMovieList: ArrayList<UiMovie>)
}

interface CallbackData {
    fun provideData(uiMovie: UiMovie)
}

interface CallbackStateMovie {
    fun provideStateData(stateMovie: StateMovie)
}