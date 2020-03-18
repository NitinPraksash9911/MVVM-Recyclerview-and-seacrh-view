package com.example.sampleappmovielist.interfaces

import android.widget.ImageView
import android.widget.TextView
import com.example.sampleappmovielist.model.Datum

interface MovieItemClickListner {

    fun movieItemClickLister(movieItem: Datum,imageView: ImageView,textView: TextView)
}