package com.anfly.loninmvp.callback;

import com.anfly.loninmvp.bean.LoginBean;

public interface LoignCallback {
    void onSuccess(LoginBean loginBean);

    void onFail(String error);
}
