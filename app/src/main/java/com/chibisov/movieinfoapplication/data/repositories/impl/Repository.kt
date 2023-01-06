package com.chibisov.movieinfoapplication.data.repositories.impl

import com.chibisov.movieinfoapplication.data.MovieCacheDataSource
import com.chibisov.movieinfoapplication.data.MovieNetDataSource
import com.chibisov.movieinfoapplication.data.converter.MovieInfoConverter
import com.chibisov.movieinfoapplication.data.converter.MovieListConverter
import com.chibisov.movieinfoapplication.data.models.UiMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(
    private val movieCacheDataSource: MovieCacheDataSource,
    private val movieNetDataSource: MovieNetDataSource,
    private val movieInfoConverter: MovieInfoConverter,
    private val movieListConverter: MovieListConverter
) {


    fun addFavorites(movie: UiMovie) {
        movieCacheDataSource.insertMovieItems(movieListConverter.toEntity(movie))

    }


    suspend fun getFavoritesCr(): List<UiMovie> {
        return movieCacheDataSource.getMovieFavoriteList().map {
            movieListConverter.toUi(it)
        }
    }


    fun searchFavorites(movie: UiMovie): Boolean {
        return movieCacheDataSource.searchItem(movieListConverter.toEntity(movie)) != null

    }

    fun removeFavorites(movie: UiMovie) {
        movieCacheDataSource.deleteFavItem(movieListConverter.toEntity(movie))
    }


    suspend fun getNetListCr(): List<UiMovie> {

        val movieList = withContext(Dispatchers.Main) {
            movieNetDataSource.getMovieListCr().films
        }
        val movieListUi = movieList.map {
            it.toUiMovie()
        }
        movieListUi.map {
            val movieListEntity = movieCacheDataSource.searchItem(it.id)
            if (movieListEntity != null) {
                it.checked = movieListEntity.checked
                it.status = movieListEntity.status
            }
        }

        return movieListUi
    }

    suspend fun addCheckedItem(id: Int) {
        withContext(Dispatchers.IO) {
            movieCacheDataSource.changeStatus(id)
        }

    }


}