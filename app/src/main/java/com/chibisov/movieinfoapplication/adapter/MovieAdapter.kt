package com.chibisov.movieinfoapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.chibisov.movieinfoapplication.MovieType
import com.chibisov.movieinfoapplication.data.models.Movie
import com.chibisov.movieinfoapplication.R
import com.chibisov.movieinfoapplication.adapter.util.MovieDiffUtil
import com.chibisov.movieinfoapplication.data.models.UiMovie
import org.w3c.dom.Text

class MovieAdapter(private val type: MovieType, private val listener: FavoriteClickListener): RecyclerView.Adapter<MovieAdapter.MyViewHolder>() {

    private var dataList = emptyList<UiMovie>()

    fun show(data:List<UiMovie>){
        val movieDiffUtil = MovieDiffUtil(dataList, data)
        val movieDiffUtilResult = DiffUtil.calculateDiff(movieDiffUtil)
        this.dataList = data
        movieDiffUtilResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_layout, parent, false)
        return when(type){
            MovieType.Common -> MyViewHolder.Base(view)
            MovieType.Favorite -> MyViewHolder.Favorite(view, listener)
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        holder.itemView.findViewById<TextView>(R.id.movieName).text = dataList[position].name
//        holder.itemView.findViewById<ImageView>(R.id.moviePoster).setImageResource(dataList[position].poster)
//        holder.itemView.findViewById<TextView>(R.id.movieName).setTextColor(dataList[position].checkStatus())
        holder.bind(dataList[position])

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    abstract class MyViewHolder(private val itemView: View): RecyclerView.ViewHolder(itemView) {
        protected val movieName: TextView = itemView.findViewById(R.id.movieName)
        protected val moviePoster: ImageView = itemView.findViewById(R.id.moviePoster)
        protected val movieFavorite: ImageView = itemView.findViewById(R.id.movieFavorite)
        open fun bind(model: UiMovie) {
            movieName.text = model.name
            moviePoster.setImageResource(model.poster)
        }

        class Base(view: View): MyViewHolder(view){
            override fun bind(model: UiMovie) {
                super.bind(model)
                movieFavorite.visibility = View.INVISIBLE
            }
        }
        class Favorite(view: View, private val listener: FavoriteClickListener):MyViewHolder(view){
            override fun bind(model: UiMovie) {
                super.bind(model)
                movieFavorite.visibility = View.VISIBLE
                movieFavorite.setImageResource(if (model.checked) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24)
                movieFavorite.setOnClickListener {
                    listener.change(model)
                }
            }
        }
    }

    interface FavoriteClickListener{
        fun change(movie: UiMovie)
    }
}