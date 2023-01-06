package com.chibisov.movieinfoapplication.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.chibisov.movieinfoapplication.data.local.entity.MovieInfoEntity
import com.chibisov.movieinfoapplication.data.local.entity.MovieListEntity
import com.chibisov.movieinfoapplication.data.models.UiMovie
import io.reactivex.Flowable

@Dao
interface MovieInfoDao {

    @Query("SELECT * FROM movie_info WHERE kinopoiskId = :id LIMIT 1")
    fun getMovieInfo(id: Int): Flowable<MovieInfoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieInfo(entity: MovieInfoEntity)

    @Query("UPDATE movie_info set status =:status where kinopoiskId = :id")
    fun updateMovieStatus(id: Int, status: Boolean)

    @Query("UPDATE movie_info set checked = 1 where kinopoiskId = :id")
    fun setMovieChecked(id: Int)

    @Query("SELECT * FROM movie_info WHERE kinopoiskId = :id LIMIT 1")
    fun getMovieInfoCr(id: Int): MovieInfoEntity
}
