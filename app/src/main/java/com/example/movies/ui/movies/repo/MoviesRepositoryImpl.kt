package com.example.movies.ui.movies.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.example.movies.model.Movie
import com.example.movies.utils.Resource
import com.example.movies.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import javax.inject.Inject
import kotlin.Exception

class MoviesRepositoryImpl : MoviesRepository {

    override suspend fun getMovies(page: Int): LiveData<Resource<List<Movie>>> = liveData {
        emit(Resource.loading(null))
        val movies = mutableListOf<Movie>()
        val url = "https://tena.egybest.run/movies/?page=$page"
        try {
            val document = withContext(Dispatchers.IO) {
                Jsoup.connect(url).get()
            }
            val elements = document.select("a.movie")
            elements.forEach {
                val link = it.attr("href")
                val imgUrl = it.getElementsByTag("img").attr("src")
                val titleAndYear = it.getElementsByClass("title").text()  // Joker (2021)
                val title = Utils.getTitle(titleAndYear)
                val year = Utils.getYear(titleAndYear)
                movies.add(Movie(link, imgUrl, title, year))
            }
            if (movies.size != 0) {
                emit(Resource.success(movies))
            } else {
                Resource.error("No movies found", null)
            }
        } catch (e: Exception) {
            emit(Resource.error(e.toString(), null))
        }
    }


}