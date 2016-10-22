package com.hsd.asmfsx.bean;

/**
 * Created by 紫荆 on 2016/10/22.
 */

public class FindFriendsBean extends BaseBean{
    private String ageMax; // 年龄最大值
    private String ageMin; // 年龄最小值
    private Integer heightMax; // 身高最大值
    private Integer heightMin; // 身高最小值
    private String schoolName; // 学校名称
    private String location; // 所在地
    private String sex; // 性别

    private Integer findFriend_pageNow;// 页码
    private Integer findFriend_pageSize;// 每页大小

    public String getAgeMax() {
        return ageMax;
    }

    public void setAgeMax(String ageMax) {
        this.ageMax = ageMax;
    }

    public String getAgeMin() {
        return ageMin;
    }

    public void setAgeMin(String ageMin) {
        this.ageMin = ageMin;
    }

    public Integer getHeightMax() {
        return heightMax;
    }

    public void setHeightMax(Integer heightMax) {
        this.heightMax = heightMax;
    }

    public Integer getHeightMin() {
        return heightMin;
    }

    public void setHeightMin(Integer heightMin) {
        this.heightMin = heightMin;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getFindFriend_pageNow() {
        return findFriend_pageNow;
    }

    public void setFindFriend_pageNow(Integer findFriend_pageNow) {
        this.findFriend_pageNow = findFriend_pageNow;
    }

    public Integer getFindFriend_pageSize() {
        return findFriend_pageSize;
    }

    public void setFindFriend_pageSize(Integer findFriend_pageSize) {
        this.findFriend_pageSize = findFriend_pageSize;
    }
}
