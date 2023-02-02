package com.example.movies.ui.movies.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.R
import com.example.movies.databinding.MovieCardBinding
import com.example.movies.listener.OnMovieClickListener
import com.example.movies.data.model.entity.Movie
import com.example.movies.utils.MediaUtils
import com.example.movies.utils.Utils.Companion.roundDoubleToOneNumber

class MoviesAdapter(val context: Context,val listener: OnMovieClickListener) : RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

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
            movie.vote = movie.vote?.roundDoubleToOneNumber()
            this.movie=movie
            binding.root.setOnClickListener {
                listener.onMovieClick(movie.id)
            }
        }
    }


}