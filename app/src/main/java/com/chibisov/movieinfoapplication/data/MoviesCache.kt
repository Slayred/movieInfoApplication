package com.chibisov.movieinfoapplication.data

import com.chibisov.movieinfoapplication.data.models.UiMovie


interface CacheDataSource: DataSource {
    fun addItem(movie: UiMovie)
    fun searchItem(movie: UiMovie): Boolean
    fun deleteItem(movie: UiMovie)
}

object MoviesCacheFavorites: CacheDataSource {

    private val favoriteList = arrayListOf<UiMovie>()
    override fun getList(): ArrayList<UiMovie> {
        return favoriteList
    }

    override fun addItem(movie: UiMovie) {
        favoriteList.add(movie)
    }

    override fun searchItem(movie: UiMovie): Boolean {
        val t  = (favoriteList.find {
                it.id == movie.id
            } != null)
    return t
    }

    override fun deleteItem(movie: UiMovie) {
        favoriteList.remove(favoriteList.find {
            it.id == movie.id
        })

    }


}

object MoviesCacheChecked {

    val favoriteList = ArrayList<UiMovie>()
}
