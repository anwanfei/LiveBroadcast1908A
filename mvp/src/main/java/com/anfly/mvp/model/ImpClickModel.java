package com.anfly.mvp.model;

import com.anfly.mvp.callback.ClickCallBack;

public class ImpClickModel implements ClickModel {
    @Override
    public void click(ClickCallBack clickCallBack) {
        String content = "积云教育";
        String errorContent = "数据错误";

        if (content.isEmpty()) {
            clickCallBack.onFail(errorContent);
        } else {
            clickCallBack.onSuccess(content);
        }
    }
}
