package com.hsd.asmfsx.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by apple on 2016/10/17.
 */
@Entity
public class DbBean {
    @Id
    private Long dbId;
    private Long userID;
    private String phone;
    private String studentID;
    private String icon;
    private String nickname;
    private int sex;
    private Date birthday;
    private String star;//
    private Integer height;
    private String sign;//
    private String education;//
    private String department;//
    private String locality;
    private int school;
    private int state;
    private Date user_registerDate;
    public Date getUser_registerDate() {
        return this.user_registerDate;
    }
    public void setUser_registerDate(Date user_registerDate) {
        this.user_registerDate = user_registerDate;
    }
    public int getState() {
        return this.state;
    }
    public void setState(int state) {
        this.state = state;
    }
    public int getSchool() {
        return this.school;
    }
    public void setSchool(int school) {
        this.school = school;
    }
    public String getLocality() {
        return this.locality;
    }
    public void setLocality(String locality) {
        this.locality = locality;
    }
    public String getDepartment() {
        return this.department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }
    public String getEducation() {
        return this.education;
    }
    public void setEducation(String education) {
        this.education = education;
    }
    public String getSign() {
        return this.sign;
    }
    public void setSign(String sign) {
        this.sign = sign;
    }
    public Integer getHeight() {
        return this.height;
    }
    public void setHeight(Integer height) {
        this.height = height;
    }
    public String getStar() {
        return this.star;
    }
    public void setStar(String star) {
        this.star = star;
    }
    public Date getBirthday() {
        return this.birthday;
    }
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
    public int getSex() {
        return this.sex;
    }
    public void setSex(int sex) {
        this.sex = sex;
    }
    public String getNickname() {
        return this.nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getIcon() {
        return this.icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }
    public String getStudentID() {
        return this.studentID;
    }
    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }
    public String getPhone() {
        return this.phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public Long getUserID() {
        return this.userID;
    }
    public void setUserID(Long userID) {
        this.userID = userID;
    }
    public Long getDbId() {
        return this.dbId;
    }
    public void setDbId(Long dbId) {
        this.dbId = dbId;
    }
    @Generated(hash = 412093992)
    public DbBean(Long dbId, Long userID, String phone, String studentID,
            String icon, String nickname, int sex, Date birthday, String star,
            Integer height, String sign, String education, String department,
            String locality, int school, int state, Date user_registerDate) {
        this.dbId = dbId;
        this.userID = userID;
        this.phone = phone;
        this.studentID = studentID;
        this.icon = icon;
        this.nickname = nickname;
        this.sex = sex;
        this.birthday = birthday;
        this.star = star;
        this.height = height;
        this.sign = sign;
        this.education = education;
        this.department = department;
        this.locality = locality;
        this.school = school;
        this.state = state;
        this.user_registerDate = user_registerDate;
    }
    @Generated(hash = 1953169116)
    public DbBean() {
    }

}
