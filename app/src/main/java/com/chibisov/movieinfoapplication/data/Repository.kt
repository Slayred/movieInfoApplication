package com.chibisov.movieinfoapplication.data

import android.util.Log
import com.chibisov.movieinfoapplication.data.models.UiMovie
import kotlin.collections.ArrayList

class Repository(private val cacheDataSource: CacheDataSource,
                 private val netDataSource: NetDataSource) {

   fun showMovies() :ArrayList<UiMovie> {
        val t = netDataSource.getList()
        for( k in t){
            if(searchFavorites(k)){
                k.changeStatus()
                Log.d("TAG", "NEW STATUS OF ${k.name} is ${k.status}")
            }
        }
        for (k in t){
            Log.d("Repository", "Film = ${k.name} status is ${k.status}")
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