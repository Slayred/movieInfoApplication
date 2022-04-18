package com.chibisov.movieinfoapplication.presentation

import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.drawable.Drawable
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chibisov.movieinfoapplication.presentation.adapter.itemDecoration.CustomHorizontalItemDecoration
import com.chibisov.movieinfoapplication.presentation.adapter.itemDecoration.CustomVerticalItemDecoration

abstract class BaseMovieListFragment : Fragment() {

    fun setAdapter(
        drawable: Drawable,
        recyclerView: RecyclerView, resources: Resources
    ): RecyclerView.LayoutManager {
        return if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.addItemDecoration(
                CustomVerticalItemDecoration(drawable)
            )
            LinearLayoutManager(
                activity,
                LinearLayoutManager.VERTICAL,
                false
            )
        } else {
            recyclerView.addItemDecoration(
                CustomHorizontalItemDecoration(drawable)
            )
            GridLayoutManager(activity, 2)
        }
    }


}