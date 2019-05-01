package com.ysc.graderank.pojo;

import javax.persistence.Id;
import java.util.List;

public class Course {
    @Id
    private Integer id;

    private String name;

    private Integer credit;

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

    /**
     * @return credit
     */
    public Integer getCredit() {
        return credit;
    }

    /**
     * @param credit
     */
    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public String getFullName() {
        return id + "-" + name;
    }

    public List<SC> getScList() {
        return scList;
    }

    public void setScList(List<SC> scList) {
        this.scList = scList;
    }
}