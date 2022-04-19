package com.chibisov.movieinfoapplication.data.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "movie_info")
@Parcelize
data class MovieInfoEntity (
    @PrimaryKey(autoGenerate = false)
    var kinopoiskId: Int,
    var name: String,
    var description: String,
    var posterPath: String,
    var status: Boolean,
    var checked: Boolean
): Parcelable
