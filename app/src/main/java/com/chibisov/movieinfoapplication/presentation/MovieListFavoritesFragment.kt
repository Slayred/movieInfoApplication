package com.chibisov.movieinfoapplication.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.RecyclerView
import com.chibisov.movieinfoapplication.MovieInfoApp
import com.chibisov.movieinfoapplication.R
import com.chibisov.movieinfoapplication.core.Const
import com.chibisov.movieinfoapplication.core.MovieType
import com.chibisov.movieinfoapplication.core.mainRouter
import com.chibisov.movieinfoapplication.data.models.UiMovie
import com.chibisov.movieinfoapplication.presentation.adapter.MovieAdapter
import com.chibisov.movieinfoapplication.viewmodels.FavoriteMovieListViewModel
import com.chibisov.movieinfoapplication.viewmodels.MovieInfoViewModel
import com.google.android.material.snackbar.Snackbar

class MovieListFavoritesFragment : BaseMovieListFragment() {


    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MovieAdapter
    private lateinit var fragment: BaseMovieListFragment
    private lateinit var favoriteMovieListViewModel: FavoriteMovieListViewModel
    private lateinit var movieInfoViewModel: MovieInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_list_favorites, container, false)
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
        favoriteMovieListViewModel =
            (requireActivity().application as MovieInfoApp).movieFavoriteListViewModel
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieInfoViewModel = (requireActivity().application as MovieInfoApp).movieInfoViewModel
        recyclerView = view.findViewById(R.id.movieRVFavor)
        adapter = MovieAdapter(MovieType.Favorite, object : MovieAdapter.FavoriteClickListener {
            override fun change(movie: UiMovie) {
                favoriteMovieListViewModel.changeStatus(movie)
                Snackbar.make(
                    view,
                    "${movie.name} " + getString(R.string.change_status),
                    Snackbar.LENGTH_LONG
                )
                    .setAnchorView(R.id.btm_nav)
                    .setAction(getString(R.string.undo)) {
                        favoriteMovieListViewModel.changeStatus(movie)
                    }.show()
            }
        }, object : MovieAdapter.DetailsCLickListener {
            override fun details(movie: UiMovie) {
                showDetails(movie)
            }
        }, favoriteMovieListViewModel.communication)
        val divider = ResourcesCompat.getDrawable(resources, R.drawable.divider, null)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = setAdapter(divider!!, recyclerView, resources)
        recyclerView.itemAnimator?.apply {
            removeDuration = 0
        }
        favoriteMovieListViewModel.observe(this) {
            adapter.updateDataFromAdapter()
        }
        favoriteMovieListViewModel.showList()

    }

    override fun onResume() {
        super.onResume()
        Log.d(Const.PRESENTATION, "${this::class.java.simpleName} is resume")
    }

    override fun onResumeMy() {
        super.onResumeMy()
        favoriteMovieListViewModel.showList()
    }

    private fun showDetails(movie: UiMovie) {
        favoriteMovieListViewModel.addCheckedItem(movie.id)
        movieInfoViewModel.setMovieID(movie.id)
        mainRouter().navigateToDetails(movie)
    }

    companion object {

        fun newInstance() = MovieListFavoritesFragment()
    }
}