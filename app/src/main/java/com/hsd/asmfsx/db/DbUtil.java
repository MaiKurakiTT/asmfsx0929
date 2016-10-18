package com.hsd.asmfsx.db;

import com.dao.DbBeanDao;

/**
 * Created by apple on 2016/10/17.
 * 获取表 Helper 的工具类
 */

public class DbUtil {
    private static DbBeanHelper mDbBeanHelper;


    private static DbBeanDao getDriverDao() {
        return DbCore.getDaoSession().getDbBeanDao();
    }

    public static DbBeanHelper getDriverHelper() {
        if (mDbBeanHelper == null) {
            mDbBeanHelper = new DbBeanHelper(getDriverDao());
        }
        return mDbBeanHelper;
    }

}
