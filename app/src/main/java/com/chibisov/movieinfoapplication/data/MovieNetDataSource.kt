package com.chibisov.movieinfoapplication.data

import com.chibisov.movieinfoapplication.data.models.KinopoiskMovieResponse
import com.chibisov.movieinfoapplication.data.models.UiMovie
import com.chibisov.movieinfoapplication.data.net.MovieService
import com.chibisov.movieinfoapplication.core.CallbackDataList
import com.chibisov.movieinfoapplication.core.CallbackStateMovie
import com.chibisov.movieinfoapplication.data.models.KinopoiskMovieInfoResponse
import com.chibisov.movieinfoapplication.domain.StateMovie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieNetDataSource(private val service: MovieService) {

    fun getNewList(callbackList: CallbackDataList) {
        service.getTopFilms().enqueue(object : Callback<KinopoiskMovieResponse> {
            override fun onResponse(
                call: Call<KinopoiskMovieResponse>,
                response: Response<KinopoiskMovieResponse>
            ) {
                if (response.isSuccessful) {
                    val listOfMovies = mutableListOf<UiMovie>()
                    response.body()?.films?.forEach {
                        if (it != null) {
                            it.toUiMovie()?.let { it1 -> listOfMovies.add(it1) }
                        }
                    }
                    callbackList.provideData(listOfMovies as ArrayList<UiMovie>)
                }
            }

            override fun onFailure(call: Call<KinopoiskMovieResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }


    fun showStateMovieInfo(callbackStateMovie: CallbackStateMovie, id: Int) {
        service.getMovieIno(id).enqueue(object : Callback<KinopoiskMovieInfoResponse>{
            override fun onResponse(
                call: Call<KinopoiskMovieInfoResponse>,
                response: Response<KinopoiskMovieInfoResponse>
            ) {
                if(response.isSuccessful){
                    response.body()!!.toUiMovie()
                        ?.let { StateMovie.Successful(it) }
                        ?.let { callbackStateMovie.provideStateData(it) }
                } else callbackStateMovie.provideStateData(StateMovie.Fail(response.code().toString()))
            }

            override fun onFailure(call: Call<KinopoiskMovieInfoResponse>, t: Throwable) {
                callbackStateMovie.provideStateData(StateMovie.Fail(t.toString()))
            }
        })
    }

}



interface NetDataSource : DataSource {

}


interface DataSource {
    fun getList(): ArrayList<UiMovie>
}