package com.chibisov.movieinfoapplication.data.net

import com.chibisov.movieinfoapplication.data.models.KinopoiskMovieResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface MovieService {

    @Headers("X-API-KEY:295873e7-0cb4-4fb5-84ff-0c1fb88fc7fd")
    @GET("films/top?page=2")
    fun getTopFilms(): Call<KinopoiskMovieResponse>
}