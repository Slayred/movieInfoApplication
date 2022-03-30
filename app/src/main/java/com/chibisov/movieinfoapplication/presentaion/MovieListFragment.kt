package com.chibisov.movieinfoapplication.presentaion

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.res.ResourcesCompat
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
import com.google.android.material.snackbar.Snackbar

class MovieListFragment: BaseMovieListFragment(), Observer {

    private lateinit var inviteBtn: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MovieAdapter
    private lateinit var fragment: BaseMovieListFragment
    private val repository = Repository(MoviesCacheFavorites, Movies)
    private val baseInteractor = BaseInteractor(repository)
    private val communication = Communication()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(Const.BUNDLE){
            _, bundle ->
            val result = bundle.getParcelable<UiMovie>(Const.MOVIE)
            Log.d("MAINFRAGMENT","Movie name is ${result?.name}")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("BaseFragmentTag", "onCreateView()1 ${javaClass.simpleName}")
        return inflater.inflate(R.layout.movie_list, container, false)
    }

    override fun onStart() {
        super.onStart()
        communication.add(this)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragment = MovieInfoFragment()
        inviteBtn = view.findViewById(R.id.inviteBtn)
        recyclerView = view.findViewById(R.id.movieRV)
        adapter = MovieAdapter(MovieType.Favorite, object : MovieAdapter.FavoriteClickListener {
            override fun change(movie: UiMovie) {
                    baseInteractor.changeStatus(movie)
                    val t = baseInteractor.showUIList()
                    communication.showUiMovieList(t)
            }
        }, object : MovieAdapter.DetailsCLickListener {
            override fun details(movie: UiMovie) {
                showDetails(movie)
            }
        }
            ,communication)
        val divider = ResourcesCompat.getDrawable(resources, R.drawable.divider, null)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = setAdapter(divider!!, recyclerView, resources)

        communication.showUiMovieList(baseInteractor.showUIList())
        inviteBtn.setOnClickListener {
            share()
        }


    }
    private fun showDetails(movie: UiMovie){
        baseInteractor.addCheckedItem(movie)
//        parentFragmentManager.setFragmentResult(Const.MOVIE,
//            bundleOf(Const.BUNDLE to movie))
        val bundle = Bundle()
        bundle.putParcelable(Const.MOVIE, movie)
        fragment.arguments = bundle
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.main_fragment_container, fragment)
        transaction.addToBackStack(fragment.javaClass.name)
        transaction.commit()
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