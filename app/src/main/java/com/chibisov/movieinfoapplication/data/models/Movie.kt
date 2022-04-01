package com.chibisov.movieinfoapplication.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    val name: String,
    val description: String,
    val poster: Int
) : Parcelable {

    fun to(): UiMovie =
        UiMovie(this.id, this.name, this.description, this.poster, status = false, checked = false)
}