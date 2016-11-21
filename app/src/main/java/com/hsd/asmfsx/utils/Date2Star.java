package com.hsd.asmfsx.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by apple on 2016/11/21.
 */

public class Date2Star {
    public static final String[] constellationArr = { "水瓶座", "双鱼座", "牡羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座",
            "天蝎座", "射手座", "魔羯座" };

    public static final int[] constellationEdgeDay = { 20, 19, 21, 21, 21, 22, 23, 23, 23, 23, 22, 22 };


    /**
     * 根据日期获取星座
     * @param time
     * @return
     */
    public static String date2Constellation(Date birthday) {
        Calendar time = Calendar.getInstance();
        time.setTime(birthday);
        int month = time.get(Calendar.MONTH);
        int day = time.get(Calendar.DAY_OF_MONTH);
        if (day < constellationEdgeDay[month]) {
            month = month - 1;
        }
        if (month >= 0) {
            return constellationArr[month];
        }
        return constellationArr[11];
    }
}
