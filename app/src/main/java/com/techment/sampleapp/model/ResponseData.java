package com.techment.sampleapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseData {
    @SerializedName("hits")
    @Expose
    private List<ResponseItems> hits;

    public List<ResponseItems> getHits() {
        return hits;
    }
}
