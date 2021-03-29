package com.sungbo.dustinfo.data;

import com.sungbo.dustinfo.model.dust_material.FineDust;

import retrofit2.Callback;

public interface FineDustRepository {
    boolean isAvailable();
    void getFineDustData(Callback<FineDust> callback);
}
