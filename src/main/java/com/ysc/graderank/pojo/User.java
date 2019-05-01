package com.ysc.graderank.pojo;

import com.ysc.graderank.enums.IdentityEnum;

import javax.persistence.*;

public class User {
    @Id
    private Integer id;

    private String password;

    private IdentityEnum identity;

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
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return identity
     */
    public IdentityEnum getIdentity() {
        return identity;
    }

    /**
     * @param identity
     */
    public void setIdentity(IdentityEnum identity) {
        this.identity = identity;
    }
}