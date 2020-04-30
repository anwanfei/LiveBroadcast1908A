package com.anfly.collectionmvp.presenter;

import com.anfly.collectionmvp.bean.ConllectionDbBean;

public interface CollectionPresenter {
    void query();

    void delete(ConllectionDbBean conllectionDbBean);
}
