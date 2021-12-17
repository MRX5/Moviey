package com.example.movies.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.example.movies.R
import com.example.movies.listener.MediaClickListener
import com.example.movies.data.model.entity.Media
import com.example.movies.utils.Constants

import com.example.movies.utils.MediaUtils
import com.smarteist.autoimageslider.SliderViewAdapter
import com.squareup.picasso.Picasso


class SliderAdapter(private val listener:MediaClickListener) : SliderViewAdapter<SliderAdapter.SliderViewHolder>() {

    private var data= mutableListOf<Media>()

    override fun onCreateViewHolder(parent: ViewGroup): SliderViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        val view= inflater.inflate(R.layout.slider_item_layout,parent,false)
        return SliderViewHolder(view)
    }

    override fun onBindViewHolder(holder: SliderViewHolder?, position: Int) {
        holder?.bind(data[position])
    }

    override fun getCount()=data.size

    fun setData(data:List<Media>) {
        data.subList(0,9)?.let {
            this.data= MediaUtils.convertMoviesAndTvShows(data.subList(0,9)) as MutableList<Media>
            notifyDataSetChanged()
        }
    }

    inner class SliderViewHolder(itemView: View?) : SliderViewAdapter.ViewHolder(itemView) {
        private val backdropImage=itemView?.findViewById<ImageView>(R.id.backdrop_image)
        private val titleTextView=itemView?.findViewById<TextView>(R.id.title_text)
        private val rateTextView=itemView?.findViewById<TextView>(R.id.rate_text)
        private val releaseDateTextView=itemView?.findViewById<TextView>(R.id.release_date_text)
        private val rateBar=itemView?.findViewById<RatingBar>(R.id.rate_bar)

        fun bind(media:Media) {
            releaseDateTextView?.text=MediaUtils.getMediaReleaseDate(media)
            titleTextView?.text=MediaUtils.getMediaTitle(media)
            rateTextView?.text=media.vote.toString()
            rateBar?.rating=MediaUtils.getActualRate(media.vote)
            Picasso.get().load(Constants.TMDB_BACKDROP_PATH + media.backdrop).into(backdropImage)
            itemView.setOnClickListener {
                if(media.media_type==Constants.MOVIE){
                    listener.onItemClick(Constants.MOVIE,media.id)
                }else{
                    listener.onItemClick(Constants.TV,media.id)
                }
            }
        }
    }

}