package com.sungbo.mvc.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;


// 데이터와 operation을 정의하기 위한 Model
// Model클래스는 View의 존재를 모름
public class Model extends Observable {


    private List<Integer> mList;

    public Model(){
        mList = new ArrayList<Integer>(3);

        mList.add(0);
        mList.add(0);
        mList.add(0);
    }

    public int getValueAtIndex(final int index) throws IndexOutOfBoundsException{
        return mList.get(index);
    }

    public void setValueAtIndex(final int index) throws IndexOutOfBoundsException{
        // 3. Model이 데이터를 갱신함
        mList.set(index, mList.get(index) + 1);
        setChanged();
        // 4. View에게 자신이 업데이트 되었다는 사실을 알림
        notifyObservers();
    }

}
