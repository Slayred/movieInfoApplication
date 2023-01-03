package com.chibisov.movieinfoapplication.presentation

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment

open class BaseFragment: Fragment() {

    open fun onBackPressed(): Boolean {
        Log.d("BACKSTACK", "Press Back FROM OPEN CLASS")
        return false
    }

    open fun showToast(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show()
    }
}