package com.chibisov.movieinfoapplication.domain

import com.chibisov.movieinfoapplication.data.models.UiMovie

sealed class StateMovie {

    class Progress(): StateMovie()
    class Successful(val uiMovie: UiMovie): StateMovie()
    class Fail(val e: String) :StateMovie()
}