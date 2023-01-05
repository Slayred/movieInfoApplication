package com.chibisov.movieinfoapplication.presentation


import com.chibisov.movieinfoapplication.data.models.UiMovie


interface MainRouter {

 fun navigateToHomeFragment()

 fun navigateToDetails(movie: UiMovie )

}