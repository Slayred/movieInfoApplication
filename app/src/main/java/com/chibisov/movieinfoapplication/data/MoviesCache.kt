package com.chibisov.movieinfoapplication.data

import com.chibisov.movieinfoapplication.data.models.UiMovie


interface CacheDataSource : DataSource {
    fun addFavItem(movie: UiMovie)
    fun searchFavItem(movie: UiMovie): Boolean
    fun deleteFavItem(movie: UiMovie)
    fun addCheckedItem(movie: UiMovie)
    fun searchCheckedItem(movie: UiMovie): Boolean
}

object MoviesCacheFavorites : CacheDataSource {

    private val favoriteList = arrayListOf<UiMovie>()
    private val checkedList = arrayListOf<Int>()


    override fun getList(): ArrayList<UiMovie> {
        return favoriteList
    }

    override fun addFavItem(movie: UiMovie) {
        favoriteList.add(movie)
    }

    override fun searchFavItem(movie: UiMovie): Boolean {
        val favMovie = (favoriteList.find {
            it.id == movie.id
        } != null)
        return favMovie
    }

    override fun deleteFavItem(movie: UiMovie) {
        favoriteList.remove(favoriteList.find {
            it.id == movie.id
        })
    }

    override fun addCheckedItem(movie: UiMovie) {
        if (!searchCheckedItem(movie)) checkedList.add(movie.id)
    }

    override fun searchCheckedItem(movie: UiMovie): Boolean {
        return checkedList.contains(movie.id)
    }

}

