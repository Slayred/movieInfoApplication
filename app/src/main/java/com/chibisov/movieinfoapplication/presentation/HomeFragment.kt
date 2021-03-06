package com.chibisov.movieinfoapplication.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chibisov.movieinfoapplication.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeFragment : Fragment() {

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
        replaceFragment(MovieListFragment())

        bottomBar.setOnItemSelectedListener {
            val fragment = this.parentFragmentManager.findFragmentById(R.id.home_fragment_container)
            when (it.itemId) {
                R.id.ic_movie_list -> if (fragment !is MovieListFragment) replaceFragment(
                    MovieListFragment()
                )
                R.id.ic_favorites -> if (fragment !is MovieListFavoritesFragment) replaceFragment(
                    MovieListFavoritesFragment()
                )
            }
            true
        }
    }


    private fun replaceFragment(fragment: Fragment) {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.home_fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()

    }
}