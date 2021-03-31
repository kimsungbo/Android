package com.sungbo.mvp.Presenter;

import com.sungbo.mvp.Contract;

public class Presenter implements Contract.Presenter, Contract.Model.OnFinishedListener {

    private Contract.View mainView;
    private Contract.Model model;

    public Presenter(Contract.View mainView, Contract.Model model){
        this.mainView = mainView;
        this.model = model;
    }

    @Override
    public void onButtonClick() {
        if (mainView != null) {
            mainView.showProgress();
        }
        // 3. View가 요청한 이벤트를 처리하기 위해 필요한 데이터를 Model에게 요청
        model.getNextCourse(this);
    }

    @Override
    public void onDestroy() {
        mainView = null;

    }


    // 6. View가 요청한 이벤트를 처리
    // 7. View에게 이벤트 처리 결과 전달
    @Override
    public void onFinished(String string) {
        if (mainView != null) {
            // 8. View가 Presenter로부터 전달받은 결과를 가지고 UI 갱신
            mainView.setString(string);
            mainView.hideProgress();
        }
    }
}
