package com.chibisov.movieinfoapplication.core

import java.lang.Exception

sealed class Error {

    object NoConnection: Exception("No COnnect")
    object NoData: Exception("No Datat")

}