package com.chibisov.movieinfoapplication.presentation

import android.util.Log
import androidx.fragment.app.Fragment

open class BaseFragment: Fragment() {

    open fun onBackPressed(): Boolean {
        Log.d("BACKSTACK", "Press Back FROM OPEN CLASS")
        return false
    }
}