package com.chibisov.movieinfoapplication.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class FavoriteMovie(
    val id: Int,
    var favorites: Boolean = false
) : Parcelable {
}