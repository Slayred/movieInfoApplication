package com.chibisov.movieinfoapplication.presentation

import com.chibisov.movieinfoapplication.data.models.UiMovie

object Screens {



    fun MovieInfoFragment(uiMovie: UiMovie) = lazy { MovieInfoFragment.newInstance(uiMovie) }

    object Home {

        fun Container() = lazy { HomeFragment.newInstance() }

        fun MovieListFavoritesFragment() = lazy { MovieListFavoritesFragment.newInstance() }

        fun MovieListFragment() = lazy { MovieListFragment.newInstance() }

    }


}