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
        //DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(GreenDaoApplication.getApp(), "student.db");
        GreenDaoHelper devOpenHelper = new GreenDaoHelper(GreenDaoApplication.getApp(), "student.db");

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
     * 查询固定名字
     *
     * @param name
     * @return
     */
    public List<StudentBean> queryByName(String name) {
        return studentBeanDao.queryBuilder().where(StudentBeanDao.Properties.Name.eq(name)).list();
    }

    /**
     * 查询固定名字和年龄的
     *
     * @param student
     * @return
     */
    public List<StudentBean> queryStudent(StudentBean student) {
        return studentBeanDao.queryBuilder().where(StudentBeanDao.Properties.Name.eq(student.getName()), StudentBeanDao.Properties.Age.gt(student.getAge())).list();
    }

    /**
     * 查询第几页的多少条数据
     *
     * @param page
     * @param count
     * @return
     */
    public List<StudentBean> queryPage(int page, int count) {
        return studentBeanDao.queryBuilder().offset(page * count).limit(count).list();
    }

    /**
     * @param studentDao
     * @return
     */
    public StudentBean query(StudentBean studentDao) {
        StudentBean student = studentBeanDao.queryBuilder().where(StudentBeanDao.Properties.Id.eq(studentDao.getId())).build().unique();
        return student;
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
