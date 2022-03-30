package com.chibisov.movieinfoapplication.presentaion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chibisov.movieinfoapplication.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeFragment : Fragment() {

    private lateinit var movieListFragment: BaseMovieListFragment
    private lateinit var movieInfoFragment: Fragment
    private lateinit var bottomBar: BottomNavigationView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomBar = view.findViewById(R.id.btm_nav)
        movieListFragment = MovieListFragment()
        movieInfoFragment = MovieListFavoritesFragment()
        replaceFragment(movieListFragment)

        bottomBar.setOnItemSelectedListener {
            when (it.itemId){
                R.id.ic_movie_list -> replaceFragment(movieListFragment)
                R.id.ic_favorites -> replaceFragment(movieInfoFragment)
            }
            true
        }
    }


    private fun replaceFragment(fragment: Fragment){
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.home_fragment_container, fragment)
        transaction.addToBackStack(fragment.javaClass.name)
        transaction.commit()

    }
}