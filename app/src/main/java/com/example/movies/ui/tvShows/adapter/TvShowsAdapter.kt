package com.example.movies.ui.tvShows.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.R
import com.example.movies.databinding.TvShowCardBinding
import com.example.movies.listener.OnTvShowClickListener
import com.example.movies.data.model.entity.TV_Show
import com.example.movies.utils.MediaUtils

class TvShowsAdapter(val context: Context, val listener: OnTvShowClickListener) : RecyclerView.Adapter<TvShowsAdapter.TvShowsViewHolder>() {

    private var tvShowsList= mutableListOf<TV_Show>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowsViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        val binding=DataBindingUtil.inflate<TvShowCardBinding>(inflater,R.layout.tvshow_card,parent,false)
        return TvShowsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TvShowsViewHolder, position: Int) {
        holder.bind(tvShowsList[position])
    }

    override fun getItemCount()=tvShowsList.size

    fun setData(tvShowsList:List<TV_Show>){
        this.tvShowsList.addAll(MediaUtils.convertTvShows(tvShowsList))
        notifyDataSetChanged()
    }

   inner class TvShowsViewHolder(private val binding: TvShowCardBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(tvShows: TV_Show) =with(binding){
            this.tvShow=tvShows
            binding.root.setOnClickListener {
                listener.onTvShowClick(tvShows.id)
            }
        }
    }

}