package com.example.movies.utils

import com.example.movies.model.Movie

class MovieConverter {

    companion object{

        fun convert(movies:List<Movie>): List<Movie> {
            movies.forEach {
                it.poster=Constants.TMDB_IMAGE_PATH+it.poster
                it.release_date= it.release_date.substring(0,4)
            }
            return movies
        }
    }
}