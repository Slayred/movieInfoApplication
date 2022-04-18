package com.chibisov.movieinfoapplication.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_info")
data class MovieInfoEntity (
    @PrimaryKey(autoGenerate = true) var id: Long
)
