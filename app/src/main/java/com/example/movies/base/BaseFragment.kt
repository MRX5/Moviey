package com.example.movies.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseFragment<T:ViewDataBinding>(@LayoutRes private val resourceId:Int) : Fragment() {
    private var _binding: T? = null
    val binding : T get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(layoutInflater, resourceId, container, false)
        return binding.root
}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        fetchData()
    }
    abstract fun setupRecyclerView()
    abstract fun fetchData()

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}
