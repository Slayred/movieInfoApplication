package com.chibisov.movieinfoapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.chibisov.movieinfoapplication.core.Const
import com.chibisov.movieinfoapplication.data.Movies
import com.chibisov.movieinfoapplication.data.MoviesCacheFavorites
import com.chibisov.movieinfoapplication.data.Repository
import com.chibisov.movieinfoapplication.data.models.UiMovie
import com.chibisov.movieinfoapplication.domain.BaseInteractor

class MovieInfo : AppCompatActivity() {

    private val repository = Repository(MoviesCacheFavorites, Movies)
    private val baseInteractor = BaseInteractor(repository)

    private lateinit var movieFavourites : ImageView
    private lateinit var movieDescr: TextView
    private lateinit var movieName: TextView
    private lateinit var moviePoster: ImageView
    private lateinit var comment: EditText
    private  var movie: UiMovie? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_info)
        movieFavourites = findViewById(R.id.favorites)
        movieDescr = findViewById(R.id.movieDescrInfo)
        movieName = findViewById(R.id.movieNameInfo)
        moviePoster = findViewById(R.id.infoImageView)
        comment = findViewById(R.id.commentET)

        if(movie == null){
            movie = this.intent.getParcelableExtra(Const.MOVIE)!!
        }

        setMovie(movie!!)


        movieFavourites.setOnClickListener {
            movie!!.status = !movie!!.status
            baseInteractor.changeStatus(movie!!)
            setFavourites(movie!!)
        }
    }

    private fun setMovie(movie: UiMovie) {
        movieName.text = movie.name
        movieDescr.text = movie.description
        movie.poster.let { moviePoster.setImageResource(it) }
        setFavourites(movie)
    }

    private fun setFavourites(movie: UiMovie) {
        movieFavourites.setImageResource(checkFavourites(movie.status))
    }

    private fun checkFavourites(status: Boolean): Int {
        return when(status){
            true -> R.drawable.baseline_favorite_24
            false -> R.drawable.baseline_favorite_border_24
        }
    }


}