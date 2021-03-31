package com.sungbo.mvp;


// View-Presenter와 Presenter-Model간의 소통을 위한 인터페이스
// View, Model, Presenter에서 쓰일 모든 abstract methods가 정의되어 있음

public interface Contract {
    interface View {
        void showProgress();
        void hideProgress();
        void setString(String string);
    }

    interface Model {
        interface OnFinishedListener {
            void onFinished(String string);
        }
        void getNextCourse(Contract.Model.OnFinishedListener onFinishedListener);
    }

    interface Presenter {
        void onButtonClick();
        void onDestroy();
    }
}
