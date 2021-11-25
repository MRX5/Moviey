package com.example.movies.utils

import android.content.Context
import android.content.res.Configuration
import com.example.movies.R

class Utils {
    companion object{
        private fun convertPxToDp(context: Context, px: Float):Int{
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
    }
}