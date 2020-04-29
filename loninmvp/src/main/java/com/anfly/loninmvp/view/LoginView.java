package com.anfly.loninmvp.view;

import com.anfly.loninmvp.bean.LoginBean;

public interface LoginView {
    void onSuccess(LoginBean loginBean);

    void onFail(String error);
}
