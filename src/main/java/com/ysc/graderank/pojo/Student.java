package com.ysc.graderank.pojo;

import com.ysc.graderank.util.ComClass;

import javax.persistence.Column;
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

    private Double gpa;

    @Column(name = "gpa_rank")
    private Integer gpaRank;

    @Column(name = "gpa_major_student")
    private Integer gpaMajorStudent;

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

    public Double getGpa() {
        return gpa;
    }

    public void setGpa(Double gpa) {
        this.gpa = gpa;
    }

    public Integer getGpaRank() {
        return gpaRank;
    }

    public void setGpaRank(Integer gpaRank) {
        this.gpaRank = gpaRank;
    }

    public Integer getGpaMajorStudent() {
        return gpaMajorStudent;
    }

    public void setGpaMajorStudent(Integer gpaMajorStudent) {
        this.gpaMajorStudent = gpaMajorStudent;
    }

    public String getGpaRankRate() {
        if (gpaRank == null || gpaMajorStudent == null) {
            return ComClass.NULL_SCORE;
        }
        double rate = gpaRank * 1.0 / gpaMajorStudent * 100;
        return String.format("%.4f%%(%d/%d)", rate, gpaRank, gpaMajorStudent);
    }
    
    public String getGpaStr() {
        return gpa == null ? "未评定" : Double.toString(gpa); 
    }
}