package com.sungbo.dustinfo.util;

import com.sungbo.dustinfo.model.dust_material.FineDust;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface FineDustApi {
    String BASE_URL = "https://api.airvisual.com/";
    //http://api.airvisual.com/v2/nearest_city?lat=33.6060463&lon=131.1870162&key=0835c535-13fc-4b02-b0c1-9b33f766e71c

//    @Headers("key: 0835c535-13fc-4b02-b0c1-9b33f766e71c")
    @GET("v2/nearest_city?")
    Call<FineDust> getFineDust(@Query("lat") double latitude,
                               @Query("lon") double longitude,
                               @Query("key") String key);

}
