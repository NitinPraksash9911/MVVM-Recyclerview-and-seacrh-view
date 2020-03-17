package com.example.sampleappmovielist.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.sampleappmovielist.model.MovieDataModel
import com.example.sampleappmovielist.network.NetworkModule
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MovieListRepository {

    val movieLiveData = MutableLiveData<MovieDataModel>()

    fun getMovieListData(): Disposable {
        return NetworkModule.getPostService()
            .getMovieList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ moviedata ->
                movieLiveData.value = moviedata

            },
                { error ->
                    val model = MovieDataModel()
                    model.error = error.message
                    movieLiveData.value = model
                })

    }

}