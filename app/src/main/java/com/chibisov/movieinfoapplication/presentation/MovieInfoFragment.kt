package com.chibisov.movieinfoapplication.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.chibisov.movieinfoapplication.MovieInfoApp
import com.chibisov.movieinfoapplication.R
import com.chibisov.movieinfoapplication.core.Const
import com.chibisov.movieinfoapplication.data.models.UiMovie
import com.chibisov.movieinfoapplication.databinding.FragmentMovieInfoCoordinatorBinding

import com.chibisov.movieinfoapplication.domain.StateMovie
import com.chibisov.movieinfoapplication.viewmodels.MovieInfoViewModel

class MovieInfoFragment : BaseFragment() {

    private lateinit var movieInfoViewModel : MovieInfoViewModel
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
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(this::javaClass.toString(), "OnCreate")
        movieInfoViewModel = (requireActivity().application as MovieInfoApp).movieInfoViewModel
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(this::javaClass.toString(), "OnViewCreated")
        binding.included.fab.setOnClickListener {
            movie!!.status = !movie!!.status
            setFavouritesImage(movie!!)
            movieInfoViewModel.changeStatus(movie!!.id, movie!!.status)
        }

        movieInfoViewModel.observeStateMovieRx(this) {
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
                is StateMovie.Fail -> {
                    showToast(it.e)
                }
            }

        }


        movieInfoViewModel.showMovieInfoCr(movie!!.id)


    }

    private fun setMovie(movie: UiMovie) {
        binding.included.toolbar.title = movie.name
        binding.included.nestedDescription.text = movie.description
        Glide.with(requireActivity().applicationContext)
            .load(movie.posterPath)
            .placeholder(R.drawable.baseline_update_24)
            .into(binding.included.mainBackdrop)
        setFavouritesImage(movie)
    }

    private fun setFavouritesImage(movie: UiMovie) {
        binding.included.fab.setImageResource(checkFavourites(movie.status))
//        sharedMovieViewModel.changeStatus(movie.id, movie.status)


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