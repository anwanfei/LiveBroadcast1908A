package com.example.finalproject.callback;

import com.example.finalproject.bean.ComPoseBean;

public interface RxCallBack {
    void onSuccess(ComPoseBean comPoseBean);

    void onFail(String error);
}
