package com.sungbo.dustinfo.finedust;

import com.sungbo.dustinfo.data.FineDustRepository;
import com.sungbo.dustinfo.model.dust_material.FineDust;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//view
public class FineDustPresenter implements FineDustContract.UserActionsListener {
    private final FineDustRepository mRepository;
    private final FineDustContract.View mView;

    public FineDustPresenter(FineDustRepository repository, FineDustContract.View view) {
        this.mRepository = repository;
        this.mView = view;
    }


    @Override
    public void loadFineDustData() {
        if (mRepository.isAvailable()){
            mView.loadingStart();
            mRepository.getFineDustData(new Callback<FineDust>() {
                @Override
                public void onResponse(Call<FineDust> call, Response<FineDust> response) {
                    mView.showFineDustResult(response.body());
                    mView.loadingEnd();
                }

                @Override
                public void onFailure(Call<FineDust> call, Throwable t) {
                    mView.showLoadError(t.getLocalizedMessage());
                    mView.loadingEnd();
                }
            });

        }
    }
}
