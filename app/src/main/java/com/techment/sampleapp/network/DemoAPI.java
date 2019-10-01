package com.techment.sampleapp.network;


import com.techment.sampleapp.model.ResponseData;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DemoAPI {
    @GET("search_by_date?tags=story")
    Observable<ResponseData> getPageData(@Query("page") int page_number);
}
