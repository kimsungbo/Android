package com.sungbo.dustinfo.data;

import com.sungbo.dustinfo.BuildConfig;
import com.sungbo.dustinfo.model.dust_material.FineDust;
import com.sungbo.dustinfo.util.FineDustUtil;

import retrofit2.Callback;

public class LocationFineDustRepository implements FineDustRepository{

    private FineDustUtil mFineDustUtil;
    private double mLatitude;
    private double mLongitude;
    private static String APPKEY = BuildConfig.AIRVISUAL_API_KEY;

    public LocationFineDustRepository(){
        mFineDustUtil = new FineDustUtil();
    }

    public LocationFineDustRepository(double lat, double lng){
        this();
        this.mLatitude = lat;
        this.mLongitude = lng;
    }

    @Override
    public boolean isAvailable() {
        if (mLatitude != 0 && mLongitude != 0){
            return true;
        }
        return false;
    }

    @Override
    public void getFineDustData(Callback<FineDust> callback) {
        mFineDustUtil.getApi().getFineDust(mLatitude, mLongitude, APPKEY)
                .enqueue(callback);
    }
}
