package com.example.movies.ui.tvShows.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.example.movies.R
import com.example.movies.listener.OnTvShowClickListener
import com.example.movies.data.model.entity.TV_Show
import com.example.movies.utils.Constants
import com.example.movies.utils.MediaUtils
import com.smarteist.autoimageslider.SliderViewAdapter
import com.squareup.picasso.Picasso

class TvShowsSliderAdapter(private val listener: OnTvShowClickListener) :
    SliderViewAdapter<TvShowsSliderAdapter.SliderViewHolder>() {

    private var data = mutableListOf<TV_Show>()

    override fun getCount() = data.size

    fun setData(data: List<TV_Show>) {
        data.subList(0, 9)?.let {
            this.data = MediaUtils.convertTvShows(data.subList(0, 9)) as MutableList<TV_Show>
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

        fun bind(tvShow: TV_Show) {
            releaseDateTextView?.text = tvShow.release_date
            titleTextView?.text = tvShow.name
            rateTextView?.text = tvShow.vote.toString()
            rateBar?.rating = MediaUtils.getActualRate(tvShow.vote)
            Picasso.get().load(Constants.TMDB_BACKDROP_PATH + tvShow.backdrop).into(backdropImage)
            itemView.setOnClickListener {
                listener.onTvShowClick(tvShow.id)
            }
        }
    }

}