package com.example.sampleappmovielist.model

import com.example.sampleappmovielist.network.NetworkResponse
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MovieDataModel {
    @SerializedName("data")
    @Expose
    var data: List<Datum>? = null

    var response: NetworkResponse? = null

}