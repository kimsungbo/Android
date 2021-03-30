package com.sungbo.dustinfo.finedust;

import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sungbo.dustinfo.R;
import com.sungbo.dustinfo.data.FineDustRepository;
import com.sungbo.dustinfo.data.LocationFineDustRepository;
import com.sungbo.dustinfo.model.dust_material.FineDust;

public class FineDustFragment extends Fragment implements FineDustContract.View {

    public TextView mLocationTextView;
    public TextView mTimeTextView;
    public TextView mDustTextView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private FineDustPresenter mPresenter;
    private FineDustRepository mRepository;
    private ImageView mDustImageView;
    private LinearLayout mDustLayout;


    public static FineDustFragment newInstance(double lat, double lng){
        Bundle args = new Bundle();
        args.putDouble("lat", lat);
        args.putDouble("lng", lng);
        FineDustFragment fragment = new FineDustFragment();
        fragment. setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null){
            double lat = getArguments().getDouble("lat");
            double lng = getArguments().getDouble("lng");
            mRepository = new LocationFineDustRepository(lat, lng);
        }
        else{
            mRepository = new LocationFineDustRepository();
        }
        mPresenter = new FineDustPresenter(mRepository, this);
        mPresenter.loadFineDustData();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fine_dust, container, false);

        mLocationTextView = view.findViewById(R.id.result_location_text);
        mTimeTextView = view.findViewById(R.id.result_time_text);
        mDustTextView = view.findViewById(R.id.result_dust_text);
        mDustImageView = view.findViewById(R.id.result_dust_image);
        mDustLayout = view.findViewById(R.id.dust_linear_layout);

        if (savedInstanceState != null){
            // 복원
            mLocationTextView.setText(savedInstanceState.getString("location"));
            mTimeTextView.setText(savedInstanceState.getString("time"));
            mDustTextView.setText(savedInstanceState.getString("dust"));
        }

        mSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeColors(Color.RED, Color.YELLOW, Color.BLUE);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadFineDustData();

            }
        });
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("location", mLocationTextView.getText().toString());
        outState.putString("time", mTimeTextView.getText().toString());
        outState.putString("dust", mDustTextView.getText().toString());
    }

    @Override
    public void showFineDustResult(FineDust finedust) {

        if (finedust.getData() != null){
            // 결과 보여주기
            String location = finedust.getData().getCountry() + ", " + finedust.getData().getState() + ", " + finedust.getData().getCity();
            mLocationTextView.setText(location);

            mTimeTextView.setText(finedust.getData().getCurrent().getPollution().getTs());
            Integer dust_aqi = finedust.getData().getCurrent().getPollution().getAqius();
            mDustTextView.setText(dust_aqi.toString());

            if (dust_aqi >= 0 && dust_aqi <= 50){
                mDustImageView.setImageResource(R.drawable.face1);
                mDustLayout.setBackgroundColor(Color.YELLOW);

            }
            else if (dust_aqi >= 51 && dust_aqi <= 100){
                mDustImageView.setImageResource(R.drawable.face2);
                mDustLayout.setBackgroundResource(R.color.face2);


            }
            else if (dust_aqi >= 101 && dust_aqi <= 150){
                mDustImageView.setImageResource(R.drawable.face3);
                mDustLayout.setBackgroundResource(R.color.face3);

            }
            else if (dust_aqi >= 151 && dust_aqi <= 200){
                mDustImageView.setImageResource(R.drawable.face4);
                mDustLayout.setBackgroundResource(R.color.face4);

            }
            else if (dust_aqi >= 201 && dust_aqi <= 300){
                mDustImageView.setImageResource(R.drawable.face5);
                mDustLayout.setBackgroundResource(R.color.face5);

            }
            else{
                mDustImageView.setImageResource(R.drawable.face6);
                mDustLayout.setBackgroundResource(R.color.face6);

            }



        }



    }

    @Override
    public void showLoadError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadingStart() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void loadingEnd() {
        mSwipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public void reload(double lat, double lng) {
        mRepository = new LocationFineDustRepository(lat, lng);
        mPresenter = new FineDustPresenter(mRepository, this);
        mPresenter.loadFineDustData();
    }
}

