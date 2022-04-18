package com.chibisov.movieinfoapplication.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chibisov.movieinfoapplication.data.local.dao.MovieInfoDao
import com.chibisov.movieinfoapplication.data.local.entity.MovieInfoEntity

@Database(entities = [MovieInfoEntity::class], version = 1, exportSchema = true)
abstract class MovieInfoDataBase: RoomDatabase() {

    abstract fun movieInfoData(): MovieInfoDao

}