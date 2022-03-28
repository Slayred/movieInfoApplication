package com.chibisov.movieinfoapplication

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chibisov.movieinfoapplication.adapter.itemDecoration.CustomVerticalItemDecoration
import com.chibisov.movieinfoapplication.adapter.MovieAdapter
import com.chibisov.movieinfoapplication.adapter.itemDecoration.CustomHorizontalItemDecoration
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


    private val activityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            if (activityResult.resultCode == RESULT_OK) {
                val r = activityResult.data!!.getParcelableExtra<UiMovie>(Const.MOVIE)
                if (r != null) {
                    Log.d(Const.TAG, "Movie ${r.name} is favorite: ${r.status}")
                }
                Log.d(
                    Const.TAG,
                    "Comment added: ${activityResult.data!!.getStringExtra(Const.COMMENT)}")
            }
        }

    override fun onStart() {
        super.onStart()
        communication.add(this)
        communication.showUiMovieList(baseInteractor.showUIList())
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
                    resources.getText(R.string.change_status),
                    Snackbar.LENGTH_SHORT
                ).setAction(resources.getText(R.string.yes)) {
                    baseInteractor.changeStatus(movie)
                    val t = baseInteractor.showUIList()
                    communication.showUiMovieList(t)
                }.show()
            }
        }, object : MovieAdapter.DetailsCLickListener {
            override fun details(movie: UiMovie) {
                showDetails(movie)
            }
        }
            ,communication)
        val divider = ResourcesCompat.getDrawable(resources, R.drawable.divider, null)

        recyclerView.adapter = adapter
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.layoutManager = LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,
                false)
            with(recyclerView){
                addItemDecoration(CustomVerticalItemDecoration(divider!!))
            }
        } else {
            recyclerView.layoutManager  = GridLayoutManager(this, 2)
            with(recyclerView) {
                addItemDecoration(
                    CustomHorizontalItemDecoration(
                        divider!!
                    )
                )
            }
        }

        inviteBtn.setOnClickListener(this)
        favoritesBtn.setOnClickListener(this)


    }
    override fun onBackPressed() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle(resources.getText(R.string.exit))
        alertDialog.setMessage(resources.getText(R.string.exit_question))
        alertDialog.setPositiveButton(resources.getText(R.string.yes)) { _, _ ->
            super.onBackPressed()
        }
        alertDialog.setNegativeButton(resources.getText(R.string.no)) { _, _ -> }
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
        baseInteractor.addCheckedItem(movie)
        val intent = Intent(this, MovieInfo::class.java)
        intent.putExtra(Const.MOVIE, movie)
        activityResultLauncher.launch(intent)

    }

    private fun showFavorites() {
        val intent = Intent(this, FavoritesMovie::class.java)
        startActivity(intent)
    }

    override fun update() {
        adapter.updateDataFromAdapter()
    }


}

