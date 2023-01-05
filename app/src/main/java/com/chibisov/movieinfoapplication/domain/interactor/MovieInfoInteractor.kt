package com.chibisov.movieinfoapplication.domain.interactor

import com.chibisov.movieinfoapplication.data.local.entity.MovieInfoEntity
import com.chibisov.movieinfoapplication.data.models.UiMovie
import io.reactivex.Flowable
import io.reactivex.Observable

interface MovieInfoInteractor {

    fun showMovieInfoRx(id: Int): Observable<UiMovie>

    fun saveToDbRx(movie: UiMovie)

    fun showMovieInfoDbRx(id: Int): Flowable<MovieInfoEntity>

    suspend fun showMovieInfoCr(id: Int): UiMovie

    suspend fun changeMovieStatus(id: Int, status: Boolean)

    fun changeMovieChecked(uiMovie: UiMovie)
    suspend fun saveToDbCr(movie: UiMovie)

}