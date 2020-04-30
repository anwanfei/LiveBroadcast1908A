package com.anfly.collectionmvp.presenter;

import com.anfly.collectionmvp.bean.ConllectionDbBean;
import com.anfly.collectionmvp.callback.CollectionCallBack;
import com.anfly.collectionmvp.model.ImpCollectionModel;
import com.anfly.collectionmvp.view.CollectionVieiw;

import java.util.List;

public class ImpCollectionPresenter implements CollectionPresenter, CollectionCallBack {
    private CollectionVieiw collectionVieiw;
    private ImpCollectionModel collectionModel;

    public ImpCollectionPresenter(CollectionVieiw collectionVieiw) {
        this.collectionVieiw = collectionVieiw;
        collectionModel = new ImpCollectionModel();
    }

    @Override
    public void query() {
        collectionModel.query(this);
    }

    @Override
    public void delete(ConllectionDbBean conllectionDbBean) {
        collectionModel.delete(conllectionDbBean, this);
    }

    @Override
    public void onQuerySuccess(List<ConllectionDbBean> list) {
        collectionVieiw.onQuerySuccess(list);
    }

    @Override
    public void onQueryFail(String error) {
        collectionVieiw.onQueryFail(error);
    }

    @Override
    public void onDeleteSuccess(String msg) {
        collectionVieiw.onDeleteSuccess(msg);
    }

    @Override
    public void onDeleteFail(String error) {
        collectionVieiw.onDeleteFail(error);
    }
}
