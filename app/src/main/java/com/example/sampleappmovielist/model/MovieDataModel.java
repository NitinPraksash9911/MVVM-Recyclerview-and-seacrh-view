
package com.example.sampleappmovielist.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MovieDataModel {

    @SerializedName("data")
    @Expose
     List<Datum> data = null;

    public List<Datum> getData() {
        return data;
    }

    String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

}
