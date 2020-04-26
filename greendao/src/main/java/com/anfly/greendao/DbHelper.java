package com.anfly.greendao;

import com.anfly.greendao.dao.DaoMaster;
import com.anfly.greendao.dao.DaoSession;
import com.anfly.greendao.dao.StudentBeanDao;

import java.util.List;

public class DbHelper {
    private final StudentBeanDao studentBeanDao;
    private static volatile DbHelper instance;

    public static DbHelper getInstance() {
        if (instance == null) {
            synchronized (DbHelper.class) {
                if (instance == null) {
                    instance = new DbHelper();
                }
            }
        }
        return instance;
    }

    private DbHelper() {
        //创建数据库
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(GreenDaoApplication.getApp(), "student.db");

        //获取读写对象
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());

        //获取管理器
        DaoSession daoSession = daoMaster.newSession();

        //获取表对象
        studentBeanDao = daoSession.getStudentBeanDao();
    }

    /**
     * 插入
     *
     * @param studentBean
     */
    public void insert(StudentBean studentBean) {
        studentBeanDao.insertOrReplace(studentBean);
    }

    /**
     * 删除数据
     *
     * @param studentBean
     */
    public void delete(StudentBean studentBean) {
        studentBeanDao.delete(studentBean);
    }

    /**
     * 查询全部
     *
     * @return
     */
    public List<StudentBean> queryAll() {
        List<StudentBean> studentBeans = studentBeanDao.loadAll();
        List<StudentBean> list = studentBeanDao.queryBuilder().list();
        return list;
    }

    /**
     * 修改
     *
     * @param studentBean
     */
    public void updata(StudentBean studentBean) {
        studentBeanDao.update(studentBean);
    }
}
