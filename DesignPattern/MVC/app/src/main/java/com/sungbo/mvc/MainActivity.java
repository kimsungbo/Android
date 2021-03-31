package com.sungbo.mvc;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sungbo.mvc.Model.Model;

import java.util.Observable;
import java.util.Observer;


// View와 Model사이의 관계를 정의
// View는 Model에서 데이터를 가져와서 그에 맞는 변경사항이 만들어짐
public class MainActivity extends AppCompatActivity implements Observer, View.OnClickListener{

    private Model myModel;

    private Button Button1;
    private Button Button2;
    private Button Button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // observable Model 과 observable Activity간의 관계 형성
        myModel = new Model();
        myModel.addObserver(this);

        Button1 = findViewById(R.id.button);
        Button2 = findViewById(R.id.button2);
        Button3 = findViewById(R.id.button3);

        // 1. 사용자 이벤트 감지
        Button1.setOnClickListener(this);
        Button2.setOnClickListener(this);
        Button3.setOnClickListener(this);

    }


    // 2. 사용자 이벤트에 따른 데이터 업데이트 유무 확인 (Model에게 데이터 업데이트 요청)
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button:
                myModel.setValueAtIndex(0);
                break;
            case R.id.button2:
                myModel.setValueAtIndex(1);
                break;
            case R.id.button3:
                myModel.setValueAtIndex(2);
                break;
        }
    }

    // 5. View는 Model의 데이터가 업데이트 되었는지 확인
    @Override
    public void update(Observable o, Object arg) {
        // 6. Model에서 업데이트 된 데이터를 가져오고, 이를 바탕으로 UI를 갱신
        Button1.setText("Count: "+myModel.getValueAtIndex(0));
        Button2.setText("Count: "+myModel.getValueAtIndex(1));
        Button3.setText("Count: "+myModel.getValueAtIndex(2));
    }

}
