package com.anfly.collectionmvp.callback;

import com.anfly.collectionmvp.bean.ConllectionDbBean;

import java.util.List;

public interface CollectionCallBack {
    void onQuerySuccess(List<ConllectionDbBean> list);

    void onQueryFail(String error);
}
