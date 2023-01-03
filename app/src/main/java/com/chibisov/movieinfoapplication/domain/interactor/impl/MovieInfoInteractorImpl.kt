package com.chibisov.movieinfoapplication.domain.interactor.impl

import com.chibisov.movieinfoapplication.data.converter.MovieInfoConverter
import com.chibisov.movieinfoapplication.data.local.entity.MovieInfoEntity
import com.chibisov.movieinfoapplication.data.models.UiMovie
import com.chibisov.movieinfoapplication.data.repositories.MovieInfoRepository
import com.chibisov.movieinfoapplication.domain.interactor.MovieInfoInteractor
import io.reactivex.Flowable
import io.reactivex.Observable

class MovieInfoInteractorImpl (private val repository: MovieInfoRepository
, private val movieConverter: MovieInfoConverter): MovieInfoInteractor {


   override fun showMovieInfoRx(id: Int): Observable<UiMovie> {
        return repository.getMovieInfoRx(id).map {
            it.toUiMovie()
        }
    }

    override fun saveToDbRx(movie: UiMovie) {
        repository.saveToDbRx(movieConverter.fromUiToEntity(movie))
    }

    override fun showMovieInfoDb(id: Int): Flowable<MovieInfoEntity> {
       return repository.getMovieInfoDb(id)
    }

    override suspend fun showMovieInfoCr(id: Int): UiMovie {
        return repository.getMovieInfoRc(id).toUiMovie()
    }

}