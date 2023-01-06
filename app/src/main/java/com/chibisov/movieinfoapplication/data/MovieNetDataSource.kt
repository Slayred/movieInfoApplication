package com.chibisov.movieinfoapplication.data

import com.chibisov.movieinfoapplication.data.models.KinopoiskMovieResponse
import com.chibisov.movieinfoapplication.data.models.UiMovie
import com.chibisov.movieinfoapplication.data.net.MovieService
import com.chibisov.movieinfoapplication.core.CallbackDataList
import com.chibisov.movieinfoapplication.data.models.KinopoiskMovieInfoModel
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieNetDataSource(private val service: MovieService) {

    fun getMovieListRX() = service.getTopFilmsRX()

    suspend fun getMovieListCr() =service.getTopFilmsCr()


    fun getMovieInfoRx(id: Int): Observable<KinopoiskMovieInfoModel> {
        return  service.getMovieInfoRx(id)
    }

    suspend fun getMovieInfoCr(id: Int): KinopoiskMovieInfoModel {
        return service.getMovieInfoCR(id)
    }

}



interface NetDataSource : DataSource {

}


interface DataSource {
    fun getList(): ArrayList<UiMovie>
}