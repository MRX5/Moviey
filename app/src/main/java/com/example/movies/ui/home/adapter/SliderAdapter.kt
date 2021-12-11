package com.example.movies.ui.home.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.example.movies.R
import com.example.movies.databinding.MovieCardBinding
import com.example.movies.databinding.SliderItemLayoutBinding
import com.example.movies.model.entity.Media
import com.example.movies.model.entity.Movie
import com.example.movies.utils.Constants
import com.example.movies.utils.MediaUtils
import com.smarteist.autoimageslider.SliderViewAdapter
import com.squareup.picasso.Picasso
import kotlin.math.log

class SliderAdapter : SliderViewAdapter<SliderAdapter.SliderViewHolder>() {

    private var data= mutableListOf<Movie>()

    override fun getCount()=data.size

    fun setData(data:List<Movie>) {
        data.subList(0,9)?.let {
            this.data= MediaUtils.convert(data.subList(0,9)) as MutableList<Movie>
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup): SliderViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        val binding=
            DataBindingUtil.inflate<SliderItemLayoutBinding>(inflater,R.layout.slider_item_layout,parent,false)
        return SliderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SliderViewHolder?, position: Int) {
        holder?.bind(data[position])
    }

    inner class SliderViewHolder(private val binding:SliderItemLayoutBinding): SliderViewAdapter.ViewHolder(binding.root){
        fun bind(movie:Movie)=with(binding){
            binding.rateBar.rating=MediaUtils.getActualRate(movie.vote)
            this.movie=movie
        }
    }

}