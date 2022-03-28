package com.chibisov.movieinfoapplication.data

import android.util.Log
import com.chibisov.movieinfoapplication.data.models.UiMovie
import kotlin.collections.ArrayList

class Repository(private val cacheDataSource: CacheDataSource,
                 private val netDataSource: NetDataSource) {

   fun showMovies() :ArrayList<UiMovie> {
//        val t = netDataSource.getList().map {
//            if(searchFavorites(it)) it.changeStatus()
//        }
       val t = netDataSource.getList()
        for( k in t){
            if(searchFavorites(k)){
                k.changeStatus()
            }
        }
       for (l in t) {
           if(searchCheckedItem(l)){
               l.checked = true
           }
       }
        return t
    }

    fun showFavorites() = cacheDataSource.getList()
    fun addFavorites(movie: UiMovie) = cacheDataSource.addFavItem(movie)
    fun removeFavorites(movie: UiMovie) = cacheDataSource.deleteFavItem(movie)
    fun searchFavorites(movie: UiMovie) = cacheDataSource.searchFavItem(movie)
    fun addCheckedItem(movie: UiMovie) = cacheDataSource.addCheckedItem(movie)
    fun searchCheckedItem(movie: UiMovie) = cacheDataSource.searchCheckedItem(movie)

}