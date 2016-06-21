package com.example.xyzreader.api;

import com.example.xyzreader.model.ListResponsData;

import java.util.List;

import retrofit.http.GET;

/**
 * Created by AdonisArifi on 12.4.2016 - 2016 . xyzreader
 */
public interface XyzReaderApi {

    @GET("/u/231329/xyzreader_data/data.json")
    void getData(retrofit.Callback<List<ListResponsData>> callback);
}
