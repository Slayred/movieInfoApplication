package com.chibisov.movieinfoapplication.data

import com.chibisov.movieinfoapplication.data.models.UiMovie


interface CacheDataSource: DataSource {
    fun addFavItem(movie: UiMovie)
    fun searchFavItem(movie: UiMovie): Boolean
    fun deleteFavItem(movie: UiMovie)
    fun addCheckedItem(movie: UiMovie)
    fun searchCheckedItem(movie: UiMovie): Boolean
}

object MoviesCacheFavorites: CacheDataSource {

    private val favoriteList = arrayListOf<UiMovie>()
    private val checkedList = arrayListOf<UiMovie>()


    override fun getList(): ArrayList<UiMovie> {
        return favoriteList
    }

    override fun addFavItem(movie: UiMovie) {
        favoriteList.add(movie)
    }

    override fun searchFavItem(movie: UiMovie): Boolean {
        val t  = (favoriteList.find {
                it.id == movie.id
            } != null)
    return t
    }

    override fun deleteFavItem(movie: UiMovie) {
        favoriteList.remove(favoriteList.find {
            it.id == movie.id
        })

    }

    override fun addCheckedItem(movie: UiMovie) {
        checkedList.add(movie)
    }

    override fun searchCheckedItem(movie: UiMovie): Boolean {
        val t = checkedList.remove(checkedList.find {
            it.id == movie.id
        })
        return t
    }


}

