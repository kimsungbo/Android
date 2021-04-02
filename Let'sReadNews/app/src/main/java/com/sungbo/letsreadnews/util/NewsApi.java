package com.sungbo.letsreadnews.util;

import com.sungbo.letsreadnews.model.news.News;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApi {
    String BASE_URL = "https://newsapi.org/";

    @GET("v2/top-headlines")
    Call<News> getNews(@Query("country") String country,
                       @Query("apiKey") String key);

    @GET("v2/everything")
    Call<News> getNewsWithKeyword(@Query("q") String keyword,
                                  @Query("sortBy") String sort,
                                  @Query("apiKey") String key);
}

