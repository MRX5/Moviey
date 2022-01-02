package com.example.movies.ui.favourites.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.R
import com.example.movies.data.local.entites.MediaEntity
import com.example.movies.data.model.entity.Movie
import com.example.movies.data.model.entity.TV_Show
import com.example.movies.databinding.MovieCardBinding
import com.example.movies.databinding.TvShowCardBinding
import com.example.movies.listener.OnMovieClickListener
import com.example.movies.listener.OnTvShowClickListener
import com.example.movies.utils.Constants

class FavouritesAdapter(
    private val movieListener: OnMovieClickListener,
    private val tvShowListener: OnTvShowClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var favouritesList = mutableListOf<MediaEntity>()
    override fun getItemViewType(position: Int): Int {
        return if (favouritesList[position].media_type == Constants.MOVIE) 0 else 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            0 -> MoviesViewHolder(
                DataBindingUtil.inflate(
                    inflater, R.layout.movie_card, parent, false
                )
            )
            else -> {
                TvShowsViewHolder(
                    DataBindingUtil.inflate(
                        inflater, R.layout.tvshow_card, parent, false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val media = favouritesList[position]
        when (holder) {
            is MoviesViewHolder -> holder.bind(
                Movie(
                    media.id,
                    media.title,
                    media.poster ?: "",
                    vote = media.vote,
                    release_date = media.release_date
                )
            )
            is TvShowsViewHolder -> {
                holder.bind(
                    TV_Show(
                        media.id,
                        media.title,
                        media.poster ?: "",
                        vote = media.vote,
                        release_date = media.release_date
                    )
                )
            }
        }
    }

    override fun getItemCount() = favouritesList.size

    fun setData(favouritesList: List<MediaEntity>) {
        this.favouritesList = favouritesList as MutableList<MediaEntity>
        notifyDataSetChanged()
    }

    inner class MoviesViewHolder(private val binding: MovieCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) = with(binding) {
            this.movie = movie
            itemView.setOnClickListener {
                movieListener.onMovieClick(movie.id)
            }
        }
    }

    inner class TvShowsViewHolder(private val binding: TvShowCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tvShows: TV_Show) = with(binding) {
            this.tvShow = tvShows
            itemView.setOnClickListener {
                tvShowListener.onTvShowClick(tvShows.id)
            }
        }
    }
}