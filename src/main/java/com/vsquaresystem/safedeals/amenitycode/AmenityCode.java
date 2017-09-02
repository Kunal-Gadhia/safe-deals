/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.amenitycode;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author hp2
 */
public class AmenityCode {

    private Integer id;
    private String name;
    private AmenityDetailTab amenityDetailTab;
    private Integer userId;
    private Date lastUpdatedTimeStamp;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AmenityDetailTab getAmenityDetailTab() {
        return amenityDetailTab;
    }

    public void setAmenityDetailTab(AmenityDetailTab amenityDetailTab) {
        this.amenityDetailTab = amenityDetailTab;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getLastUpdatedTimeStamp() {
        return lastUpdatedTimeStamp;
    }

    public void setLastUpdatedTimeStamp(Date lastUpdatedTimeStamp) {
        this.lastUpdatedTimeStamp = lastUpdatedTimeStamp;
    }

}
