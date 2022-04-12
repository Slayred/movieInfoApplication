package com.chibisov.movieinfoapplication.adapter.util

import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import com.chibisov.movieinfoapplication.data.models.Movie
import com.chibisov.movieinfoapplication.data.models.UiMovie

class MovieDiffUtil(
    private val oldList: ArrayList<UiMovie>, private val newList: ArrayList<UiMovie>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(
        oldItemPosition: Int, newItemPosition: Int
    ) = oldList[oldItemPosition]
        .same(newList[newItemPosition])


    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        Log.d(
            "DIFFUTIL",
            "OLD  ID = ${oldList[oldItemPosition].id}, status = ${oldList[oldItemPosition].status}" +
                    "OLD  ID = ${newList[newItemPosition].id}, status = ${newList[newItemPosition].status}"
        )
        return oldList[oldItemPosition].id == newList[newItemPosition].id
                && oldList[oldItemPosition].description == newList[newItemPosition].description
                && oldList[oldItemPosition].name == newList[newItemPosition].name
                && oldList[oldItemPosition].poster == newList[newItemPosition].poster
                && oldList[oldItemPosition].status == newList[newItemPosition].status
    }
}