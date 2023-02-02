package com.example.movies.utils

import android.content.Context
import android.content.res.Configuration
import com.example.movies.R

class Utils {
    companion object{
         fun convertPxToDp(context: Context, px: Float):Int{
            val x=context.resources.displayMetrics.density
            return (x*px).toInt()
        }

        fun Double.roundDoubleToOneNumber():Double{
            return try{
                String.format("%.1f", this).toDouble()
            }catch (e:java.lang.Exception){
                0.0
            }
        }
    }
}