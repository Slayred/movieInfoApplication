package com.chibisov.movieinfoapplication.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class UiMovie( val id: Int
              , val name: String
              , val description: String
              , val poster: Int
              , var status: Boolean
              , var checked: Boolean): Parcelable {


    fun changeStatus(): UiMovie {
        this.status = !this.status
        return this
    }

    fun same(movie: UiMovie): Boolean{
        return this.id == movie.id
    }


}
