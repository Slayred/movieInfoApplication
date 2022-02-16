package com.chibisov.movieinfoapplication.data

import com.chibisov.movieinfoapplication.data.models.UiMovie


interface CacheDataSource: NetDataSource {
    //fun getList(): List<UiMovie>
    fun addItem(movie: UiMovie)
    fun searchItem(movie: UiMovie): UiMovie
    fun deleteItem(movie: UiMovie)
}

object MoviesCacheFavorites: CacheDataSource {

    private val favoriteList = mutableListOf<UiMovie>()
    override fun getList(): List<UiMovie> {
        return favoriteList
    }

    override fun addItem(movie: UiMovie) {
        favoriteList.add(movie)
    }

    override fun searchItem(movie: UiMovie): UiMovie {
        return favoriteList.find {
            it.id == movie.id
        }!!
    }

    override fun deleteItem(movie: UiMovie) {
        favoriteList.remove(movie)
    }


}
object MoviesCacheChecked {

    val favoriteList = mutableListOf<UiMovie>()
}
