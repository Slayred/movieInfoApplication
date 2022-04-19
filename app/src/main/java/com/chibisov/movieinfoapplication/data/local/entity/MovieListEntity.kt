package com.chibisov.movieinfoapplication.data.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "movie_list")
@Parcelize
data class MovieListEntity(
    @PrimaryKey(autoGenerate = false) var kinopoikId: Int,
    var posterPreviewPath: String,
    var name: String,
    var status: Boolean,
    var checked: Boolean
): Parcelable
