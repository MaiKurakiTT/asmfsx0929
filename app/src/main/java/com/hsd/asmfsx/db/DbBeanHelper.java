package com.hsd.asmfsx.db;

import org.greenrobot.greendao.AbstractDao;

/**
 * Created by apple on 2016/10/17.
 * 具体表的实现类
 */

public class DbBeanHelper extends BaseDbHelper<DbBean, Long> {


    public DbBeanHelper(AbstractDao dao) {
        super(dao);
    }
}
