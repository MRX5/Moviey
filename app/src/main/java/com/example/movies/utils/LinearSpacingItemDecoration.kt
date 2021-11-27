package com.example.movies.utils

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class LinearSpacingItemDecoration(private val context: Context,private val orientation:Int)
    : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        if(orientation==LinearLayoutManager.HORIZONTAL)
        outRect.left=Utils.convertPxToDp(context,8f)
        else{
            val itemPosition=parent.getChildLayoutPosition(view)
            if(itemPosition==0){
                outRect.left=Utils.convertPxToDp(context,16f)
            }else{
                outRect.left=Utils.convertPxToDp(context,8f)
            }
            outRect.left=Utils.convertPxToDp(context,8f)
            outRect.right=Utils.convertPxToDp(context,8f)
            outRect.bottom=Utils.convertPxToDp(context,8f)
        }
    }
}