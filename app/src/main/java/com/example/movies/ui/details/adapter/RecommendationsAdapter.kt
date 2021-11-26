package com.example.movies.ui.details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.R
import com.example.movies.adapter.MovieClickListener
import com.example.movies.databinding.MovieCardBinding
import com.example.movies.model.Movie
import com.example.movies.utils.MovieConverter

class RecommendationsAdapter(private val listener:MovieClickListener) :
    RecyclerView.Adapter<RecommendationsAdapter.RecommendationsViewHolder>() {

    private var recommendationsList= mutableListOf<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendationsViewHolder {
        val inflater= LayoutInflater.from(parent.context)
        val binding= DataBindingUtil.inflate<MovieCardBinding>(inflater, R.layout.movie_card,parent,false)
        return RecommendationsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecommendationsViewHolder, position: Int) {
        val movie=recommendationsList[position]
        movie.release_date=MovieConverter.getMovieYear(movie.release_date)
        holder.bind(movie)
    }

    override fun getItemCount()=recommendationsList.size

    fun setData(movies:List<Movie>){
        recommendationsList= movies as MutableList<Movie>
        notifyDataSetChanged()
    }

    inner class RecommendationsViewHolder(private val binding: MovieCardBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(movie:Movie) =with(binding){
            this.movie=movie
            binding.root.setOnClickListener {
                listener.onMovieClick(movie)
            }
        }
    }
}