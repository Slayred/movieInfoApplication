package com.chibisov.movieinfoapplication.ui

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chibisov.movieinfoapplication.R
import com.chibisov.movieinfoapplication.adapter.MovieAdapter
import com.chibisov.movieinfoapplication.adapter.itemDecoration.CustomHorizontalItemDecoration
import com.chibisov.movieinfoapplication.adapter.itemDecoration.CustomVerticalItemDecoration
import com.chibisov.movieinfoapplication.core.MovieType
import com.chibisov.movieinfoapplication.core.Observer
import com.chibisov.movieinfoapplication.data.Movies
import com.chibisov.movieinfoapplication.data.MoviesCacheFavorites
import com.chibisov.movieinfoapplication.data.Repository
import com.chibisov.movieinfoapplication.data.models.UiMovie
import com.chibisov.movieinfoapplication.domain.BaseInteractor
import com.chibisov.movieinfoapplication.domain.Communication
import com.google.android.material.snackbar.Snackbar

class BaseFragment: Fragment(), Observer {

    private lateinit var inviteBtn: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var favoritesBtn: Button
    private lateinit var adapter: MovieAdapter
    private val repository = Repository(MoviesCacheFavorites, Movies)
    private val baseInteractor = BaseInteractor(repository)
    private val communication = Communication()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("BaseFragmentTag", "onCreateView()1 ${javaClass.simpleName}")
//        return super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.movie_list, container, false)
    }

    override fun onStart() {
        super.onStart()
        communication.add(this)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inviteBtn = view.findViewById(R.id.inviteBtn)
        recyclerView = view.findViewById(R.id.movieRV)
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
            recyclerView.layoutManager = LinearLayoutManager(activity,
                LinearLayoutManager.VERTICAL,
                false)
            with(recyclerView){
                addItemDecoration(CustomVerticalItemDecoration(divider!!))
            }
        } else {
            recyclerView.layoutManager  = GridLayoutManager(activity, 2)
            with(recyclerView) {
                addItemDecoration(
                    CustomHorizontalItemDecoration(
                        divider!!
                    )
                )
            }
        }
        communication.showUiMovieList(baseInteractor.showUIList())

        inviteBtn.setOnClickListener {
            share()
        }
    }
    private fun showDetails(movie: UiMovie){
        baseInteractor.addCheckedItem(movie)
//        val intent = Intent(this, MovieInfo::class.java)
//        intent.putExtra(Const.MOVIE, movie)
//        activityResultLauncher.launch(intent)
    }

    private fun share() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.invite_text))
        startActivity(Intent.createChooser(intent, getString(R.string.share_via)))
    }

    override fun update() {
        adapter.updateDataFromAdapter()
    }
}