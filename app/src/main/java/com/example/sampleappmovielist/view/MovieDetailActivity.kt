package com.example.sampleappmovielist.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.sampleappmovielist.R
import com.example.sampleappmovielist.databinding.ActivityMovieDetailBinding
import com.example.sampleappmovielist.model.Datum

class MovieDetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityMovieDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail)

        val movieData = intent.getParcelableExtra<Datum>("movie")

        binding.data = movieData

    }

    private fun setDataInView() {

    }
}
