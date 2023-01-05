package com.chibisov.movieinfoapplication.data.local.dao

import androidx.room.*
import com.chibisov.movieinfoapplication.data.local.entity.MovieListEntity


@Dao
interface MovieListDao {

    @Query("SELECT * FROM MOVIE_LIST")
    fun getMoviesList(): List<MovieListEntity>

    @Query("SELECT * FROM MOVIE_LIST where status = 1")
    fun getMoviesFavoriteList(): List<MovieListEntity>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(entity: MovieListEntity)

    @Update
    fun updateMovie(entity: MovieListEntity)

    @Query("SELECT * FROM MOVIE_LIST WHERE kinopoiskId = :id LIMIT 1")
    fun searchItem(id: Int): MovieListEntity?

    @Delete
    fun delete(movieListEntity: MovieListEntity)

    @Query("UPDATE movie_list set status = :status where kinopoiskId = :id")
    fun updateStatusOfMovie(id: Int, status: Boolean)
}