package com.example.movies.ui.tv_details.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.movies.R
import com.example.movies.databinding.SeasonCardBinding
import com.example.movies.model.entity.Season
import com.example.movies.utils.MediaUtils

class SeasonsAdapter : RecyclerView.Adapter<SeasonsAdapter.SeasonViewHolder>() {

    private var seasons = mutableListOf<Season>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonsAdapter.SeasonViewHolder {
        val inflater= LayoutInflater.from(parent.context)
        val binding= DataBindingUtil.inflate<SeasonCardBinding>(inflater, R.layout.season_item_layout,parent,false)
        return SeasonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SeasonViewHolder, position: Int) = holder.bind(seasons[position])

    override fun getItemCount() = seasons.size

    fun setData(season: List<Season>) {
        this.seasons = MediaUtils.convertAndFilterSeasonList(season) as MutableList<Season>
        notifyDataSetChanged()
    }

    inner class SeasonViewHolder(private val binding:SeasonCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(season: Season) = with(binding) {
           this.season=season
        }
    }

}