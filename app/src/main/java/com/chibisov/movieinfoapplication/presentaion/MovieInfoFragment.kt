package com.chibisov.movieinfoapplication.presentaion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import com.chibisov.movieinfoapplication.R
import com.chibisov.movieinfoapplication.core.Const
import com.chibisov.movieinfoapplication.data.Movies
import com.chibisov.movieinfoapplication.data.MoviesCacheFavorites
import com.chibisov.movieinfoapplication.data.Repository
import com.chibisov.movieinfoapplication.data.models.UiMovie
import com.chibisov.movieinfoapplication.domain.BaseInteractor

/**
 * A simple [Fragment] subclass.
 * Use the [MovieInfoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MovieInfoFragment : BaseMovieListFragment() {

    private val repository = Repository(MoviesCacheFavorites, Movies)
    private val baseInteractor = BaseInteractor(repository)

    private lateinit var movieFavourites : ImageView
    private lateinit var movieDescr: TextView
    private lateinit var movieName: TextView
    private lateinit var moviePoster: ImageView
    private lateinit var comment: EditText
    private var movie: UiMovie? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        setFragmentListener()
        movie = arguments?.getParcelable(Const.MOVIE)
        return inflater.inflate(R.layout.fragment_movie_info, container, false)
    }

//    private fun setFragmentListener() {
//        setFragmentResultListener(Const.MOVIE){
//                _, bundle ->
//           movie =  bundle.getParcelable(Const.BUNDLE)
//        }
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieFavourites = view.findViewById(R.id.favorites)
        movieDescr = view.findViewById(R.id.movieDescrInfo)
        movieName = view.findViewById(R.id.movieNameInfo)
        moviePoster = view.findViewById(R.id.infoImageView)
        comment = view.findViewById(R.id.commentET)


        movie?.let { setMovie(it) }

        requireActivity().onBackPressedDispatcher //custom CallBack for backPressed
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true){
                override fun handleOnBackPressed() {
                    TODO("Not yet implemented")
                }

            })
    }

    private fun setMovie(movie: UiMovie) {
        movieName.text = movie.name
        movieDescr.text = movie.description
        movie.poster.let { moviePoster.setImageResource(it) }
        setFavourites(movie)
    }

    private fun setFavourites(movie: UiMovie) {
        movieFavourites.setImageResource(checkFavourites(movie.status))
    }

    private fun checkFavourites(status: Boolean): Int {
        return when(status){
            true -> R.drawable.baseline_favorite_24
            false -> R.drawable.baseline_favorite_border_24
        }
    }



//    override fun onBackPressed() {
//        data.putExtra(Const.COMMENT, comment.text.toString())
//        data.putExtra(Const.MOVIE, movie)
//        setResult(AppCompatActivity.RESULT_OK, data)
//        finish()
//        super.onBackPressed()
//    }
}