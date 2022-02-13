package com.chibisov.movieinfoapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.chibisov.movieinfoapplication.data.models.Movie
import com.chibisov.movieinfoapplication.R
import com.chibisov.movieinfoapplication.adapter.util.MovieDiffUtil
import com.chibisov.movieinfoapplication.data.models.UiMovie

class MovieAdapter: RecyclerView.Adapter<MovieAdapter.MyViewHolder>() {

    private var dataList = emptyList<UiMovie>()

    fun show(data:List<UiMovie>){
        val movieDiffUtil = MovieDiffUtil(dataList, data)
        val movieDiffUtilResult = DiffUtil.calculateDiff(movieDiffUtil)
        this.dataList = data as MutableList<UiMovie>
        movieDiffUtilResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.movieName).text = dataList[position].name
        holder.itemView.findViewById<ImageView>(R.id.moviePoster).setImageResource(dataList[position].poster)
        holder.itemView.findViewById<TextView>(R.id.movieName).setTextColor(dataList[position].checkStatus())
        //holder.itemView.findViewById<ImageView>(R.id.movieFavorite).setImageResource(dataList[position].favorites)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }
}