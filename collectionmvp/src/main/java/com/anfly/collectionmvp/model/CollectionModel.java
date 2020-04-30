package com.anfly.collectionmvp.model;

import com.anfly.collectionmvp.bean.ConllectionDbBean;
import com.anfly.collectionmvp.callback.CollectionCallBack;

public interface CollectionModel {
    void query(CollectionCallBack collectionCallBack);

    void delete(ConllectionDbBean conllectionDbBean, CollectionCallBack collectionCallBack);
}
