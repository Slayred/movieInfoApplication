package com.chibisov.movieinfoapplication.data.repositories.impl

import com.chibisov.movieinfoapplication.core.CallbackDataList
import com.chibisov.movieinfoapplication.data.MovieCacheDataSource
import com.chibisov.movieinfoapplication.data.MovieNetDataSource
import com.chibisov.movieinfoapplication.data.converter.MovieInfoConverter
import com.chibisov.movieinfoapplication.data.converter.MovieListConverter
import com.chibisov.movieinfoapplication.data.models.KinopoiskMovieInfoModel
import com.chibisov.movieinfoapplication.data.models.UiMovie
import io.reactivex.Observable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(
    private val movieCacheDataSource: MovieCacheDataSource,
    private val movieNetDataSource: MovieNetDataSource,
    private val movieInfoConverter: MovieInfoConverter,
    private val movieListConverter: MovieListConverter
) {


    fun getNetList(callbackDataList: CallbackDataList) {
        movieNetDataSource.getNewList(callbackDataList)
    }

    fun getNetListRx(): Observable<List<UiMovie>> = movieNetDataSource.getMovieListRX()
        .map { kinopoiskMovieResponse ->
            kinopoiskMovieResponse.films.map {
                it.toUiMovie()
            }
        }


    fun addFavorites(movie: UiMovie) {
        movieCacheDataSource.insertMovieItems(movieListConverter.toEntity(movie))

    }

    fun showFavoritesRx() = movieCacheDataSource.getMovieItems().map {
        movieListConverter.toUi(it)
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

    fun getMovieInfoRx(id: Int): Observable<KinopoiskMovieInfoModel> {
        return movieNetDataSource.getMovieInfoRx(id)
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


}