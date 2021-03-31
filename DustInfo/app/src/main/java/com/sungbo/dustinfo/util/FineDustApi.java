package com.sungbo.dustinfo.util;

import com.sungbo.dustinfo.model.dust_material.FineDust;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface FineDustApi {
    String BASE_URL = "https://api.airvisual.com/";

    @GET("v2/nearest_city?")
    Call<FineDust> getFineDust(@Query("lat") double latitude,
                               @Query("lon") double longitude,
                               @Query("key") String key);

}
