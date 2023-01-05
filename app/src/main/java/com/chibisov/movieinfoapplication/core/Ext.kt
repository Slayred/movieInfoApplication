package com.chibisov.movieinfoapplication.core

import androidx.fragment.app.Fragment
import com.chibisov.movieinfoapplication.presentation.MainRouter
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import kotlin.math.abs

fun Fragment.mainRouter(): MainRouter {
    return requireActivity() as MainRouter
}

fun AppBarLayout.collapsingPercentage(): Float {
    return abs(this.height - this.bottom) / this.totalScrollRange.toFloat()
}