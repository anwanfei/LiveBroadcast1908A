package com.anfly.collectionmvp.view;

import com.anfly.collectionmvp.bean.ConllectionDbBean;

import java.util.List;

public interface CollectionVieiw {
    void onQuerySuccess(List<ConllectionDbBean> list);

    void onQueryFail(String error);

    void onDeleteSuccess(String msg);

    void onDeleteFail(String error);
}
