package com.sungbo.letsreadnews.util;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsUtil {
    private NewsApi mGetApi;

    public NewsUtil(){
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(NewsApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mGetApi = mRetrofit.create(NewsApi.class);
    }

    public NewsApi getApi(){
        return mGetApi;
    }


}
