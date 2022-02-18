package com.chibisov.movieinfoapplication.data

import android.util.Log
import com.chibisov.movieinfoapplication.data.models.UiMovie

class Repository(private val cacheDataSource: CacheDataSource,
                 private val netDataSource: NetDataSource) {

    fun showMovies() :List<UiMovie> {
//        netDataSource.getList().map {
//            if (searchFavorites(it)) {
//                Log.d("TAG", "Status FIND ${showFavorites().contains(it)}")
//                it.changeStatus()
//            } else {
//                Log.d("TAG", "Status NOT ${it.status}")
//                it
//            }
//        }
        var t = netDataSource.getList()
        for( k in t){
            if(searchFavorites(k)){
                k.changeStatus()
                Log.d("TAG", "NEW STATUS OF ${k.name} is ${k.status}")
            }
        }
        return t
    }

    fun showFavorites() = cacheDataSource.getList()
    fun addFavorites(movie: UiMovie) = cacheDataSource.addItem(movie)
    fun removeFavorites(movie: UiMovie) = cacheDataSource.deleteItem(movie)
    fun searchFavorites(movie: UiMovie) = cacheDataSource.searchItem(movie)




//    fun checkedList() = MoviesCache.checkedList
//    fun addCheckedList(movie: UiMovie) = MoviesCache.checkedList.add(CheckedMovie(movie.id))
//    fun removeCheckedList(movie: UiMovie) = MoviesCache.checkedList.remove(CheckedMovie(movie.id))
//    fun searchChecked(movie: UiMovie) = MoviesCache.checkedList.find {
//        it.id == movie.id
//    }
}