package com.anfly.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.anfly.greendao.dao.DaoMaster;
import com.anfly.greendao.dao.StudentBeanDao;

import org.greenrobot.greendao.database.Database;

public class GreenDaoHelper extends DaoMaster.OpenHelper {

    public GreenDaoHelper(Context context, String name) {
        super(context, name);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        MigrationHelper.migrate(db, new MigrationHelper.ReCreateAllTableListener() {
            @Override
            public void onCreateAllTables(Database db, boolean ifNotExists) {
                DaoMaster.createAllTables(db, ifNotExists);
            }

            @Override
            public void onDropAllTables(Database db, boolean ifExists) {
                DaoMaster.dropAllTables(db, ifExists);
            }
        }, StudentBeanDao.class);
    }
}
