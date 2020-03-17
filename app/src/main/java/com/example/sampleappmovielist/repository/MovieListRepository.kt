package com.example.sampleappmovielist.repository

import androidx.lifecycle.MutableLiveData
import com.example.sampleappmovielist.MyApplication
import com.example.sampleappmovielist.model.MovieDataModel
import com.example.sampleappmovielist.network.NetworkModule
import com.example.sampleappmovielist.network.NetworkResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

object MovieListRepository {

    val movieLiveData = MutableLiveData<MovieDataModel>()

    fun getMovieListData(): Disposable {
        return NetworkModule.getPostService()
            .getMovieList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({moviedata ->

                if(!MyApplication.hasNetwork()){
                    moviedata.response = NetworkResponse.FROM_CACHE
                }
                else{
                    moviedata.response = NetworkResponse.SUCCESS
                }
                movieLiveData.value = moviedata

            },
                { _ ->
                    val model = MovieDataModel()
                    if(!MyApplication.hasNetwork()){
                        model.response = NetworkResponse.NO_INTERNET
                    }
                    else{
                        model.response = NetworkResponse.OTHERS
                    }
                    movieLiveData.value = model
                })

    }

}