package com.chibisov.movieinfoapplication.adapter.util

import androidx.recyclerview.widget.DiffUtil
import com.chibisov.movieinfoapplication.data.models.Movie

class MovieDiffUtil(private val oldList: List<Movie>
                    , private val newList: List<Movie>): DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =  oldList[oldItemPosition] === newList[newItemPosition]


    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
                && oldList[oldItemPosition].description == newList[newItemPosition].description
                && oldList[oldItemPosition].name == newList[newItemPosition].name
                && oldList[oldItemPosition].poster == newList[newItemPosition].poster
                && oldList[oldItemPosition].favorites == newList[newItemPosition].favorites
    }
}