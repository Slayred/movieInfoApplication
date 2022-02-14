package com.chibisov.movieinfoapplication.data.models

import com.chibisov.movieinfoapplication.R

class UiMovie( val id: Int
              , val name: String
              , val description: String
              , val poster: Int
              , var status: Boolean
              , var checked: Boolean) {

    fun checkStatus(): Int {
      return  if (status) R.color.red
        else R.color.black
    }

}
