package com.chibisov.movieinfoapplication.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.replace
import androidx.lifecycle.ViewModelProvider
import com.chibisov.movieinfoapplication.R
import com.chibisov.movieinfoapplication.viewmodels.HomeViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeFragment : BaseFragment() {

    private lateinit var bottomBar: BottomNavigationView
    private lateinit var activeFragmentTag: String
//    private lateinit var currentFragment: Fragment
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onResume() {
        super.onResume()
        Log.d("BACKSTACK", "ON RESUME")
    }

    override fun onStop() {
        super.onStop()
        Log.d("BACKSTACK", "ON STOP")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putChar("1",'a')
        Log.d("BACKSTACK", "ON SAVED INSTANCE")
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("BACKSTACK", "ON View CREATED")
        super.onViewCreated(view, savedInstanceState)
        bottomBar = view.findViewById(R.id.btm_nav)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        replaceFragment(MovieListFragment())
        homeViewModel.observe(this){

        }
        Log.d("BACKSTACK", "CURRENT CHILDBACKSTACK IS ${childFragmentManager.fragments}")

        bottomBar.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.ic_movie_list -> {
                    replaceFragment(MovieListFragment())
                    it.isChecked = true
                }
                R.id.ic_favorites ->  {
                    replaceFragment(MovieListFavoritesFragment())
                    it.isChecked = true
                }
            }
            true
        }
    }

    override fun onBackPressed(): Boolean {
        val fragment = childFragmentManager.findFragmentByTag(activeFragmentTag) as BaseFragment?
        if (fragment != null && fragment.onBackPressed()) return true
        if (fragment !is MovieListFragment) {
            replaceFragment(MovieListFragment())
            return true
        }
        return super.onBackPressed()
    }


    private fun replaceFragment(fragment: Fragment) {
        if (!isAdded) return
        activeFragmentTag = fragment::class.java.simpleName
        val activeFragment = childFragmentManager.findFragmentByTag(activeFragmentTag)
        val transaction = childFragmentManager.beginTransaction()
        //TODO ACtive fargment is null bcs it first try
        if (activeFragment != null) {
            childFragmentManager.fragments.forEach {
                transaction.hide(it)
                activeFragment.onResume()
            }
            transaction.show(activeFragment)
            activeFragment.onResume()
        } else {
            transaction.add(R.id.home_fragment_container, fragment, activeFragmentTag)
        }
        Log.d("BACKSTACK", "CURRENT BACKSTACKENTITY IS ${childFragmentManager.backStackEntryCount}")
        transaction.commit()
    }

    companion object {

        fun newInstance() = HomeFragment()
    }
}