package com.example.sampleappmovielist.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Datum(
    @SerializedName("id") @Expose var id: Int? = null,
    @SerializedName("title") @Expose var title: String? = null,
    @SerializedName("year") @Expose var year: String? = null,
    @SerializedName("genre") @Expose var genre: String? = null,
    @SerializedName("poster") @Expose var poster: String? = null
) : Parcelable {

}