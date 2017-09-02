/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.builder;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Pratik
 */
public class Builder {

    private Integer id;
    private String name;
    private Integer cityId;
    private String description;
    private String address;
    private String phoneNumber;
    private String projectName;
    private String propertyName;
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

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 11 * hash + Objects.hashCode(this.id);
        hash = 11 * hash + Objects.hashCode(this.name);
        hash = 11 * hash + Objects.hashCode(this.cityId);
        hash = 11 * hash + Objects.hashCode(this.description);
        hash = 11 * hash + Objects.hashCode(this.address);
        hash = 11 * hash + Objects.hashCode(this.phoneNumber);
        hash = 11 * hash + Objects.hashCode(this.projectName);
        hash = 11 * hash + Objects.hashCode(this.propertyName);
        hash = 11 * hash + Objects.hashCode(this.userId);
        hash = 11 * hash + Objects.hashCode(this.lastUpdatedTimeStamp);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Builder other = (Builder) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.cityId, other.cityId)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        if (!Objects.equals(this.phoneNumber, other.phoneNumber)) {
            return false;
        }
        if (!Objects.equals(this.projectName, other.projectName)) {
            return false;
        }
        if (!Objects.equals(this.propertyName, other.propertyName)) {
            return false;
        }
        if (!Objects.equals(this.userId, other.userId)) {
            return false;
        }
        if (!Objects.equals(this.lastUpdatedTimeStamp, other.lastUpdatedTimeStamp)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Builder{" + "id=" + id + ", name=" + name + ", cityId=" + cityId + ", description=" + description + ", address=" + address + ", phoneNumber=" + phoneNumber + ", projectName=" + projectName + ", propertyName=" + propertyName + ", userId=" + userId + ", lastUpdatedTimeStamp=" + lastUpdatedTimeStamp + '}';
    }

}
