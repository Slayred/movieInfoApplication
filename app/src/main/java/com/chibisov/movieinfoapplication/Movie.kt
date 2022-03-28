package com.chibisov.movieinfoapplication

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    val name: String,
    val description: String,
    val poster: Int,
    var favorites: Boolean = false
): Parcelable {
}