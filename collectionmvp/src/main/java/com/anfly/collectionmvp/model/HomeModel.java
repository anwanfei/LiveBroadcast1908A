package com.anfly.collectionmvp.model;

import com.anfly.collectionmvp.bean.ConllectionDbBean;
import com.anfly.collectionmvp.callback.HomeCallBack;

public interface HomeModel {
    void homeData(HomeCallBack homeCallBack);

    void insert(ConllectionDbBean conllectionDbBean, HomeCallBack homeCallBack);
}
