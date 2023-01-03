package com.chibisov.movieinfoapplication.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.addCallback
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.bumptech.glide.Glide
import com.chibisov.movieinfoapplication.MovieInfoApp
import com.chibisov.movieinfoapplication.R
import com.chibisov.movieinfoapplication.core.Const
import com.chibisov.movieinfoapplication.data.models.UiMovie
import com.chibisov.movieinfoapplication.databinding.FragmentMovieInfoCoordinatorBinding

import com.chibisov.movieinfoapplication.domain.StateMovie
import com.chibisov.movieinfoapplication.viewmodels.SharedMovieViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MovieInfoFragment : BaseFragment() {

    private lateinit var sharedMovieViewModel : SharedMovieViewModel
    private var movie: UiMovie? = null
    private lateinit var binding: FragmentMovieInfoCoordinatorBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        movie = arguments?.getParcelable(Const.MOVIE)
        Log.d(this::javaClass.toString(), "OnCreateView")
        binding = FragmentMovieInfoCoordinatorBinding.inflate(inflater, container, false)
        return binding.root
//        return inflater.inflate(R.layout.fragment_movie_info_coordinator, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(this::javaClass.toString(), "OnCreate")
        sharedMovieViewModel = (requireActivity().application as MovieInfoApp).sharedMovieViewModel
        requireActivity().onBackPressedDispatcher //custom CallBack for backPressed
            .addCallback(this) {
                val movieBack = movie
//                val comment = comment.text.toString()
                val comment = binding.included.nestedComment.text.toString()
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
        binding.included.fab.setOnClickListener {
            movie!!.status = !movie!!.status
            setFavourites(movie!!)
        }

        sharedMovieViewModel.observeStateMovie(this) {
            when(it){
                is StateMovie.Successful -> {
                    binding.progressBar.isVisible = false
                    binding.included.coordinatorMovieInfo.isVisible = true
                    setMovie(it.uiMovie)
                }
                is StateMovie.Progress -> {
                    binding.included.coordinatorMovieInfo.isVisible = false
                    binding.progressBar.isVisible = true
                }
            }
        }
        sharedMovieViewModel.showStateMovieInfo()

    }

    private fun setMovie(movie: UiMovie) {
        binding.included.toolbar.title = movie.name
        binding.included.nestedDescription.text = movie.description
        Glide.with(requireActivity().applicationContext)
            .load(movie.posterPath)
            .placeholder(R.drawable.baseline_update_24)
//            .into(moviePoster)
            .into(binding.included.mainBackdrop)
        setFavourites(movie)
    }

    private fun setFavourites(movie: UiMovie) {
        binding.included.fab.setImageResource(checkFavourites(movie.status))
    }

    private fun checkFavourites(status: Boolean): Int {
        return when (status) {
            true -> R.drawable.baseline_favorite_24
            false -> R.drawable.baseline_favorite_border_24
        }
    }

    companion object {

        fun newInstance(uiMovie: UiMovie)  = MovieInfoFragment().apply {
            arguments = Bundle().apply {
                putParcelable(Const.MOVIE, uiMovie)
            }
        }
    }

}