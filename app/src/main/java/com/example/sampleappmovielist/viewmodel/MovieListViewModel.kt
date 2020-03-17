package com.example.sampleappmovielist.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sampleappmovielist.model.MovieDataModel
import com.example.sampleappmovielist.repository.MovieListRepository
import io.reactivex.disposables.CompositeDisposable

class MovieListViewModel : ViewModel() {

    val movieLiveData = MutableLiveData<MovieDataModel>()

    private val compositeDisposable = CompositeDisposable()

    fun getMovieListData() {
        compositeDisposable.add(MovieListRepository.getMovieListData())
        MovieListRepository.movieLiveData.observeForever {
            movieLiveData.value = it
        }
    }


    override fun onCleared() {
        super.onCleared()
        if (!compositeDisposable.isDisposed)
            compositeDisposable.dispose()
    }

}