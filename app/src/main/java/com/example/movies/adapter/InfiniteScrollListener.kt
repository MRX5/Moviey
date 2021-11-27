package com.example.movies.adapter

import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class InfiniteScrollListener(private val onLoadMore: () -> Unit,private val isLinearLayout: Boolean) :
    RecyclerView.OnScrollListener() {

    private var previousTotal = 0
    private var isLoading = true

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount = recyclerView.childCount
        val totalItemCount = recyclerView.layoutManager!!.itemCount
        val firstVisibleItemPosition =
           if(isLinearLayout) (recyclerView.layoutManager as LinearLayoutManager?)!!.findFirstVisibleItemPosition()
            else (recyclerView.layoutManager as GridLayoutManager?)!!.findFirstVisibleItemPosition()

        if (isLoading) {
            if (totalItemCount > previousTotal) {
                previousTotal = totalItemCount
                isLoading = false
            }
        }

        if (!isLoading) {
            if (dy > 0) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount) {
                    onLoadMore()
                    isLoading = true
                }
            }
        }
    }
}