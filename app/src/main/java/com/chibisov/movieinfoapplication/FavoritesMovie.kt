package com.chibisov.movieinfoapplication

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chibisov.movieinfoapplication.adapter.MovieAdapter
import com.chibisov.movieinfoapplication.adapter.itemDecoration.CustomHorizontalItemDecoration
import com.chibisov.movieinfoapplication.adapter.itemDecoration.CustomVerticalItemDecoration
import com.chibisov.movieinfoapplication.core.Const
import com.chibisov.movieinfoapplication.core.MovieType
import com.chibisov.movieinfoapplication.core.Observer
import com.chibisov.movieinfoapplication.data.Movies
import com.chibisov.movieinfoapplication.data.MoviesCacheFavorites
import com.chibisov.movieinfoapplication.data.Repository
import com.chibisov.movieinfoapplication.data.models.UiMovie
import com.chibisov.movieinfoapplication.domain.BaseInteractor
import com.chibisov.movieinfoapplication.domain.Communication
import com.google.android.material.snackbar.Snackbar

class FavoritesMovie : AppCompatActivity(), Observer {

    private lateinit var recyclerView: RecyclerView

    private val repository = Repository(MoviesCacheFavorites, Movies)
    private val baseInteractor = BaseInteractor(repository)
    private val communication = Communication()
    private lateinit var adapter: MovieAdapter


    private val activityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            if (activityResult.resultCode == RESULT_OK) {
                val r = activityResult.data!!.getParcelableExtra<UiMovie>(Const.MOVIE)
                if (r != null) {
                    Log.d(Const.TAG, "Movie ${r.name} is favorite: ${r.status}")
                }
                Log.d(
                    Const.TAG,
                    "Comment added: ${activityResult.data!!.getStringExtra(Const.COMMENT)}"
                )
            }
        }

    override fun onStart() {
        super.onStart()
        communication.add(this)
    }

    override fun onStop() {
        super.onStop()
        communication.remove(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites_movie)
        recyclerView = findViewById(R.id.movieRVFavor)
        val divider = ResourcesCompat.getDrawable(resources, R.drawable.divider, null)
        adapter = MovieAdapter(MovieType.Favorite, object : MovieAdapter.FavoriteClickListener {
            override fun change(movie: UiMovie) {
                Snackbar.make(
                    recyclerView,
                    resources.getText(R.string.change_status),
                    Snackbar.LENGTH_SHORT
                ).setAction(resources.getText(R.string.yes)) {
                    baseInteractor.changeStatus(movie)
                    communication.showUiMovieList(baseInteractor.showFavorites())
                }.show()
            }
        }, object : MovieAdapter.DetailsCLickListener {
            override fun details(movie: UiMovie) {
                showDetails(movie)
            }
        }, communication)
        recyclerView.adapter = adapter
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.layoutManager = LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false
            )
            with(recyclerView) {
                addItemDecoration(CustomVerticalItemDecoration(divider!!))
            }
        } else {
            recyclerView.layoutManager = GridLayoutManager(this, 2)
            with(recyclerView) {
                addItemDecoration(
                    CustomHorizontalItemDecoration(
                        divider!!
                    )
                )
            }
        }
        communication.showUiMovieList(ArrayList(baseInteractor.showFavorites()))
    }

    override fun update() {
        adapter.updateDataFromAdapter()
    }


    private fun showDetails(movie: UiMovie) {
        val intent = Intent(this, MovieInfo::class.java)
        intent.putExtra(Const.MOVIE, movie)
        activityResultLauncher.launch(intent)
    }
}