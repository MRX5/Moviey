package com.example.movies.utils

import android.content.Context

class Utils {
    companion object{
        fun convertPxToDp(context: Context, px: Float):Int{
            val x=context.resources.displayMetrics.density
            return (x*px).toInt()
        }
    }
}