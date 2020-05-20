package com.example.finalproject.utils;

import com.example.finalproject.bean.FoodDbBean;
import com.example.finalproject.common.FinalApplication;
import com.example.finalproject.dao.DaoMaster;
import com.example.finalproject.dao.DaoSession;
import com.example.finalproject.dao.FoodDbBeanDao;

import java.util.List;

public class DbHelper {
    private static volatile DbHelper dbHelper;
    private final FoodDbBeanDao foodDbBeanDao;

    public static DbHelper getDbHelper() {
        if (dbHelper == null) {
            synchronized (DbHelper.class) {
                if (dbHelper == null) {
                    dbHelper = new DbHelper();
                }
            }
        }
        return dbHelper;
    }

    public DbHelper() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(FinalApplication.getApp(), "final.db");
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        foodDbBeanDao = daoSession.getFoodDbBeanDao();
    }

    /**
     * 插入
     *
     * @param foodDbBean
     * @return
     */
    public long insert(FoodDbBean foodDbBean) {
        if (!isInserted(foodDbBean)) {
            long l = foodDbBeanDao.insertOrReplace(foodDbBean);
            if (l >= 0) {
                return l;
            } else {
                return -1;
            }
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

    /**
     * 模糊查询
     *
     * @param info
     * @return
     */
    public List<FoodDbBean> querLike(String info) {
        List<FoodDbBean> list = foodDbBeanDao.queryBuilder().where(FoodDbBeanDao.Properties.Title.like("%" + info + "%")).list();
        return list;
    }

    /**
     * 返回全部
     *
     * @return
     */
    public List<FoodDbBean> queryAll() {
        List<FoodDbBean> foodDbBeans = foodDbBeanDao.loadAll();
        return foodDbBeans;
    }

    /**
     * 判断是否插入
     *
     * @param foodDbBean
     * @return
     */
    private boolean isInserted(FoodDbBean foodDbBean) {
        List<FoodDbBean> list = foodDbBeanDao.queryBuilder().where(FoodDbBeanDao.Properties.Title.eq(foodDbBean.getTitle())).list();
        if (list.size() > 0) {
            return true;
        } else {
            return false;
        }
    }
}
