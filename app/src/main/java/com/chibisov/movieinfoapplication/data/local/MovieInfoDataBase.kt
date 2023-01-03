package com.chibisov.movieinfoapplication.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.chibisov.movieinfoapplication.data.local.dao.MovieInfoDao
import com.chibisov.movieinfoapplication.data.local.dao.MovieListDao
import com.chibisov.movieinfoapplication.data.local.entity.MovieInfoEntity
import com.chibisov.movieinfoapplication.data.local.entity.MovieListEntity

@Database(
    entities = [MovieInfoEntity::class,
        MovieListEntity::class], version = 1, exportSchema = true
)
abstract class MovieInfoDataBase : RoomDatabase() {

    abstract fun movieInfoData(): MovieInfoDao
    abstract fun movieListDao(): MovieListDao

    companion object {

        @Volatile
        private var INSTANCE: MovieInfoDataBase? = null

        fun getDataBase(context: Context): MovieInfoDataBase {
            val tempInstance = INSTANCE
            return if (tempInstance != null) {
                tempInstance
            } else synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MovieInfoDataBase::class.java,
                    "movie_database"
                )//.allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

}