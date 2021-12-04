package com.example.movies.ui.movies.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.R
import com.example.movies.adapter.MediaClickListener
import com.example.movies.databinding.MovieCardBinding
import com.example.movies.model.entity.Movie
import com.example.movies.utils.Constants
import com.example.movies.utils.MediaUtils

class MoviesAdapter(val context: Context,val listener: MediaClickListener) : RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

    private var moviesList= mutableListOf<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        val binding=DataBindingUtil.inflate<MovieCardBinding>(inflater,R.layout.movie_card,parent,false)
        return MoviesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val movie=moviesList[position]
        holder.bind(movie)
    }

    override fun getItemCount()=moviesList.size

    fun setData(moviesList:List<Movie>){
        this.moviesList.addAll(MediaUtils.convert(moviesList))
        notifyDataSetChanged()
    }

   inner class MoviesViewHolder(private val binding: MovieCardBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(movie: Movie) =with(binding){
            this.movie=movie
            binding.root.setOnClickListener {
                listener.onItemClick(Constants.MOVIE,movie.id)
            }
        }
    }


}