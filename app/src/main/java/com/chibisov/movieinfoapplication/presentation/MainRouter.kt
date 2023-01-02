package com.chibisov.movieinfoapplication.presentation

import androidx.fragment.app.Fragment
import com.chibisov.movieinfoapplication.data.models.UiMovie


fun Fragment.mainRouter(): MainRouter {
  return requireActivity() as MainRouter
}

interface MainRouter {

 fun navigateToHomeFragment()

 fun navigateToDetails(movie: UiMovie )

}