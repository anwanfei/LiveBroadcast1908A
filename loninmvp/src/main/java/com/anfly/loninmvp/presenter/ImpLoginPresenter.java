package com.anfly.loninmvp.presenter;

import android.text.TextUtils;

import com.anfly.loninmvp.bean.LoginBean;
import com.anfly.loninmvp.callback.LoignCallback;
import com.anfly.loninmvp.model.ImpLoginModel;
import com.anfly.loninmvp.view.LoginView;

public class ImpLoginPresenter implements LoginPresenter, LoignCallback {

    private LoginView loginView;
    private ImpLoginModel loginModel;

    public ImpLoginPresenter(LoginView loginView) {
        this.loginView = loginView;
        loginModel = new ImpLoginModel();
    }

    @Override
    public void login(String userName, String pwd) {
        if (TextUtils.isEmpty(pwd)) {
            loginView.onFail("请输入密码");
            return;
        }

        if (TextUtils.isEmpty(userName)) {
            loginView.onFail("请输入账号");
            return;
        }
        loginModel.login(this, userName, pwd);
    }

    @Override
    public void onSuccess(LoginBean loginBean) {
        loginView.onSuccess(loginBean);
    }

    @Override
    public void onFail(String error) {
        loginView.onFail(error);
    }
}
