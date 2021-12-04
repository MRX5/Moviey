package com.example.movies.ui.tv_details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.R
import com.example.movies.adapter.MediaClickListener
import com.example.movies.databinding.MovieCardBinding
import com.example.movies.databinding.TvShowCardBinding
import com.example.movies.model.entity.Movie
import com.example.movies.model.entity.TV_Show
import com.example.movies.utils.Constants
import com.example.movies.utils.MediaUtils

class RelatedTvShowAdapter(private val listener:MediaClickListener) :
    RecyclerView.Adapter<RelatedTvShowAdapter.RelatedTvShowViewHolder>() {

    private var recommendationsList= mutableListOf<TV_Show>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RelatedTvShowViewHolder {
        val inflater= LayoutInflater.from(parent.context)
        val binding= DataBindingUtil.inflate<TvShowCardBinding>(inflater, R.layout.tvshow_card,parent,false)
        return RelatedTvShowViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RelatedTvShowViewHolder, position: Int) {
        val tvShow=recommendationsList[position]
        tvShow.release_date=MediaUtils.extractYearFromDate(tvShow.release_date)
        holder.bind(tvShow)
    }

    override fun getItemCount()=recommendationsList.size

    fun setData(tvShows:List<TV_Show>){
        recommendationsList= tvShows as MutableList<TV_Show>
        notifyDataSetChanged()
    }

    inner class RelatedTvShowViewHolder(private val binding: TvShowCardBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(tvShow: TV_Show) =with(binding){
            this.tvShow=tvShow
            binding.root.setOnClickListener {
                listener.onItemClick(Constants.TV,tvShow.id)
            }
        }
    }
}