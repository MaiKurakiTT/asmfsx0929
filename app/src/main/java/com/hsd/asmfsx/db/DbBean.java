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
    private Integer user_ID;
    private String user_phone;
    private String user_uuid;
    private String student_ID;

    private String user_icon;
    private String user_nickname;
    private String user_sex;
    private Date user_birthday;
    private String user_star;//
    private Integer user_height;
    private String user_sign;//
    private String user_education;//
    private String user_department;//
    private String user_locality;
    private String user_school;
    private String user_state;
    private String user_registerDate;
    public String getUser_registerDate() {
        return this.user_registerDate;
    }
    public void setUser_registerDate(String user_registerDate) {
        this.user_registerDate = user_registerDate;
    }
    public String getUser_state() {
        return this.user_state;
    }
    public void setUser_state(String user_state) {
        this.user_state = user_state;
    }
    public String getUser_school() {
        return this.user_school;
    }
    public void setUser_school(String user_school) {
        this.user_school = user_school;
    }
    public String getUser_locality() {
        return this.user_locality;
    }
    public void setUser_locality(String user_locality) {
        this.user_locality = user_locality;
    }
    public String getUser_department() {
        return this.user_department;
    }
    public void setUser_department(String user_department) {
        this.user_department = user_department;
    }
    public String getUser_education() {
        return this.user_education;
    }
    public void setUser_education(String user_education) {
        this.user_education = user_education;
    }
    public String getUser_sign() {
        return this.user_sign;
    }
    public void setUser_sign(String user_sign) {
        this.user_sign = user_sign;
    }
    public Integer getUser_height() {
        return this.user_height;
    }
    public void setUser_height(Integer user_height) {
        this.user_height = user_height;
    }
    public String getUser_star() {
        return this.user_star;
    }
    public void setUser_star(String user_star) {
        this.user_star = user_star;
    }
    public Date getUser_birthday() {
        return this.user_birthday;
    }
    public void setUser_birthday(Date user_birthday) {
        this.user_birthday = user_birthday;
    }
    public String getUser_sex() {
        return this.user_sex;
    }
    public void setUser_sex(String user_sex) {
        this.user_sex = user_sex;
    }
    public String getUser_nickname() {
        return this.user_nickname;
    }
    public void setUser_nickname(String user_nickname) {
        this.user_nickname = user_nickname;
    }
    public String getUser_icon() {
        return this.user_icon;
    }
    public void setUser_icon(String user_icon) {
        this.user_icon = user_icon;
    }
    public String getStudent_ID() {
        return this.student_ID;
    }
    public void setStudent_ID(String student_ID) {
        this.student_ID = student_ID;
    }
    public String getUser_uuid() {
        return this.user_uuid;
    }
    public void setUser_uuid(String user_uuid) {
        this.user_uuid = user_uuid;
    }
    public String getUser_phone() {
        return this.user_phone;
    }
    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }
    public Integer getUser_ID() {
        return this.user_ID;
    }
    public void setUser_ID(Integer user_ID) {
        this.user_ID = user_ID;
    }
    public Long getDbId() {
        return this.dbId;
    }
    public void setDbId(Long dbId) {
        this.dbId = dbId;
    }
    @Generated(hash = 779294752)
    public DbBean(Long dbId, Integer user_ID, String user_phone, String user_uuid,
            String student_ID, String user_icon, String user_nickname,
            String user_sex, Date user_birthday, String user_star,
            Integer user_height, String user_sign, String user_education,
            String user_department, String user_locality, String user_school,
            String user_state, String user_registerDate) {
        this.dbId = dbId;
        this.user_ID = user_ID;
        this.user_phone = user_phone;
        this.user_uuid = user_uuid;
        this.student_ID = student_ID;
        this.user_icon = user_icon;
        this.user_nickname = user_nickname;
        this.user_sex = user_sex;
        this.user_birthday = user_birthday;
        this.user_star = user_star;
        this.user_height = user_height;
        this.user_sign = user_sign;
        this.user_education = user_education;
        this.user_department = user_department;
        this.user_locality = user_locality;
        this.user_school = user_school;
        this.user_state = user_state;
        this.user_registerDate = user_registerDate;
    }
    @Generated(hash = 1953169116)
    public DbBean() {
    }

}
