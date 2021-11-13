package com.example.movies.adapter

import android.content.Context
import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.R
import com.example.movies.databinding.MovieCardBinding
import com.example.movies.model.Movie
import com.example.movies.utils.MovieConverter
import com.example.movies.utils.Utils
import com.squareup.picasso.Picasso

class MoviesAdapter(val context: Context) : RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

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
        this.moviesList.addAll(MovieConverter.convert(moviesList))
        notifyDataSetChanged()
    }

   inner class MoviesViewHolder(private val binding: MovieCardBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(movie:Movie) =with(binding){
            this.movie=movie
            val w: Int
            val h: Int
            if(context.resources.configuration.orientation==Configuration.ORIENTATION_LANDSCAPE){
                w= Utils.convertPxToDp(context,context.resources.getDimension(R.dimen.movie_card_image_width_landscape))
                h= Utils.convertPxToDp(context,context.resources.getDimension(R.dimen.movie_card_image_height_landscape))
            }else{
                w= Utils.convertPxToDp(context,context.resources.getDimension(R.dimen.movie_card_image_width_portrait))
                h= Utils.convertPxToDp(context,context.resources.getDimension(R.dimen.movie_card_image_height_portrait))
            }
            Picasso.get().load(movie.poster).resize(w,h).into(binding.moviePosterImg)
        }
    }


}