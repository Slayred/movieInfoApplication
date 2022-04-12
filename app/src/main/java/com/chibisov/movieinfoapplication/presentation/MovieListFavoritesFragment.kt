package com.chibisov.movieinfoapplication.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.RecyclerView
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

/**
 * A simple [Fragment] subclass.
 * Use the [MovieListFavoritesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MovieListFavoritesFragment : BaseMovieListFragment(), Observer{

    private val repository = Repository(MoviesCacheFavorites, Movies)
    private val baseInteractor = BaseInteractor(repository)
    private val communication = Communication()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MovieAdapter
    private lateinit var fragment: BaseMovieListFragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        requireActivity().onBackPressedDispatcher //custom CallBack for backPressed
//            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
//                override fun handleOnBackPressed() {
//                    parentFragmentManager.popBackStack()
//                }
//            })
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
        fragment = MovieInfoFragment()
        recyclerView = view.findViewById(R.id.movieRVFavor)
        adapter = MovieAdapter(MovieType.Favorite, object : MovieAdapter.FavoriteClickListener {
            override fun change(movie: UiMovie) {
                baseInteractor.changeStatus(movie)
                val t = baseInteractor.showFavorites()
                communication.showUiMovieList(t)

            }
        }, object : MovieAdapter.DetailsCLickListener {
            override fun details(movie: UiMovie) {
                showDetails(movie)
            }
        }, communication)
        val divider = ResourcesCompat.getDrawable(resources, R.drawable.divider, null)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = setAdapter(divider!!, recyclerView, resources)

        communication.showUiMovieList(ArrayList(baseInteractor.showFavorites()))
    }

    private fun showDetails(movie: UiMovie) {
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