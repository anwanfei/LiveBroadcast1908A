package com.anfly.collectionmvp.model;

import com.anfly.collectionmvp.bean.ConllectionDbBean;
import com.anfly.collectionmvp.callback.CollectionCallBack;
import com.anfly.collectionmvp.utils.DbHelper;

import java.util.List;

public class ImpCollectionModel implements CollectionModel {
    @Override
    public void query(CollectionCallBack collectionCallBack) {
        List<ConllectionDbBean> list = DbHelper.getInstancec().queryAll();
        if (list != null && list.size() > 0) {
            collectionCallBack.onQuerySuccess(list);
        } else {
            collectionCallBack.onQueryFail("查询失败或数据为空");
        }
    }
}
