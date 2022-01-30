package com.chibisov.movieinfoapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chibisov.movieinfoapplication.Movie
import com.chibisov.movieinfoapplication.R

class MovieAdapter: RecyclerView.Adapter<MovieAdapter.MyViewHolder>() {

    var dataList = emptyList<Movie>()








    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.movieName).text = dataList[position].name
        holder.itemView.findViewById<ImageView>(R.id.moviePoster).setImageResource(dataList[position].poster)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }


    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }
}