package com.ysc.graderank.pojo;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "dev_detail")
public class DevDetail {
    @Id
    private Integer id;

    private Integer yid;

    private String name;

    private Double score;

    private String kind;

    private String verify;

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
     * @return yid
     */
    public Integer getYid() {
        return yid;
    }

    /**
     * @param yid
     */
    public void setYid(Integer yid) {
        this.yid = yid;
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
     * @return score
     */
    public Double getScore() {
        return score;
    }

    /**
     * @param score
     */
    public void setScore(Double score) {
        this.score = score;
    }

    /**
     * @return kind
     */
    public String getKind() {
        return kind;
    }

    /**
     * @param kind
     */
    public void setKind(String kind) {
        this.kind = kind;
    }

    /**
     * @return verify
     */
    public String getVerify() {
        return verify;
    }

    /**
     * @param verify
     */
    public void setVerify(String verify) {
        this.verify = verify;
    }
}