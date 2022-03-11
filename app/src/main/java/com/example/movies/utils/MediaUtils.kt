package com.example.movies.utils

import com.example.movies.data.model.entity.*
import java.lang.StringBuilder

class MediaUtils {

    companion object {

        fun convert(movies: List<Movie>): List<Movie> {
            movies.forEach {
                it.release_date = extractYearFromDate(it.release_date)
            }
            return movies
        }

        fun convertTvShows(tvShows: List<TV_Show>): List<TV_Show> {
            tvShows.forEach {
                it.release_date = extractYearFromDate(it.release_date)
            }
            return tvShows
        }

        fun convertMoviesAndTvShows(mediaList: List<Media>): List<Media> {
            mediaList.forEach {
                if (it.media_type == Constants.MOVIE) {
                    it.release_date = extractYearFromDate(it.release_date)
                } else {
                    it.first_air_date = extractYearFromDate(it.first_air_date)
                }
            }
            return mediaList
        }
        fun getMediaReleaseDate(media:Media): String {
            return if(media.media_type==Constants.MOVIE) extractYearFromDate(media.release_date)
            else extractYearFromDate(media.first_air_date)
        }

        fun getMediaTitle(media:Media): String {
            return if(media.media_type==Constants.MOVIE) media.title
            else media.name
        }

        fun extractYearFromDate(date: String?): String {
            date?.let {
                return if (it.isNotEmpty()) it.substring(0, 4) else "No date"
            } ?: return "No date"
        }

        fun formatMovieLength(minutes: String?): String {
            return if (minutes != null) {
                val hours = minutes.toInt() / 60
                val min = minutes.toInt() % 60
                if (hours == 0) "${min}m"
                else "${hours}h ${min}m"
            } else {
                "0h 0m"
            }
        }

        private fun addPrefixPath(url: String?) = Constants.TMDB_IMAGE_PATH + url

        fun addBackdropPrefixPath(url: String?) = Constants.TMDB_BACKDROP_PATH + url

        fun extractGenresNames(genres: List<Genres>): String {
            val result = StringBuilder("")
            var cnt=2
            for(idx in genres.indices){
                if(genres[idx].name.length>=12){
                    if(cnt==0)break;
                    else cnt--
                }
                result.append(genres[idx].name+", ")
                if (cnt==0)break
                cnt--
            }

            return if (result.isNotEmpty() && result.last().isWhitespace()) result.dropLast(2)
                .toString()
            else result.toString()
        }

        fun extractCastsPictures(casts: List<Cast>?): List<Cast> {
            casts?.forEach {
                it.profilePicture = addPrefixPath(it.profilePicture)
            } ?: return emptyList()
            return casts
        }

        fun extractYoutubeLink(videos: Video?): String {
            videos?.let {
                it.results.forEach { current ->
                    if (current.type == "Trailer") {
                        return Constants.YOUTUBE_PATH + current.key
                    }
                }
            }
            return ""
        }

        fun covertGenresIdToName(genres: List<Int>?): String {
            var result = ""
            var cnt = 2
            if (genres != null) {
                for (idx in genres.indices) {
                    when (genres[idx]) {
                        28 -> result += ", " + "Action"
                        12 -> result += ", " + "Adventure"
                        16 -> result += ", " + "Animation"
                        35 -> result += ", " + "Comedy"
                        80 -> result += ", " + "Crime"
                        99 -> result += ", " + "Documentary"
                        18 -> result += ", " + "Drama"
                        10751 -> result += ", " + "Family"
                        14 -> result += ", " + "Fantasy"
                        36 -> result += ", " + "History"
                        27 -> result += ", " + "Horror"
                        10402 -> result += ", " + "Music"
                        9648 -> result += ", " + "Mystery"
                        10749 -> result += ", " + "Romance"
                        878 -> result += ", " + "Science Fiction"
                        10770 -> result += ", " + "TV Movie"
                        53 -> result += ", " + "Thriller"
                        10752 -> result += ", " + "War"
                        37 -> result += ", " + "Western"
                        10759 -> result += ", " + "Action, Adventure"
                        10762 -> result += ", " + "Kids"
                        10763 -> result += ", " + "News"
                        10764 -> result += ", " + "Reality"
                        10765 -> result += ", " + "Sci-Fi, Fantasy"
                        10766 -> result += ", " + "Soap"
                        10767 -> result += ", " + "Talk"
                        10768 -> result += ", " + "War, Politics"
                    }
                    if (cnt != 0 && result.length<20) cnt--
                    else break
                }
                if (result.isNotEmpty()) result = result.drop(2) // remove ", " from result
                else result = "No genres"
            }
            return result
        }

        fun getActualRate(vote: Double?): Float {
            return vote?.let {
                (vote / 2).toFloat()
            } ?: 0.0f
        }

        fun filterMediaType(mediaList: List<Media>): List<Media> {
            return mediaList.filter {
                it.media_type != "person"
            }
        }

        fun convertAndFilterSeasonList(seasons: List<Season>): List<Season> {
            val filteredList = seasons.filter {
                it.season_number != 0 && it.episode_count != "0"
            }
            filteredList.map {
                it.episode_count = it.episode_count + " Episodes"
                it.air_date = extractYearFromDate(it.air_date)
            }
            return filteredList
        }
    }


}