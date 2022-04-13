package com.chibisov.movieinfoapplication.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toolbar
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.setFragmentResult
import com.chibisov.movieinfoapplication.R
import com.chibisov.movieinfoapplication.core.Const
import com.chibisov.movieinfoapplication.data.Movies
import com.chibisov.movieinfoapplication.data.MoviesCacheFavorites
import com.chibisov.movieinfoapplication.data.Repository
import com.chibisov.movieinfoapplication.data.models.UiMovie
import com.chibisov.movieinfoapplication.domain.BaseInteractor
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MovieInfoFragment : BaseMovieListFragment() {

    private val repository = Repository(MoviesCacheFavorites, Movies)
    private val baseInteractor = BaseInteractor(repository)

    private lateinit var movieFavourites: FloatingActionButton
    private lateinit var movieDescr: TextView
    private lateinit var movieName: androidx.appcompat.widget.Toolbar
    private lateinit var moviePoster: ImageView
    private lateinit var comment: EditText
    private var movie: UiMovie? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        movie = arguments?.getParcelable(Const.MOVIE)
        return inflater.inflate(R.layout.fragment_movie_info_coordinator, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        requireActivity().onBackPressedDispatcher //custom CallBack for backPressed
            .addCallback(this){
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
        movieFavourites = view.findViewById(R.id.fab)
        movieDescr = view.findViewById(R.id.nested_description)
        movieName = view.findViewById(R.id.toolbar)
        moviePoster = view.findViewById(R.id.main_backdrop)
        comment = view.findViewById(R.id.nested_comment)

        movie?.let { setMovie(it) }
        movieFavourites.setOnClickListener{
            movie!!.status = !movie!!.status
            baseInteractor.changeStatus(movie!!)
            setFavourites(movie!!)
        }

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