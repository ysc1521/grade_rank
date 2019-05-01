package com.ysc.graderank.pojo;

import javax.persistence.Id;
import java.util.List;

public class Student {
    @Id
    private Integer id;

    private String name;

    private Integer mid;

    private Major major;

    private String password;

    private List<SC> scList;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public List<SC> getScList() {
        return scList;
    }

    public void setScList(List<SC> scList) {
        this.scList = scList;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}