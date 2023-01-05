package com.chibisov.movieinfoapplication.data.repositories

import com.chibisov.movieinfoapplication.data.local.entity.MovieInfoEntity
import com.chibisov.movieinfoapplication.data.models.KinopoiskMovieInfoModel
import com.chibisov.movieinfoapplication.data.models.UiMovie
import io.reactivex.Flowable
import io.reactivex.Observable

interface MovieInfoRepository {

    fun getMovieInfoRx(id: Int): Observable<KinopoiskMovieInfoModel>

    fun saveToDbRx(movieInfoEntity: MovieInfoEntity)

    fun getMovieInfoDb(id: Int): Flowable<MovieInfoEntity>

    suspend fun getMovieInfoRc(id: Int): UiMovie

    suspend fun changeMovieInfoStatus(id: Int, status: Boolean)

    suspend fun saveToDbCr(movie: UiMovie)
}