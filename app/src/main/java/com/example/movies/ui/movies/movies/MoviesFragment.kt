package com.example.movies.ui.movies.movies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movies.R
import com.example.movies.databinding.FragmentMoviesBinding

class MoviesFragment : Fragment() {
    lateinit var binding:FragmentMoviesBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

}