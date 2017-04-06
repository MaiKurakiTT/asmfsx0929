package com.hsd.asmfsx.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by sun on 2016/12/5.
 */

public class UserInformationBean2 implements Serializable {
    private Long id;

    private String phone;
    /**
     * @Fields icon : 头像
     */
    private String icon;
    /**
     * @Fields nickname : 昵称
     */
    private String nickname;
    private Integer sex;
    private Long birthday;
    private String star;//
    private Integer height;
    private String sign;//
    private String education;//
    private String department;//
    private String year;//
    private String locality;
    private String graduate;//
    private String idol;//
    private String book;//
    private String adore;//
    private Integer school;
    private Integer state;
    private Date registerDate;
    private boolean isStudentVerify;

    public boolean isStudentVerify() {
        return isStudentVerify;
    }

    public void setStudentVerify(boolean studentVerify) {
        isStudentVerify = studentVerify;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Long getBirthday() {
        return birthday;
    }

    public void setBirthday(Long birthday) {
        this.birthday = birthday;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getGraduate() {
        return graduate;
    }

    public void setGraduate(String graduate) {
        this.graduate = graduate;
    }

    public String getIdol() {
        return idol;
    }

    public void setIdol(String idol) {
        this.idol = idol;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public String getAdore() {
        return adore;
    }

    public void setAdore(String adore) {
        this.adore = adore;
    }

    public Integer getSchool() {
        return school;
    }

    public void setSchool(Integer school) {
        this.school = school;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }
}
