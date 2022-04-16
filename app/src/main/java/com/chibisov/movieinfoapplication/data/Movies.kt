package com.chibisov.movieinfoapplication.data

import com.chibisov.movieinfoapplication.data.models.Movie
import com.chibisov.movieinfoapplication.R
import com.chibisov.movieinfoapplication.core.CallbackData
import com.chibisov.movieinfoapplication.data.models.KinopoiskMovieResponse
import com.chibisov.movieinfoapplication.data.models.UiMovie
import com.chibisov.movieinfoapplication.data.net.MovieService
import com.chibisov.movieinfoapplication.core.CallbackDataList
import com.chibisov.movieinfoapplication.core.CallbackStateMovie
import com.chibisov.movieinfoapplication.data.models.KinopoiskMovieInfoResponse
import com.chibisov.movieinfoapplication.domain.StateMovie
import kotlinx.coroutines.delay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object Movies : NetDataSource {
    private val movieList = arrayListOf(
        Movie(
            1,
            "Криминальное чтиво",
            "Двое бандитов Винсент Вега и Джулс Винфилд ведут философские беседы в перерывах между разборками и решением проблем с должниками криминального босса Марселласа Уоллеса.Двое бандитов Винсент Вега и Джулс Винфилд ведут философские беседы в перерывах между разборками и решением проблем с должниками криминального босса Марселласа Уоллеса.Двое бандитов Винсент Вега и Джулс Винфилд ведут философские беседы в перерывах между разборками и решением проблем с должниками криминального босса Марселласа Уоллеса.Двое бандитов Винсент Вега и Джулс Винфилд ведут философские беседы в перерывах между разборками и решением проблем с должниками криминального босса Марселласа Уоллеса.Двое бандитов Винсент Вега и Джулс Винфилд ведут философские беседы в перерывах между разборками и решением проблем с должниками криминального босса Марселласа Уоллеса.Двое бандитов Винсент Вега и Джулс Винфилд ведут философские беседы в перерывах между разборками и решением проблем с должниками криминального босса Марселласа Уоллеса.Двое бандитов Винсент Вега и Джулс Винфилд ведут философские беседы в перерывах между разборками и решением проблем с должниками криминального босса Марселласа Уоллеса.Двое бандитов Винсент Вега и Джулс Винфилд ведут философские беседы в перерывах между разборками и решением проблем с должниками криминального босса Марселласа Уоллеса.Двое бандитов Винсент Вега и Джулс Винфилд ведут философские беседы в перерывах между разборками и решением проблем с должниками криминального босса Марселласа Уоллеса.Двое бандитов Винсент Вега и Джулс Винфилд ведут философские беседы в перерывах между разборками и решением проблем с должниками криминального босса Марселласа Уоллеса.Двое бандитов Винсент Вега и Джулс Винфилд ведут философские беседы в перерывах между разборками и решением проблем с должниками криминального босса Марселласа Уоллеса.Двое бандитов Винсент Вега и Джулс Винфилд ведут философские беседы в перерывах между разборками и решением проблем с должниками криминального босса Марселласа Уоллеса.Двое бандитов Винсент Вега и Джулс Винфилд ведут философские беседы в перерывах между разборками и решением проблем с должниками криминального босса Марселласа Уоллеса.Двое бандитов Винсент Вега и Джулс Винфилд ведут философские беседы в перерывах между разборками и решением проблем с должниками криминального босса Марселласа Уоллеса.Двое бандитов Винсент Вега и Джулс Винфилд ведут философские беседы в перерывах между разборками и решением проблем с должниками криминального босса Марселласа Уоллеса.Двое бандитов Винсент Вега и Джулс Винфилд ведут философские беседы в перерывах между разборками и решением проблем с должниками криминального босса Марселласа Уоллеса.Двое бандитов Винсент Вега и Джулс Винфилд ведут философские беседы в перерывах между разборками и решением проблем с должниками криминального босса Марселласа Уоллеса.",
            R.drawable.ic_pilp_fiction_foreground
        ),
        Movie(
            2,
            "Бойцовский клуб",
            "Сотрудник страховой компании страдает хронической бессонницей и отчаянно пытается вырваться из мучительно скучной жизни. Однажды в очередной командировке он встречает некоего Тайлера Дёрдена — харизматического торговца мылом с извращенной философией.",
            R.drawable.ic_fight_club_foreground
        ),
        Movie(
            3,
            "300 Спартанцев",
            "События картины повествуют о кровопролитной битве при Фермопилах в 480 году до н.э., в которой триста отважных спартанцев во главе со своим царем Леонидом преградили путь многотысячной армии персидского царя Ксеркса.",
            R.drawable.ic_300_foreground
        ),
        Movie(
            4,
            "Индиана Джонс",
            "Известный археолог и специалист по оккультным наукам доктор Джонс получает опасное задание от правительства США. Он должен отправиться на поиски уникальной реликвии — священного Ковчега.",
            R.drawable.ic_indiana_jones_foreground
        )
    )

    override fun getList(): ArrayList<UiMovie> {
        return movieList.map { it.to() } as ArrayList<UiMovie>
    }


}

class MovieNetDataSource(private val service: MovieService) {

    fun getNewList(callbackList: CallbackDataList) {
        service.getTopFilms().enqueue(object : Callback<KinopoiskMovieResponse> {
            override fun onResponse(
                call: Call<KinopoiskMovieResponse>,
                response: Response<KinopoiskMovieResponse>
            ) {
                if (response.isSuccessful) {
                    val k = mutableListOf<UiMovie>()
                    response.body()?.films?.forEach {
                        if (it != null) {
                            k.add(it.toUiMovie())
                        }
                    }
                    callbackList.provideData(k as ArrayList<UiMovie>)
                }
            }

            override fun onFailure(call: Call<KinopoiskMovieResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun showMovieInfo(callbackData: CallbackData, id: Int) {
        service.getMovieIno(id).enqueue(object : Callback<KinopoiskMovieInfoResponse>{
            override fun onResponse(
                call: Call<KinopoiskMovieInfoResponse>,
                response: Response<KinopoiskMovieInfoResponse>
            ) {
                if (response.isSuccessful){
                    callbackData.provideData(response.body()!!.toUiMovie())
                }
            }

            override fun onFailure(call: Call<KinopoiskMovieInfoResponse>, t: Throwable) {
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
                    callbackStateMovie.provideStateData(StateMovie.Successful(response.body()!!.toUiMovie()))
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