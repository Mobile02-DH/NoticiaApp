package com.example.connect.connectnews.helper;

import com.example.connect.connectnews.model.News;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NewService {

    @GET("{articles}")
    Call<News> getNewsByCategory(
            @Path("articles") String articles,
            @Query("country") String country,
            @Query("apiKey") String apiKey,
            @Query("category") String category
    );
}
