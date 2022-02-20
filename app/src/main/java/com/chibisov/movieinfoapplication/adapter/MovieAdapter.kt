package com.chibisov.movieinfoapplication.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chibisov.movieinfoapplication.Communication
import com.chibisov.movieinfoapplication.core.MovieType
import com.chibisov.movieinfoapplication.R
import com.chibisov.movieinfoapplication.data.models.UiMovie

class MovieAdapter(
    private val type: MovieType,
    private val listener: FavoriteClickListener,
    private val detailsCLickListener: DetailsCLickListener,
    private val communication: Communication
) :
    RecyclerView.Adapter<MovieAdapter.MyViewHolder>() {


    fun updateDataFromAdapter() {
//       notifyDataSetChanged()
        communication.getDiffResult().dispatchUpdatesTo(this)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_layout,
            parent, false)
        return when (type) {
            MovieType.Common -> MyViewHolder.Base(view)
            MovieType.Favorite -> MyViewHolder.Favorite(view, listener, detailsCLickListener)
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(communication.getUIMoviesList()[position])

    }

    override fun getItemCount(): Int {
        return communication.getUIMoviesList().size
    }

    abstract class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val movieName: TextView = itemView.findViewById(R.id.movieName)
        private val moviePoster: ImageView = itemView.findViewById(R.id.moviePoster)
        protected val movieFavorite: ImageView = itemView.findViewById(R.id.movieFavorite)
        protected val movieDetails: Button = itemView.findViewById(R.id.movieDetails)
        open fun bind(model: UiMovie) {
            movieName.text = model.name
            moviePoster.setImageResource(model.poster)
        }

        class Base(view: View) : MyViewHolder(view) {
            override fun bind(model: UiMovie) {
                super.bind(model)
                movieFavorite.visibility = View.INVISIBLE
            }
        }

        class Favorite(view: View, private val listener: FavoriteClickListener,
        private val detailsListener: DetailsCLickListener) :
            MyViewHolder(view) {
            override fun bind(model: UiMovie) {
                super.bind(model)
                movieFavorite.visibility = View.VISIBLE
                movieFavorite
                    .setImageResource(
                        if (model.status) R.drawable.baseline_favorite_24
                        else R.drawable.baseline_favorite_border_24
                    )
                movieFavorite.setOnClickListener {
                    listener.change(model)
                }
                movieDetails.setOnClickListener {
                    detailsListener.details(model)
                }

            }
        }
    }

    interface FavoriteClickListener {
        fun change(movie: UiMovie)
    }
    interface FavoriteClickDeleteListener: FavoriteClickListener{
        fun change(movie: UiMovie, position: Int)
    }

    interface DetailsCLickListener {
        fun details(movie: UiMovie)
    }




}