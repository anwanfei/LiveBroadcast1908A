package com.anfly.exercise11.utils;

import com.anfly.exercise11.bean.FoodDbBean;
import com.anfly.exercise11.common.ExerciseApplication;
import com.anfly.exercise11.dao.DaoMaster;
import com.anfly.exercise11.dao.DaoSession;
import com.anfly.exercise11.dao.FoodDbBeanDao;

import java.util.List;

public class DbHelper {
    private static volatile DbHelper helper;
    private final FoodDbBeanDao foodDbBeanDao;

    public static DbHelper getHelper() {
        if (helper == null) {
            synchronized (DbHelper.class) {
                if (helper == null) {
                    helper = new DbHelper();
                }
            }
        }
        return helper;
    }

    public DbHelper() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(ExerciseApplication.getApp(), "e.db");
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        foodDbBeanDao = daoSession.getFoodDbBeanDao();
    }

    public long insert(FoodDbBean foodDbBean) {
        if (!isInserted(foodDbBean)) {
            long l = foodDbBeanDao.insertOrReplace(foodDbBean);
            return l;
        }
        return -1;
    }

    public boolean delete(FoodDbBean foodDbBean) {
        if (isInserted(foodDbBean)) {
            foodDbBeanDao.delete(foodDbBean);
            return true;
        }
        return false;
    }

    private boolean isInserted(FoodDbBean foodDbBean) {
        List<FoodDbBean> list = foodDbBeanDao.queryBuilder().where(FoodDbBeanDao.Properties.Name.eq(foodDbBean.getName())).list();
        if (list != null && list.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public List<FoodDbBean> queryAll() {
        List<FoodDbBean> list = foodDbBeanDao.loadAll();
        return list;
    }
}
