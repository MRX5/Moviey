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

        fun getImageWidth(context: Context):Int{
            return if(context.resources.configuration.orientation== Configuration.ORIENTATION_LANDSCAPE){
                convertPxToDp(context,context.resources.getDimension(R.dimen.movie_card_image_width_landscape))
            }else{
                convertPxToDp(context,context.resources.getDimension(R.dimen.movie_card_image_width_portrait))
            }
        }

        fun getImageHeight(context: Context):Int{
            return if(context.resources.configuration.orientation== Configuration.ORIENTATION_LANDSCAPE){
                convertPxToDp(context,context.resources.getDimension(R.dimen.movie_card_image_height_landscape))
            }else{
                convertPxToDp(context,context.resources.getDimension(R.dimen.movie_card_image_height_portrait))
            }
        }

        fun getTitle(str:String)= str.split("(")[0]
        fun getYear(str:String): String {
            return try {
                str.split("(")[1].substring(0,4)
            }catch (e:Exception){
                ""
            }
        }

    }
}