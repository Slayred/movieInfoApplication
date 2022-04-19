package com.chibisov.movieinfoapplication.data.local.dao

import androidx.room.*
import com.chibisov.movieinfoapplication.data.local.entity.MovieInfoEntity
import com.chibisov.movieinfoapplication.data.local.entity.MovieListEntity


@Dao
interface MovieListDao {

    @Query("SELECT * FROM MOVIE_LIST")
    fun getMoviesList(): List<MovieListEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(entity: MovieListEntity)

    @Update
    fun updateMovie(entity: MovieListEntity)

    @Query("SELECT * FROM MOVIE_LIST WHERE kinopoikId = :id LIMIT 1")
    fun searchItem(id: Int): MovieListEntity?

    @Delete
    fun delete(movieListEntity: MovieListEntity)
}