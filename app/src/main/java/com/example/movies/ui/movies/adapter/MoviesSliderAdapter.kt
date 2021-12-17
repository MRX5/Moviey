package com.example.movies.ui.movies.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.example.movies.R
import com.example.movies.listener.OnMovieClickListener
import com.example.movies.data.model.entity.Movie
import com.example.movies.utils.Constants
import com.example.movies.utils.MediaUtils
import com.smarteist.autoimageslider.SliderViewAdapter
import com.squareup.picasso.Picasso

class MoviesSliderAdapter(private val listener: OnMovieClickListener) :
    SliderViewAdapter<MoviesSliderAdapter.SliderViewHolder>() {

    private var data = mutableListOf<Movie>()

    override fun getCount() = data.size

    fun setData(data: List<Movie>) {
        data.subList(0, 9)?.let {
            this.data = MediaUtils.convert(data.subList(0, 9)) as MutableList<Movie>
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup): SliderViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.slider_item_layout, parent, false)
        return SliderViewHolder(view)
    }

    override fun onBindViewHolder(holder: SliderViewHolder?, position: Int) {
        holder?.bind(data[position])
    }

    inner class SliderViewHolder(itemView: View?) : SliderViewAdapter.ViewHolder(itemView) {
        private val backdropImage = itemView?.findViewById<ImageView>(R.id.backdrop_image)
        private val titleTextView = itemView?.findViewById<TextView>(R.id.title_text)
        private val rateTextView = itemView?.findViewById<TextView>(R.id.rate_text)
        private val releaseDateTextView = itemView?.findViewById<TextView>(R.id.release_date_text)
        private val rateBar = itemView?.findViewById<RatingBar>(R.id.rate_bar)

        fun bind(movie: Movie) {
            releaseDateTextView?.text = movie.release_date
            titleTextView?.text = movie.title
            rateTextView?.text = movie.vote.toString()
            rateBar?.rating = MediaUtils.getActualRate(movie.vote)
            Picasso.get().load(Constants.TMDB_BACKDROP_PATH + movie.backdrop).into(backdropImage)
            itemView.setOnClickListener {
                listener.onMovieClick(movie.id)
            }
        }
    }

}