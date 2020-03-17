package com.example.sampleappmovielist.repository

import com.example.sampleappmovielist.model.MovieDataModel
import io.reactivex.Observable
import io.reactivex.Observer
import retrofit2.http.GET
import java.util.*

interface MovieListWebServices {


    @GET("movies")
    fun getMovieList(): Observable<MovieDataModel>
}