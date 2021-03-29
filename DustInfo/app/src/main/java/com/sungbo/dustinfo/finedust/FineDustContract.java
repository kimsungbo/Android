package com.sungbo.dustinfo.finedust;

import com.sungbo.dustinfo.model.dust_material.FineDust;

public class FineDustContract {
    interface View{
        void showFineDustResult(FineDust finedust);
        void showLoadError(String message);
        void loadingStart();
        void loadingEnd();
        void reload(double lat, double lng);
    }

    interface UserActionsListener{
        void loadFineDustData();

    }
}
