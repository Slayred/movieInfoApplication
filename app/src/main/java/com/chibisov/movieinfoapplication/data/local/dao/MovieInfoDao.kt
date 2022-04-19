package com.chibisov.movieinfoapplication.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chibisov.movieinfoapplication.data.local.entity.MovieInfoEntity
import com.chibisov.movieinfoapplication.data.local.entity.MovieListEntity
import com.chibisov.movieinfoapplication.data.models.UiMovie

@Dao
interface MovieInfoDao {

    @Query("SELECT * FROM movie_info WHERE kinopoiskId = :id LIMIT 1")
    fun getMovieInfo(id: Int): List<MovieInfoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieInfo(entity: MovieInfoEntity)
}
