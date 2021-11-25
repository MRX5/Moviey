package com.example.movies.ui.details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.R
import com.example.movies.databinding.CastLayoutBinding
import com.example.movies.model.Cast

class CastsAdapter : RecyclerView.Adapter<CastsAdapter.CastViewHolder>() {

    private var castsList= mutableListOf<Cast>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        val inflater= LayoutInflater.from(parent.context)
        val binding=
            DataBindingUtil.inflate<CastLayoutBinding>(inflater, R.layout.cast_layout,parent,false)
        return CastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        holder.bind(castsList[position])
    }

    fun setData(castsList:List<Cast>){
        this.castsList= castsList as MutableList<Cast>
        notifyDataSetChanged()
    }
    override fun getItemCount()=castsList.size

    inner class CastViewHolder(private val binding: CastLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(cast: Cast)= with(binding){
            this.cast=cast
        }
    }
}