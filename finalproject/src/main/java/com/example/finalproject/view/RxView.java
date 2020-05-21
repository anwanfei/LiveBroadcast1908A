package com.example.finalproject.view;

import com.example.finalproject.bean.ComPoseBean;

public interface RxView {
    void onSuccess(ComPoseBean comPoseBean  );

    void onFail(String error);
}
