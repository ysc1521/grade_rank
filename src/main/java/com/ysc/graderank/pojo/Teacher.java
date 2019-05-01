package com.ysc.graderank.pojo;

import javax.persistence.Id;
import java.util.List;

public class Teacher {
    @Id
    private Integer id;

    private String name;

    private String password;

    private List<Major> majorList;

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

    public List<Major> getMajorList() {
        return majorList;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMajorList(List<Major> majorList) {
        this.majorList = majorList;
    }

    public String getMajorListStr() {
        if (majorList.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (Major major : majorList) {
            sb.append(major.getFullName()).append(',');
        }
        String tempStr = sb.toString();
        return tempStr.substring(0, sb.length() - 1);
    }
}