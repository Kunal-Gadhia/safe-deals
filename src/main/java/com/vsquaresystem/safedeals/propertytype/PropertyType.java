package com.vsquaresystem.safedeals.propertytype;

import java.util.Date;
import java.util.Objects;

public class PropertyType {

    private Integer id;
    private String numberOfBhk;
    private Integer carpetArea;
    private String priceRange;
    private Integer userId;
    private Date lastUpdatedTimeStamp;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumberOfBhk() {
        return numberOfBhk;
    }

    public void setNumberOfBhk(String numberOfBhk) {
        this.numberOfBhk = numberOfBhk;
    }

    public Integer getCarpetArea() {
        return carpetArea;
    }

    public void setCarpetArea(Integer carpetArea) {
        this.carpetArea = carpetArea;
    }

    public String getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(String priceRange) {
        this.priceRange = priceRange;
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
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.id);
        hash = 37 * hash + Objects.hashCode(this.numberOfBhk);
        hash = 37 * hash + Objects.hashCode(this.carpetArea);
        hash = 37 * hash + Objects.hashCode(this.priceRange);
        hash = 37 * hash + Objects.hashCode(this.userId);
        hash = 37 * hash + Objects.hashCode(this.lastUpdatedTimeStamp);
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
        final PropertyType other = (PropertyType) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.numberOfBhk, other.numberOfBhk)) {
            return false;
        }
        if (!Objects.equals(this.carpetArea, other.carpetArea)) {
            return false;
        }
        if (!Objects.equals(this.priceRange, other.priceRange)) {
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
        return "PropertyType{" + "id=" + id + ", numberOfBhk=" + numberOfBhk + ", carpetArea=" + carpetArea + ", priceRange=" + priceRange + ", userId=" + userId + ", lastUpdatedTimeStamp=" + lastUpdatedTimeStamp + '}';
    }

}
