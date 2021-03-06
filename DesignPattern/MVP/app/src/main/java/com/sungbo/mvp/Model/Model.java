package com.sungbo.mvp.Model;

import android.os.Handler;

import com.sungbo.mvp.Contract;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Model implements Contract.Model {

    private List<String> arrayList = Arrays.asList(
            "DSA Self Paced: Master the basics of Data Structures and Algorithms to solve complex problems efficiently. ",
            "Placement 100: This course will guide you for placement with theory,lecture videos, weekly assignments " +
                    "contests and doubt assistance.",
            "Amazon SDE Test Series: Test your skill & give the final touch to your preparation before applying for " +
                    "product based against like Amazon, Microsoft, etc.",
            "Complete Interview Preparation: Cover all the important concepts and topics required for the interviews. " +
                    "Get placement ready before the interviews begin",
            "Low Level Design for SDE 1 Interview: Learn Object-oriented Analysis and Design to prepare for " +
                    "SDE 1 Interviews in top companies"
    );

    // 5. 가져온 데이터를 Presenter에게 전달
    @Override
    public void getNextCourse(final OnFinishedListener onFinishedListener) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 4. 데이터를 가져옴
                onFinishedListener.onFinished(getRandomString());
            }
        }, 1200);
    }

    private String getRandomString() {
        Random random = new Random();
        int index = random.nextInt(arrayList.size());
        return arrayList.get(index);
    }
}
