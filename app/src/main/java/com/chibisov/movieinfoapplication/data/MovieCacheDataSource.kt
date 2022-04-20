package com.chibisov.movieinfoapplication.data


import androidx.lifecycle.LiveData
import com.chibisov.movieinfoapplication.data.local.dao.MovieInfoDao
import com.chibisov.movieinfoapplication.data.local.dao.MovieListDao
import com.chibisov.movieinfoapplication.data.local.entity.MovieInfoEntity
import com.chibisov.movieinfoapplication.data.local.entity.MovieListEntity
import com.chibisov.movieinfoapplication.data.models.UiMovie
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers



class MovieCacheDataSource(private val movieListDao: MovieListDao,
private val movieInfoDao: MovieInfoDao) {

    private val compositeDisposable = CompositeDisposable()


    fun getMovieItems(): List<MovieListEntity> {
        return movieListDao.getMoviesList()
    }


    fun searchItem(movieListEntity: MovieListEntity): MovieListEntity? {
        return  movieListDao.searchItem(movieListEntity.kinopoikId)
    }


    fun insertMovieItems(movieListEntity: MovieListEntity) {
        movieListDao.insertMovie(movieListEntity.copy(status = true))
    }

    fun insertMovieInfo(movieInfoEntity: MovieInfoEntity) = movieInfoDao.insertMovieInfo(movieInfoEntity)

    fun updateMovieItems(movieListEntity: MovieListEntity) = movieListDao.updateMovie(movieListEntity)

    fun deleteFavItem(movieListEntity: MovieListEntity) {
        movieListDao.delete(movieListEntity)
    }

}