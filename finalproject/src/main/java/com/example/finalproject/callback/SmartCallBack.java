package com.example.finalproject.callback;

import com.example.finalproject.bean.SmartBean;

public interface SmartCallBack {
    void onSuccess(SmartBean smartBean);

    void onFail(String error);
}
