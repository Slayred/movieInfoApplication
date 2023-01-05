package com.chibisov.movieinfoapplication.data


import com.chibisov.movieinfoapplication.data.local.dao.MovieInfoDao
import com.chibisov.movieinfoapplication.data.local.dao.MovieListDao
import com.chibisov.movieinfoapplication.data.local.entity.MovieInfoEntity
import com.chibisov.movieinfoapplication.data.local.entity.MovieListEntity
import io.reactivex.Flowable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class MovieCacheDataSource(private val movieListDao: MovieListDao,
private val movieInfoDao: MovieInfoDao) {



    fun getMovieItems(): List<MovieListEntity> {
        return movieListDao.getMoviesList()
    }

    suspend fun getMovieFavoriteList(): List<MovieListEntity> {
        return withContext(Dispatchers.IO){
            movieListDao.getMoviesFavoriteList()
        }
    }

    fun searchItem(movieListEntity: MovieListEntity): MovieListEntity? {
        return  movieListDao.searchItem(movieListEntity.kinopoiskId)
    }

   suspend fun searchItem(id: Int): MovieListEntity? {
        return withContext(Dispatchers.IO){
            movieListDao.searchItem(id)
        }
    }


    fun insertMovieItems(movieListEntity: MovieListEntity) {
        movieListDao.insertMovie(movieListEntity.copy(status = true))
    }

    fun insertMovieInfo(movieInfoEntity: MovieInfoEntity) {
        movieInfoDao.insertMovieInfo(movieInfoEntity)
    }

    suspend fun insertMovieInfoDbRc(movieInfoEntity: MovieInfoEntity) {
        withContext(Dispatchers.IO){
            movieInfoDao.insertMovieInfo(movieInfoEntity)
        }
    }

    fun updateMovieItems(movieListEntity: MovieListEntity) = movieListDao.updateMovie(movieListEntity)

    fun deleteFavItem(movieListEntity: MovieListEntity) {
        movieListDao.delete(movieListEntity)
    }

    fun getMovieInfo(id: Int): Flowable<MovieInfoEntity> {
        return movieInfoDao.getMovieInfo(id)
    }

    fun updateMovieInfoStatus(id: Int, status: Boolean) {
        movieInfoDao.updateMovieStatus(id, status)

    }

    fun updateMovieListStatus(id: Int, status: Boolean) {
        movieListDao.updateStatusOfMovie(id,status)
    }

    fun saveMovieRc(fromUiToEntity: MovieInfoEntity) {
        fromUiToEntity.checked = true
        movieInfoDao.insertMovieInfo(fromUiToEntity)
    }

}