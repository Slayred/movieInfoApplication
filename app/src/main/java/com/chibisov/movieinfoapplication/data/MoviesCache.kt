package com.chibisov.movieinfoapplication.data

import com.chibisov.movieinfoapplication.data.models.UiMovie

object MoviesCache {

    val favoriteList = mutableListOf<UiMovie>()
    val checkedList = mutableListOf<CheckedMovie>()
}
