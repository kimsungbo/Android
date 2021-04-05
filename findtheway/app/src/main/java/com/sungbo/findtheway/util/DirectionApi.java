package com.sungbo.findtheway.util;

import com.sungbo.findtheway.data.Direction;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DirectionApi {

    String BASE_URL = "https://maps.googleapis.com/";

    @GET("maps/api/directions/json?")
    Call<Direction> getDirection(@Query("origin") String origin,
                                @Query("destination") String destination,
                                @Query("mode") String mode,
                                @Query("key") String key);


}
