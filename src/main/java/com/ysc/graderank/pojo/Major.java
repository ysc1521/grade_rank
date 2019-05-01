package com.ysc.graderank.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.List;

public class Major {
    @Id
    private Integer id;

    private String name;

    private Integer grade;

    @Column(name = "GPA_years")
    private Integer gpaYears;

    private Integer tid;

    private Teacher teacher;

    private List<Student> studentList;

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
     * @return grade
     */
    public Integer getGrade() {
        return grade;
    }

    /**
     * @param grade
     */
    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    /**
     * @return GPA_years
     */
    public Integer getGpaYears() {
        return gpaYears;
    }

    /**
     * @param gpaYears
     */
    public void setGpaYears(Integer gpaYears) {
        this.gpaYears = gpaYears;
    }

    /**
     * @return tno
     */
    public Integer getTid() {
        return tid;
    }

    /**
     * @param tid
     */
    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public String getFullName() {
        return grade + "级" + name;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }
}