package com.hsd.asmfsx.utils;

/**
 * Created by sun on 2017/3/3.
 */

public class SchoolUtils {
    public static String SchoolInt2String(int schoolCode){
        String school = null;
        switch (schoolCode){
            case 0:
                school = "河南师范大学";
            default:
                school = "未知";
        }
        return school;
    }
}
