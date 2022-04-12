package com.chibisov.movieinfoapplication.presentaion

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
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.RecyclerView
import com.chibisov.movieinfoapplication.R
import com.chibisov.movieinfoapplication.adapter.MovieAdapter
import com.chibisov.movieinfoapplication.core.Const
import com.chibisov.movieinfoapplication.core.IOnBackPressed
import com.chibisov.movieinfoapplication.core.MovieType
import com.chibisov.movieinfoapplication.core.Observer
import com.chibisov.movieinfoapplication.data.Movies
import com.chibisov.movieinfoapplication.data.MoviesCacheFavorites
import com.chibisov.movieinfoapplication.data.Repository
import com.chibisov.movieinfoapplication.data.models.UiMovie
import com.chibisov.movieinfoapplication.domain.BaseInteractor
import com.chibisov.movieinfoapplication.domain.Communication

class MovieListFragment : BaseMovieListFragment(), Observer, IOnBackPressed {

    private lateinit var inviteBtn: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MovieAdapter
    private lateinit var fragment: BaseMovieListFragment
    private val repository = Repository(MoviesCacheFavorites, Movies)
    private val baseInteractor = BaseInteractor(repository)
    private val communication = Communication()

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
        }, communication)
        val divider = ResourcesCompat.getDrawable(resources, R.drawable.divider, null)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = setAdapter(divider!!, recyclerView, resources)

        communication.showUiMovieList(baseInteractor.showUIList())
        inviteBtn.setOnClickListener {
            share()
        }


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
//        transaction.replace(R.id.main_fragment_container, fragment)
        //Пытаемся поменять фрагмент для бэкстека
        transaction.replace(R.id.home_fragment_container, fragment)
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

    override fun onBackPressed(): Boolean {
        return true
    }

}