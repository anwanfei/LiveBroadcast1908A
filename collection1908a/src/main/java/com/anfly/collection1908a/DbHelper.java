package com.anfly.collection1908a;

import com.anfly.collection1908a.dao.ConllectionDbBeanDao;
import com.anfly.collection1908a.dao.DaoMaster;
import com.anfly.collection1908a.dao.DaoSession;

import java.util.List;

public class DbHelper {
    private static volatile DbHelper instancec;
    private final ConllectionDbBeanDao conllectionDbBeanDao;

    public static DbHelper getInstancec() {
        if (instancec == null) {
            synchronized (DbHelper.class) {
                if (instancec == null) {
                    instancec = new DbHelper();
                }
            }
        }
        return instancec;
    }

    public DbHelper() {
        //创建数据库
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(CollectionApplication.getApp(), "collection.db");

        //获取读写权限
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());

        //获取管理器
        DaoSession daoSession = daoMaster.newSession();

        //获取表对象
        conllectionDbBeanDao = daoSession.getConllectionDbBeanDao();
    }

    /**
     * 判断表中是否应有了该对象
     *
     * @param conllectionDbBean
     * @return
     */
    private boolean isHased(ConllectionDbBean conllectionDbBean) {
        List<ConllectionDbBean> list = conllectionDbBeanDao.queryBuilder()
                .where(ConllectionDbBeanDao.Properties.Title.eq(conllectionDbBean.getTitle())).list();
        if (list.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 插入数据
     *
     * @param conllectionDbBean
     * @return
     */
    public long insert(ConllectionDbBean conllectionDbBean) {
        if (!isHased(conllectionDbBean)) {
            long l = conllectionDbBeanDao.insertOrReplace(conllectionDbBean);
            return l;
        }
        return -1;
    }

    /**
     * 删除数据
     *
     * @param conllectionDbBean
     * @return
     */
    public boolean delete(ConllectionDbBean conllectionDbBean) {
        if (isHased(conllectionDbBean)) {
            conllectionDbBeanDao.delete(conllectionDbBean);
            return true;
        }
        return false;
    }

    /**
     * 查询全部
     *
     * @return
     */
    public List<ConllectionDbBean> queryAll() {
        List<ConllectionDbBean> list = conllectionDbBeanDao.loadAll();
        return list;
    }

    /**
     * 修改
     *
     * @param conllectionDbBean
     * @return
     */
    public boolean updata(ConllectionDbBean conllectionDbBean) {
        if (isHased(conllectionDbBean)) {
            conllectionDbBeanDao.update(conllectionDbBean);
            return true;
        }
        return false;
    }
}
