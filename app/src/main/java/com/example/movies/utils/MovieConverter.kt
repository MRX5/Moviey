package com.example.movies.utils

import android.util.Log
import com.example.movies.model.Cast
import com.example.movies.model.Genres
import com.example.movies.model.Movie
import java.lang.StringBuilder

class MovieConverter {

    companion object {

        fun convert(movies: List<Movie>): List<Movie> {
            movies.forEach {
                it.poster = Constants.TMDB_IMAGE_PATH + it.poster
                it.release_date = it.release_date.substring(0, 4)
            }
            return movies
        }

        fun getMovieYear(date: String?): String {
            return date?.substring(0, 4) ?:"No date"
        }

        fun formatMovieLength(minutes: String?): String {
            return if(minutes!=null){
                val hours = minutes.toInt() / 60
                val min = minutes.toInt() % 60
                if (hours == 0) " • ${min}m"
                else " • ${hours}h ${min}m"
            }else{
                "0h 0m"
            }
        }

        fun addPrefixPath(url: String?)=Constants.TMDB_IMAGE_PATH+url

        fun extractGenresNames(genres:List<Genres>): String {
            val result=StringBuilder("")
            for(idx in 0..(genres.size - 1).coerceAtMost(2)){
                result.append(genres[idx].name+", ")
            }
            return if(result.last().isWhitespace()) result.dropLast(2).toString()
            else result.toString()
        }

        fun extractCastsPictures(casts: List<Cast>): List<Cast> {
            casts.forEach {
                it.profilePicture= addPrefixPath(it.profilePicture)
            }
            return casts
        }

    }
}