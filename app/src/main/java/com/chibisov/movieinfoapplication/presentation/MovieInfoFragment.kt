package com.chibisov.movieinfoapplication.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.setFragmentResult
import com.chibisov.movieinfoapplication.MovieInfoApp
import com.chibisov.movieinfoapplication.R
import com.chibisov.movieinfoapplication.core.Const
import com.chibisov.movieinfoapplication.data.MovieNetDataSource
import com.chibisov.movieinfoapplication.data.Movies
import com.chibisov.movieinfoapplication.data.MoviesCacheFavorites
import com.chibisov.movieinfoapplication.data.Repository
import com.chibisov.movieinfoapplication.data.models.UiMovie
import com.chibisov.movieinfoapplication.data.net.MovieService
import com.chibisov.movieinfoapplication.data.retrofit.RetrofitFactory
import com.chibisov.movieinfoapplication.domain.BaseInteractor
import com.chibisov.movieinfoapplication.domain.StateMovie
import com.chibisov.movieinfoapplication.viewmodels.SharedMovieViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MovieInfoFragment : BaseMovieListFragment() {

    var BASE_URL = "https://kinopoiskapiunofficial.tech/api/v2.2/"
    private val repository = Repository(MoviesCacheFavorites, Movies, MovieNetDataSource(
        RetrofitFactory.getRetrofitInstance(BASE_URL).create(MovieService::class.java)))
    private val baseInteractor = BaseInteractor(repository)
    private lateinit var sharedMovieViewModel : SharedMovieViewModel

    private lateinit var movieFavourites: FloatingActionButton
    private lateinit var movieDescr: TextView
    private lateinit var movieName: androidx.appcompat.widget.Toolbar
    private lateinit var moviePoster: ImageView
    private lateinit var comment: EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var coordinatorLayout: CoordinatorLayout
    private var movie: UiMovie? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        movie = arguments?.getParcelable(Const.MOVIE)
        Log.d(this::javaClass.toString(), "OnCreateView")
        return inflater.inflate(R.layout.fragment_movie_info_coordinator, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(this::javaClass.toString(), "OnCreate")
        sharedMovieViewModel = (requireActivity().application as MovieInfoApp).sharedMovieViewModel
        requireActivity().onBackPressedDispatcher //custom CallBack for backPressed
            .addCallback(this) {
                val movieBack = movie
                val comment = comment.text.toString()
                setFragmentResult(
                    Const.BUNDLE,
                    bundleOf(Const.MOVIE to movieBack, Const.COMMENT to comment)
                )
                parentFragmentManager.popBackStack()
            }
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(this::javaClass.toString(), "OnViewCreated")
        coordinatorLayout = view.findViewById(R.id.included)
        movieFavourites = view.findViewById(R.id.fab)
        movieDescr = view.findViewById(R.id.nested_description)
        movieName = view.findViewById(R.id.toolbar)
        moviePoster = view.findViewById(R.id.main_backdrop)
        comment = view.findViewById(R.id.nested_comment)
        progressBar = view.findViewById(R.id.progress_bar)


        movieFavourites.setOnClickListener {
            movie!!.status = !movie!!.status
            baseInteractor.changeStatus(movie!!)
            setFavourites(movie!!)
        }

        sharedMovieViewModel.observeStateMovie(this) {
            when(it){
                is StateMovie.Successful -> {
                    progressBar.isVisible = false
                    coordinatorLayout.isVisible = true
                    setMovie(it.uiMovie)
                }
                is StateMovie.Progress -> {
                    coordinatorLayout.isVisible = false
                    progressBar.isVisible = true
                }
            }
        }
        sharedMovieViewModel.showStateMovieInfo()

    }

    private fun setMovie(movie: UiMovie) {
        movieName.title = movie.name
        movieDescr.text = movie.description
        movie.poster.let { moviePoster.setImageResource(it) }
        setFavourites(movie)
    }

    private fun setFavourites(movie: UiMovie) {
        movieFavourites.setImageResource(checkFavourites(movie.status))
    }

    private fun checkFavourites(status: Boolean): Int {
        return when (status) {
            true -> R.drawable.baseline_favorite_24
            false -> R.drawable.baseline_favorite_border_24
        }
    }

}