package com.chibisov.movieinfoapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView

class MovieInfo : AppCompatActivity() {
    private lateinit var movieFavourites : ImageView
    private lateinit var movieDescr: TextView
    private lateinit var movieName: TextView
    private lateinit var moviePoster: ImageView
    private lateinit var comment: EditText
    private var data = Intent()
    private  var movie: Movie? = null


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
            changeFavorites(movie!!)
        }
    }

    private fun setMovie(movie: Movie) {
        movieName.text = movie.name
        movieDescr.text = movie.description
        movie.poster.let { moviePoster.setImageResource(it) }
        setFavourites(movie)
    }

    private fun setFavourites(movie: Movie) {
        movieFavourites.setImageResource(checkFavourites(movie.favorites))
    }

    private fun checkFavourites(favorites: Boolean): Int {
        return when(favorites){
            true -> R.drawable.baseline_favorite_24
            false -> R.drawable.baseline_favorite_border_24
        }
    }

    private fun changeFavorites(movie: Movie) {
        movie.favorites = !movie.favorites
        setFavourites(movie)
    }

    override fun onBackPressed() {
        data.putExtra(Const.MOVIE, movie)
        data.putExtra(Const.COMMENT, comment.text.toString())
        setResult(RESULT_OK, data)
        finish()
        super.onBackPressed()
    }
}