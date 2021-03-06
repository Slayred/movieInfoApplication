package com.chibisov.movieinfoapplication.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.chibisov.movieinfoapplication.R
import com.chibisov.movieinfoapplication.adapter.MovieAdapter
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

/**
 * A simple [Fragment] subclass.
 * Use the [MovieListFavoritesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MovieListFavoritesFragment : BaseMovieListFragment(), Observer {

    private val repository = Repository(MoviesCacheFavorites, Movies)
    private val baseInteractor = BaseInteractor(repository)
    private val communication = Communication()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_list_favorites, container, false)
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
        setFragmentResultListener(Const.BUNDLE) { _, bundle ->
            val resultMovie = bundle.getParcelable<UiMovie>(Const.MOVIE)
            val resultComment = bundle.getString(Const.COMMENT)
            Log.d(
                "MAINFRAGMENT",
                "Movie status is ${resultMovie?.status} \n Comment is $resultComment"
            )
        }
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.movieRVFavor)
        adapter = MovieAdapter(MovieType.Favorite, object : MovieAdapter.FavoriteClickListener {
            override fun change(movie: UiMovie) {
                baseInteractor.changeStatus(movie)
                communication.showUiMovieList(baseInteractor.showFavorites())
                Snackbar.make(
                    view,
                    "${movie.name} " + getString(R.string.change_status),
                    Snackbar.LENGTH_LONG
                )
                    .setAnchorView(R.id.btm_nav)
                    .setAction(getString(R.string.undo)) {
                        baseInteractor.changeStatus(movie)
                        communication.showUiMovieList(baseInteractor.showFavorites())
                    }.show()
            }
        }, object : MovieAdapter.DetailsCLickListener {
            override fun details(movie: UiMovie) {
                showDetails(movie)
            }
        }, communication)
        val divider = ResourcesCompat.getDrawable(resources, R.drawable.divider, null)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = setAdapter(divider!!, recyclerView, resources)
        recyclerView.itemAnimator?.apply {
            removeDuration = 0
        }
        communication.showUiMovieList(baseInteractor.showFavorites())

    }

    private fun showDetails(movie: UiMovie) {
        val fragment = MovieInfoFragment()
        baseInteractor.addCheckedItem(movie)
        parentFragmentManager.setFragmentResult(
            Const.MOVIE,
            bundleOf(Const.BUNDLE to movie)
        )
        val bundle = Bundle()
        bundle.putParcelable(Const.MOVIE, movie)
        fragment.arguments = bundle
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.home_fragment_container, fragment)
        transaction.addToBackStack(fragment.javaClass.name)
        transaction.commit()
    }

    override fun update() {
        adapter.updateDataFromAdapter()
    }

}