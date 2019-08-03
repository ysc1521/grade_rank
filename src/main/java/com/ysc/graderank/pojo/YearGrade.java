package com.ysc.graderank.pojo;

import com.ysc.graderank.util.ComClass;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

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

    @Column(name = "major_student")
    private Integer majorStudent;

    private Student student;

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

    public Integer getMajorStudent() {
        return majorStudent;
    }

    public void setMajorStudent(Integer majorStudent) {
        this.majorStudent = majorStudent;
    }

    public String getSchoolYear() {
        return (year - 1) + "-" + year + "学年";
    }

    public String getBasicScoreStr() {
        return basicScore == null ? ComClass.NULL_SCORE : Double.toString(basicScore);
    }

    public String getDevScoreStr() {
        return devScore == null ? ComClass.NULL_SCORE : Double.toString(devScore);
    }

    public String getComScoreStr() {
        return comScore == null ? ComClass.NULL_SCORE : Double.toString(comScore);
    }

    private String getFormatStr(Integer rank, Integer sum) {
        if (sum == null || rank == null) {
            return "未评定";
        }
        double rate = rank * 1.0 / sum * 100;
        return String.format("%.4f%%(%d/%d)", rate, aveRank, majorStudent);
    }

    public String getAveScoreMajorRate() {
        return getFormatStr(aveRank, majorStudent);
    }

    public String getComScoreMajorRate() {
        return getFormatStr(comRank, majorStudent);
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}