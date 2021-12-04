package com.example.movies.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.R
import com.example.movies.adapter.MediaClickListener
import com.example.movies.databinding.SearchItemLayoutBinding
import com.example.movies.model.entity.Media
import com.example.movies.utils.Constants
import com.example.movies.utils.MediaUtils


class SearchAdapter(private val listener: MediaClickListener) :
    RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    var mediaList = mutableListOf<Media>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<SearchItemLayoutBinding>(
                inflater,
                R.layout.search_item_layout,
                parent,
                false
            )
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(mediaList[position])
    }

    override fun getItemCount() = mediaList.size

    fun setData(mediaList: List<Media>) {
        this.mediaList.addAll(MediaUtils.filterMediaType(mediaList))
        notifyDataSetChanged()
    }

    fun clearData() {
        mediaList.clear()
        notifyDataSetChanged()
    }

    inner class SearchViewHolder(private val binding: SearchItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(media: Media) = with(binding) {
            if (media.media_type == "movie") {
                media.release_date = MediaUtils.extractYearFromDate(media.release_date)
            } else { // tv
                media.release_date = MediaUtils.extractYearFromDate(media.first_air_date)
                media.title = media.name
            }

            binding.media = media
            binding.genres = MediaUtils.covertGenresIdToName(media.genre_ids)
            binding.rateBar.rating = MediaUtils.getActualRate(media.vote)

            binding.root.setOnClickListener {
                if(media.media_type==Constants.MOVIE){
                    listener.onItemClick(Constants.MOVIE,media.id)
                }else{
                    listener.onItemClick(Constants.TV,media.id)
                }
            }
        }
    }

}
