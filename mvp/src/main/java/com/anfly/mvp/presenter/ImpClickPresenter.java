package com.anfly.mvp.presenter;

import com.anfly.mvp.callback.ClickCallBack;
import com.anfly.mvp.model.ImpClickModel;
import com.anfly.mvp.view.ClickView;

public class ImpClickPresenter implements ClickPresener, ClickCallBack {

    private ImpClickModel impClickModel;
    private ClickView clickView;

    public ImpClickPresenter(ClickView clickView) {
        this.clickView = clickView;
        impClickModel = new ImpClickModel();
    }

    @Override
    public void click() {
        if (impClickModel != null) {
            impClickModel.click(this);
        }
    }

    @Override
    public void onSuccess(String msg) {
        clickView.onSuccess(msg);
    }

    @Override
    public void onFail(String error) {
        clickView.onFail(error);
    }
}
