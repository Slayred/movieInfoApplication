package com.chibisov.movieinfoapplication

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chibisov.movieinfoapplication.adapter.MovieAdapter
import com.chibisov.movieinfoapplication.core.Observer
import com.chibisov.movieinfoapplication.data.Movies
import com.chibisov.movieinfoapplication.data.MoviesCacheFavorites
import com.chibisov.movieinfoapplication.data.Repository
import com.chibisov.movieinfoapplication.data.models.Movie
import com.chibisov.movieinfoapplication.data.models.UiMovie
import com.chibisov.movieinfoapplication.domain.BaseInteractor
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), View.OnClickListener, Observer {

    private lateinit var inviteBtn: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var favoritesBtn: Button
    private lateinit var adapter: MovieAdapter
    private var checkedStatus = intArrayOf(0, 0, 0, 0)
    private val repository = Repository(MoviesCacheFavorites, Movies)
    private val baseInteractor = BaseInteractor(repository)
    private val communication = Communication(repository)

    private val activityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            if (activityResult.resultCode == RESULT_OK) {
                val r = activityResult.data!!.getParcelableExtra<Movie>(Const.MOVIE)
//                listMovie.find {
//                    it.id == r?.id
//                }?.favorites = r?.favorites!!
//                Log.d(Const.TAG, "Movie ${r.name} is favorite: ${r.favorites}")
//                Log.d(
//                    Const.TAG,
//                    "Comment added: ${activityResult.data!!.getStringExtra(Const.COMMENT)}"
//                )
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        inviteBtn = findViewById(R.id.inviteBtn)
        favoritesBtn = findViewById(R.id.favoriteBtn)
        recyclerView = findViewById(R.id.movieRV)
        adapter = MovieAdapter(MovieType.Favorite, object :MovieAdapter.FavoriteClickListener{
            override fun change(movie: UiMovie) {
                Snackbar.make(
                    recyclerView,
                    "Some text",
                    Snackbar.LENGTH_SHORT
                ).setAction("YES"){
                    baseInteractor.changeStatus(movie)
                    communication.showUIMovie(baseInteractor.showUIList())
                }.show()
            }
        }, communication)
        recyclerView.adapter = adapter
        if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.layoutManager = LinearLayoutManager(this)
        } else recyclerView.layoutManager = GridLayoutManager(this, 2)

        adapter.show()

        inviteBtn.setOnClickListener(this)
        favoritesBtn.setOnClickListener(this)

    }

    override fun onBackPressed() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Exit")
        alertDialog.setMessage("Are you sure to exit?")
        alertDialog.setPositiveButton("YES"){
                _, _ -> super.onBackPressed()
        }
        alertDialog.setNegativeButton("NO"){_, _ ->}
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

    private fun showFavorites() {
        val intent = Intent(this, FavoritesMovie::class.java)
        activityResultLauncher.launch(intent)
    }

    override fun update() {
        adapter.update()
    }
}

