package com.ysc.graderank.pojo;

import javax.persistence.*;

@Table(name = "year_grade")
public class YearGrade {
    @Id
    private Integer id;

    private Integer sid;

    private Integer year;

    @Column(name = "basic_score")
    private Double basicScore;

    @Column(name = "dev_score")
    private Double devScore;

    @Column(name = "ave_score")
    private Double aveScore;

    @Column(name = "com_score")
    private Double comScore;

    @Column(name = "basic_detail")
    private String basicDetail;

    @Column(name = "ave_rank")
    private Integer aveRank;

    @Column(name = "com_rank")
    private Integer comRank;

    private Double credit;

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
     * @return year
     */
    public Integer getYear() {
        return year;
    }

    /**
     * @param year
     */
    public void setYear(Integer year) {
        this.year = year;
    }

    /**
     * @return basic_score
     */
    public Double getBasicScore() {
        return basicScore;
    }

    /**
     * @param basicScore
     */
    public void setBasicScore(Double basicScore) {
        this.basicScore = basicScore;
    }

    /**
     * @return dev_score
     */
    public Double getDevScore() {
        return devScore;
    }

    /**
     * @param devScore
     */
    public void setDevScore(Double devScore) {
        this.devScore = devScore;
    }

    /**
     * @return ave_score
     */
    public Double getAveScore() {
        return aveScore;
    }

    /**
     * @param aveScore
     */
    public void setAveScore(Double aveScore) {
        this.aveScore = aveScore;
    }

    /**
     * @return com_score
     */
    public Double getComScore() {
        return comScore;
    }

    /**
     * @param comScore
     */
    public void setComScore(Double comScore) {
        this.comScore = comScore;
    }

    /**
     * @return basic_detail
     */
    public String getBasicDetail() {
        return basicDetail;
    }

    /**
     * @param basicDetail
     */
    public void setBasicDetail(String basicDetail) {
        this.basicDetail = basicDetail;
    }

    public Integer getAveRank() {
        return aveRank;
    }

    public void setAveRank(Integer aveRank) {
        this.aveRank = aveRank;
    }

    public Integer getComRank() {
        return comRank;
    }

    public void setComRank(Integer comRank) {
        this.comRank = comRank;
    }

    public Double getCredit() {
        return credit;
    }

    public void setCredit(Double credit) {
        this.credit = credit;
    }

    public String getSchoolYear() {
        return (year - 1) + "-" + year + "学年";
    }
}