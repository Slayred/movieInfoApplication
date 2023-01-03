package com.chibisov.movieinfoapplication.data.repositories.impl

import android.util.Log
import com.chibisov.movieinfoapplication.data.MovieCacheDataSource
import com.chibisov.movieinfoapplication.data.MovieNetDataSource
import com.chibisov.movieinfoapplication.data.local.entity.MovieInfoEntity
import com.chibisov.movieinfoapplication.data.models.KinopoiskMovieInfoModel
import com.chibisov.movieinfoapplication.data.models.UiMovie
import com.chibisov.movieinfoapplication.data.repositories.MovieInfoRepository
import io.reactivex.Flowable
import io.reactivex.Observable

class MovieInfoRepositoryImpl(
    private val movieNetDataSource: MovieNetDataSource,
    private val movieCacheDataSource: MovieCacheDataSource
) : MovieInfoRepository {


    override fun getMovieInfoRx(id: Int): Observable<KinopoiskMovieInfoModel> {
        return movieNetDataSource.getMovieInfoRx(id)
    }

    override fun saveToDbRx(movieInfoEntity: MovieInfoEntity) {
        Log.d("DB", "SAVE TO DB")
        movieCacheDataSource.insertMovieInfo(movieInfoEntity)
    }

    override fun getMovieInfoDb(id: Int): Flowable<MovieInfoEntity> {
        return movieCacheDataSource.getMovieInfo(id)
    }

    override suspend fun getMovieInfoRc(id: Int): KinopoiskMovieInfoModel {
        return movieNetDataSource.getMovieInfoCr(id)
    }

    fun getMovieInfoFetch(id: Int) {


    }
}