package com.chibisov.movieinfoapplication.data

import com.chibisov.movieinfoapplication.data.models.Movie
import com.chibisov.movieinfoapplication.R
import com.chibisov.movieinfoapplication.data.models.UiMovie

object Movies: NetDataSource {


        private val movieList= arrayListOf<Movie>(
            Movie(1, "Криминальное чтиво", "Двое бандитов Винсент Вега и Джулс Винфилд ведут философские беседы в перерывах между разборками и решением проблем с должниками криминального босса Марселласа Уоллеса.", R.drawable.ic_pilp_fiction_foreground),
            Movie(2, "Бойцовский клуб", "Сотрудник страховой компании страдает хронической бессонницей и отчаянно пытается вырваться из мучительно скучной жизни. Однажды в очередной командировке он встречает некоего Тайлера Дёрдена — харизматического торговца мылом с извращенной философией.", R.drawable.ic_fight_club_foreground),
            Movie(3, "300 Спартанцев", "События картины повествуют о кровопролитной битве при Фермопилах в 480 году до н.э., в которой триста отважных спартанцев во главе со своим царем Леонидом преградили путь многотысячной армии персидского царя Ксеркса.",
                R.drawable.ic_300_foreground),
            Movie(4, "Индиана Джонс", "Известный археолог и специалист по оккультным наукам доктор Джонс получает опасное задание от правительства США. Он должен отправиться на поиски уникальной реликвии — священного Ковчега.", R.drawable.ic_indiana_jones_foreground)
        )

    override fun getList(): ArrayList<UiMovie> {
        return movieList.map { it.to() } as ArrayList<UiMovie>
    }


}

interface NetDataSource: DataSource {

}

interface DataSource {
    fun getList(): ArrayList<UiMovie>
}