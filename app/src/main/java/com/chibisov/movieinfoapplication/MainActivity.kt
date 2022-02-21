package com.chibisov.movieinfoapplication

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chibisov.movieinfoapplication.adapter.MovieAdapter
import com.chibisov.movieinfoapplication.core.Const
import com.chibisov.movieinfoapplication.core.MovieType
import com.chibisov.movieinfoapplication.core.Observer
import com.chibisov.movieinfoapplication.data.Movies
import com.chibisov.movieinfoapplication.data.MoviesCacheFavorites
import com.chibisov.movieinfoapplication.data.Repository
import com.chibisov.movieinfoapplication.data.models.UiMovie
import com.chibisov.movieinfoapplication.domain.BaseInteractor
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), View.OnClickListener, Observer {

    private lateinit var inviteBtn: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var favoritesBtn: Button
    private lateinit var adapter: MovieAdapter
    private val repository = Repository(MoviesCacheFavorites, Movies)
    private val baseInteractor = BaseInteractor(repository)
    private val communication = Communication()

//    private val activityResultLauncher =
//        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
//            if (activityResult.resultCode == RESULT_OK) {
//                val r = activityResult.data!!.getParcelableExtra<Movie>(Const.MOVIE)
//            }
//        }

    override fun onStart() {
        super.onStart()
        communication.add(this)
        Log.d("TAG", "MainActivity onStart")
        communication.showUiMovieList(ArrayList(baseInteractor.showUIList()))
        adapter.updateDataFromAdapter()
    }

    override fun onStop() {
        super.onStop()
        communication.remove(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("TAG", "MainActivity onCreate")
        setContentView(R.layout.activity_main)
        inviteBtn = findViewById(R.id.inviteBtn)
        favoritesBtn = findViewById(R.id.favoriteBtn)
        recyclerView = findViewById(R.id.movieRV)
        adapter = MovieAdapter(MovieType.Favorite, object : MovieAdapter.FavoriteClickListener {
            override fun change(movie: UiMovie) {
                Snackbar.make(
                    recyclerView,
                    "Change Status?",
                    Snackbar.LENGTH_SHORT
                ).setAction("YES") {
                    baseInteractor.changeStatus(movie)
                    communication.showUiMovieList(ArrayList(baseInteractor.showUIList()))
                }.show()
            }
        }, object : MovieAdapter.DetailsCLickListener {
            override fun details(movie: UiMovie) {
                showDetails(movie)
            }
        }
            ,communication)
        recyclerView.adapter = adapter
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.layoutManager = LinearLayoutManager(this)
        } else recyclerView.layoutManager = GridLayoutManager(this, 2)


        inviteBtn.setOnClickListener(this)
        favoritesBtn.setOnClickListener(this)


    }

    override fun onBackPressed() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Exit")
        alertDialog.setMessage("Are you sure to exit?")
        alertDialog.setPositiveButton("YES") { _, _ ->
            super.onBackPressed()
        }
        alertDialog.setNegativeButton("NO") { _, _ -> }
        alertDialog.show()
    }

    private fun share() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.invite_text))
        startActivity(Intent.createChooser(intent, getString(R.string.share_via)))
    }


    override fun onClick(p0: View?) {
        when (p0?.id) {
            inviteBtn.id -> {
                share()
            }
            favoritesBtn.id -> {
                showFavorites()
            }
        }
    }

    private fun showDetails(movie: UiMovie){
        val intent = Intent(this, MovieInfo::class.java)
        intent.putExtra(Const.MOVIE, movie)
        startActivity(intent)
    }

    private fun showFavorites() {
        val intent = Intent(this, FavoritesMovie::class.java)
        startActivity(intent)
    }

    override fun update() {
        adapter.updateDataFromAdapter()
    }


}

