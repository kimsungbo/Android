package com.sungbo.findtheway.util;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DirectionUtil {
    private DirectionApi mGetApi;

    public DirectionUtil(){
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(DirectionApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mGetApi = mRetrofit.create(DirectionApi.class);
    }

    public DirectionApi getApi(){
        return mGetApi;
    }
}
