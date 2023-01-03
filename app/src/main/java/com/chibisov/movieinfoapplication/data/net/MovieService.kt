package com.chibisov.movieinfoapplication.data.net

import com.chibisov.movieinfoapplication.data.models.KinopoiskMovieInfoModel
import com.chibisov.movieinfoapplication.data.models.KinopoiskMovieResponse
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface MovieService {

    @Headers("X-API-KEY:295873e7-0cb4-4fb5-84ff-0c1fb88fc7fd")
    @GET("films/top?page=1")
    fun getTopFilms(): Call<KinopoiskMovieResponse>


    @Headers("X-API-KEY:295873e7-0cb4-4fb5-84ff-0c1fb88fc7fd")
    @GET("films/top?page=1")
    fun getTopFilmsRX(): Observable<KinopoiskMovieResponse>

    @Headers("X-API-KEY:295873e7-0cb4-4fb5-84ff-0c1fb88fc7fd")
    @GET("films/top?page=1")
    suspend fun getTopFilmsCr(): KinopoiskMovieResponse





    @Headers("X-API-KEY:295873e7-0cb4-4fb5-84ff-0c1fb88fc7fd")
    @GET("films/{id}")
    fun getMovieIno(@Path("id") id: Int): Call<KinopoiskMovieInfoModel>

    @Headers("X-API-KEY:295873e7-0cb4-4fb5-84ff-0c1fb88fc7fd")
    @GET("films/{id}")
    fun getMovieInfoRx(@Path("id")id: Int): Observable<KinopoiskMovieInfoModel>

    @Headers("X-API-KEY:295873e7-0cb4-4fb5-84ff-0c1fb88fc7fd")
    @GET("films/{id}")
    suspend fun getMovieInfoCR(@Path("id")id: Int): KinopoiskMovieInfoModel


}