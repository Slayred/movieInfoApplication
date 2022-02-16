package com.chibisov.movieinfoapplication.data

import com.chibisov.movieinfoapplication.data.models.UiMovie

class Repository(private val dataSource: CacheDataSource,
            private val netDataSource: NetDataSource) {


    //private val cacheDataSource = dataSource

    fun showMovies() = netDataSource.getList()

    fun showFavorites() = dataSource.getList()
    fun addFavorites(movie: UiMovie) = dataSource.addItem(movie)
    fun removeFavorites(movie: UiMovie) = dataSource.deleteItem(movie)
    fun searchFavorites(movie: UiMovie) = dataSource.searchItem(movie)


//    fun checkedList() = MoviesCache.checkedList
//    fun addCheckedList(movie: UiMovie) = MoviesCache.checkedList.add(CheckedMovie(movie.id))
//    fun removeCheckedList(movie: UiMovie) = MoviesCache.checkedList.remove(CheckedMovie(movie.id))
//    fun searchChecked(movie: UiMovie) = MoviesCache.checkedList.find {
//        it.id == movie.id
//    }
}