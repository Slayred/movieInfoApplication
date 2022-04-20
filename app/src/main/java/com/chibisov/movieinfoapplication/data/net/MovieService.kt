package com.chibisov.movieinfoapplication.data.net

import com.chibisov.movieinfoapplication.data.models.FilmsShortItem
import com.chibisov.movieinfoapplication.data.models.KinopoiskMovieInfoResponse
import com.chibisov.movieinfoapplication.data.models.KinopoiskMovieResponse
import io.reactivex.Flowable
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface MovieService {

    @Headers("X-API-KEY:295873e7-0cb4-4fb5-84ff-0c1fb88fc7fd")
    @GET("films/top?page=2")
    fun getTopFilms(): Call<KinopoiskMovieResponse>


    @Headers("X-API-KEY:295873e7-0cb4-4fb5-84ff-0c1fb88fc7fd")
    @GET("films/top?page=2")
    fun getTopFilmsRX(): Observable<KinopoiskMovieResponse>



    @Headers("X-API-KEY:295873e7-0cb4-4fb5-84ff-0c1fb88fc7fd")
    @GET("films/{id}")
    fun getMovieIno(@Path("id") id: Int): Call<KinopoiskMovieInfoResponse>
}