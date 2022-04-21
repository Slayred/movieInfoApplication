package com.chibisov.movieinfoapplication.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chibisov.movieinfoapplication.MovieInfoApp
import com.chibisov.movieinfoapplication.R
import com.chibisov.movieinfoapplication.presentation.adapter.MovieAdapter
import com.chibisov.movieinfoapplication.core.Const
import com.chibisov.movieinfoapplication.core.MovieType
import com.chibisov.movieinfoapplication.data.models.UiMovie
import com.chibisov.movieinfoapplication.viewmodels.MovieListViewModel
import com.chibisov.movieinfoapplication.viewmodels.SharedMovieViewModel
import com.google.android.material.snackbar.Snackbar

class MovieListFragment : BaseMovieListFragment() {

    private lateinit var inviteBtn: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MovieAdapter
    private lateinit var fragment: Fragment
    private lateinit var listViewModel: MovieListViewModel
    private lateinit var sharedMovieViewModel: SharedMovieViewModel
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(Const.BUNDLE) { _, bundle ->
            val resultMovie = bundle.getParcelable<UiMovie>(Const.MOVIE)
            val resultComment = bundle.getString(Const.COMMENT)
            Log.d(
                "MAINFRAGMENT",
                "Movie status is ${resultMovie?.status} \n Comment is $resultComment"
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("BaseFragmentTag", "onCreateView()1 ${javaClass.simpleName}")
        requireActivity().onBackPressedDispatcher //custom CallBack for backPressed
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    val alertDialog = AlertDialog.Builder(requireContext())
                    alertDialog.setTitle(resources.getText(R.string.exit))
                    alertDialog.setMessage(resources.getText(R.string.exit_question))
                    alertDialog.setPositiveButton(resources.getText(R.string.yes)) { _, _ ->
                        requireActivity().finish()
                    }
                    alertDialog.setNegativeButton(resources.getText(R.string.no)) { _, _ -> }
                    alertDialog.show()
                }
            })
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listViewModel = (requireActivity().application as MovieInfoApp).movieListViewModel
        sharedMovieViewModel = (requireActivity().application as MovieInfoApp).sharedMovieViewModel
        fragment = MovieInfoFragment()
        inviteBtn = view.findViewById(R.id.inviteBtn)
        recyclerView = view.findViewById(R.id.movieRV)
        swipeRefreshLayout = view.findViewById(R.id.movie_list_swipe_refresh)
        adapter = MovieAdapter(MovieType.Favorite, object : MovieAdapter.FavoriteClickListener {
            override fun change(movie: UiMovie) {
                listViewModel.changeStatus(movie)
                Snackbar.make(
                    view,
                    "${movie.name} " + getString(R.string.change_status),
                    Snackbar.LENGTH_LONG
                )
                    .setAnchorView(R.id.btm_nav)
                    .setAction(getString(R.string.undo)) {
                        listViewModel.changeStatus(movie)
                    }.show()
            }
        }, object : MovieAdapter.DetailsCLickListener {
            override fun details(movie: UiMovie) {
                showDetails(movie)
            }
        }, listViewModel.communication)
        val divider = ResourcesCompat.getDrawable(resources, R.drawable.divider, null)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = setAdapter(divider!!, recyclerView, resources)
        (recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

        listViewModel.showList()

        listViewModel.observe(this ){
            adapter.updateDataFromAdapter()
        }

        swipeRefreshLayout.setOnRefreshListener{
            listViewModel.showList()
            swipeRefreshLayout.isRefreshing = false
        }

        inviteBtn.setOnClickListener {
            share()
        }
    }

    private fun showDetails(movie: UiMovie) {
        listViewModel.addCheckedItem(movie)
        sharedMovieViewModel.setMovieID(movie.id)
        val fragment = MovieInfoFragment()
        parentFragmentManager.setFragmentResult(
            Const.MOVIE,
            bundleOf(Const.BUNDLE to movie)
        )
        val bundle = Bundle()
        bundle.putParcelable(Const.MOVIE, movie)
        fragment.arguments = bundle
        val transaction = parentFragmentManager.beginTransaction()
        //Пытаемся поменять фрагмент для бэкстека
        transaction.replace(R.id.home_fragment_container, fragment)
//        transaction.replace(R.id.main_fragment_container, fragment)
        transaction.addToBackStack(fragment.javaClass.name)
        transaction.commit()
    }

    private fun share() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.invite_text))
        startActivity(Intent.createChooser(intent, getString(R.string.share_via)))
    }


}