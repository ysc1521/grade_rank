package com.ysc.graderank.pojo;

import javax.persistence.*;

public class SC {
    @Id
    private Integer id;

    private Integer sid;

    private Integer cid;

    @Column(name = "select_year")
    private Integer selectYear;

    private Integer score;

    private Course course;

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
     * @return sid
     */
    public Integer getSid() {
        return sid;
    }

    /**
     * @param sid
     */
    public void setSid(Integer sid) {
        this.sid = sid;
    }

    /**
     * @return cid
     */
    public Integer getCid() {
        return cid;
    }

    /**
     * @param cid
     */
    public void setCid(Integer cid) {
        this.cid = cid;
    }

    /**
     * @return select_year
     */
    public Integer getSelectYear() {
        return selectYear;
    }

    /**
     * @param selectYear
     */
    public void setSelectYear(Integer selectYear) {
        this.selectYear = selectYear;
    }

    /**
     * @return score
     */
    public Integer getScore() {
        return score;
    }

    /**
     * @param score
     */
    public void setScore(Integer score) {
        this.score = score;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getSchoolYear() {
        return (selectYear - 1) + "-" + selectYear + "学年";
    }
}