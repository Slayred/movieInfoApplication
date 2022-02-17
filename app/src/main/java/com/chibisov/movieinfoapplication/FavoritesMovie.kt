package com.chibisov.movieinfoapplication

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chibisov.movieinfoapplication.adapter.MovieAdapter
import com.chibisov.movieinfoapplication.core.Const
import com.chibisov.movieinfoapplication.core.MovieType
import com.chibisov.movieinfoapplication.core.Observable
import com.chibisov.movieinfoapplication.core.Observer
import com.chibisov.movieinfoapplication.data.Movies
import com.chibisov.movieinfoapplication.data.MoviesCacheFavorites
import com.chibisov.movieinfoapplication.data.Repository
import com.chibisov.movieinfoapplication.data.models.UiMovie
import com.chibisov.movieinfoapplication.domain.BaseInteractor
import com.google.android.material.snackbar.Snackbar

class FavoritesMovie : AppCompatActivity(), Observer {

    private lateinit var recyclerView: RecyclerView

    private val repository = Repository(MoviesCacheFavorites, Movies)
    private val baseInteractor = BaseInteractor(repository)
    private val communication = Communication()
    private lateinit var  adapter: MovieAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites_movie)
        recyclerView = findViewById(R.id.movieRVFavor)
        adapter = MovieAdapter(MovieType.Favorite, object : MovieAdapter.FavoriteClickListener{
            override fun change(movie: UiMovie) {
                Snackbar.make(
                    recyclerView,
                    "Some text",
                    Snackbar.LENGTH_SHORT
                ).setAction("YES"){
                    baseInteractor.changeStatus(movie)
                    communication.setUIMovieList(baseInteractor.showFavorites())
                }.show()
            }
        }, object : MovieAdapter.DetailsCLickListener {
            override fun details(movie: UiMovie) {
                showDetails(movie)
            }
        }
            ,communication)
        communication.add(this)
        recyclerView.adapter = adapter
        if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.layoutManager = LinearLayoutManager(this)
        } else recyclerView.layoutManager = GridLayoutManager(this, 2)

        communication.setUIMovieList(baseInteractor.showFavorites())
    }

    override fun update() {
        adapter.updateDataFromAdapter()
    }


    override fun onDestroy() {
        super.onDestroy()
        communication.remove(this)
    }

    private fun showDetails(movie: UiMovie){
        val intent = Intent(this, MovieInfo::class.java)
        intent.putExtra(Const.MOVIE, movie)
        startActivity(intent)
    }
}