package com.anfly.loninmvp.model;

import com.anfly.loninmvp.callback.LoignCallback;

public interface LoginModel {
    void login(LoignCallback loginCallback, String userName, String password);
}
