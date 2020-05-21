package com.example.finalproject.view;

import com.example.finalproject.bean.SmartBean;

public interface SmartView {
    void onSuccess(SmartBean smartBean);

    void onFail(String error);
}
