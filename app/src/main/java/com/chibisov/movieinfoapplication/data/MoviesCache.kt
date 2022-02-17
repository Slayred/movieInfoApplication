package com.chibisov.movieinfoapplication.data

import com.chibisov.movieinfoapplication.data.models.UiMovie


interface CacheDataSource: DataSource {
    //fun getList(): List<UiMovie>
    fun addItem(movie: UiMovie)
    fun searchItem(movie: UiMovie): Boolean
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

    val favoriteList = mutableListOf<UiMovie>()
}
