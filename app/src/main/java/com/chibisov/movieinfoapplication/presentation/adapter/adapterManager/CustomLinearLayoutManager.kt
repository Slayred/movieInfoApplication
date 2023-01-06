package com.chibisov.movieinfoapplication.presentation.adapter.adapterManager

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chibisov.movieinfoapplication.core.Const

class CustomLinearLayoutManager(context: Context, orientation: Int, reverseLayout: Boolean) :
    LinearLayoutManager(context, orientation, reverseLayout) {
        //TODO need fix call change livedata from another thread
    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        try {
            super.onLayoutChildren(recycler, state)
        } catch (e: IndexOutOfBoundsException) {
            Log.d(Const.PRESENTATION, "Out of boundes(")
        }

    }
}