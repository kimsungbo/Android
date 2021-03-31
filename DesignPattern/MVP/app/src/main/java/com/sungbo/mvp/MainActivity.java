package com.sungbo.mvp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sungbo.mvp.Model.Model;
import com.sungbo.mvp.Presenter.Presenter;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity implements Contract.View {

    private TextView textView;
    private Button button;
    private ProgressBar progressBar;

    Contract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);
        progressBar = findViewById(R.id.progressBar);

        presenter = new Presenter(this, new Model());

        // 1. View가 사용자 이벤트 감지
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 2. View가 Presenter에게 이벤트 발생을 알리고 처리를 요청
                presenter.onButtonClick();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    // method to display the Course Detail TextView
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        textView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(GONE);
        textView.setVisibility(View.VISIBLE);
    }

    @Override
    public void setString(String string) {
        textView.setText(string);
    }
}
