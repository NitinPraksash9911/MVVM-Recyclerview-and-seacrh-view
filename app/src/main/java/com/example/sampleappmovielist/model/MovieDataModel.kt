package com.example.sampleappmovielist.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MovieDataModel {
    @SerializedName("data")
    @Expose
    var data: List<Datum>? = null

    var error: String? = null

}