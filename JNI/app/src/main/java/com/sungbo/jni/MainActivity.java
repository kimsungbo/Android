package com.sungbo.jni;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    // C언어로 작성된 모듈 호출
    static {
        System.loadLibrary("jniCalculator");
    }

    //  C언어로 작성된 함수와 동일한 형태
    public native int getSum(int num1, int num2);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.text_result);

        int num1 = 10;
        int num2 = 20;

        int sum = getSum(num1, num2);
        textView.setText("JNI SUM = " +  sum);

    }
}