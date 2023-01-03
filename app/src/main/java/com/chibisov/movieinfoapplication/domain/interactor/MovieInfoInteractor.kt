package com.chibisov.movieinfoapplication.domain.interactor

import com.chibisov.movieinfoapplication.data.local.entity.MovieInfoEntity
import com.chibisov.movieinfoapplication.data.models.UiMovie
import io.reactivex.Flowable
import io.reactivex.Observable

interface MovieInfoInteractor {

    fun showMovieInfoRx(id: Int): Observable<UiMovie>

    fun saveToDbRx(movie: UiMovie)

    fun showMovieInfoDb(id: Int): Flowable<MovieInfoEntity>

    suspend fun showMovieInfoCr(id: Int): UiMovie
}