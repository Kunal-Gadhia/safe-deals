package com.vsquaresystem.safedeals.amenitydetail;

import java.util.Date;
import java.util.Objects;

public class AmenityDetail {

    private Integer id;
    private String name;
    private String address;
    private String phoneNumber;
    private String mobileNumber;
    private Integer locationId;
    private Integer amenityId;
    private Double latitude;
    private Double longitude;
    private Integer cityId;
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

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public Integer getAmenityId() {
        return amenityId;
    }

    public void setAmenityId(Integer amenityId) {
        this.amenityId = amenityId;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
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
        hash = 53 * hash + Objects.hashCode(this.id);
        hash = 53 * hash + Objects.hashCode(this.name);
        hash = 53 * hash + Objects.hashCode(this.address);
        hash = 53 * hash + Objects.hashCode(this.phoneNumber);
        hash = 53 * hash + Objects.hashCode(this.mobileNumber);
        hash = 53 * hash + Objects.hashCode(this.locationId);
        hash = 53 * hash + Objects.hashCode(this.amenityId);
        hash = 53 * hash + Objects.hashCode(this.latitude);
        hash = 53 * hash + Objects.hashCode(this.longitude);
        hash = 53 * hash + Objects.hashCode(this.cityId);
        hash = 53 * hash + Objects.hashCode(this.userId);
        hash = 53 * hash + Objects.hashCode(this.lastUpdatedTimeStamp);
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
        final AmenityDetail other = (AmenityDetail) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        if (!Objects.equals(this.phoneNumber, other.phoneNumber)) {
            return false;
        }
        if (!Objects.equals(this.mobileNumber, other.mobileNumber)) {
            return false;
        }
        if (!Objects.equals(this.locationId, other.locationId)) {
            return false;
        }
        if (!Objects.equals(this.amenityId, other.amenityId)) {
            return false;
        }
        if (!Objects.equals(this.latitude, other.latitude)) {
            return false;
        }
        if (!Objects.equals(this.longitude, other.longitude)) {
            return false;
        }
        if (!Objects.equals(this.cityId, other.cityId)) {
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
        return "AmenityDetail{" + "id=" + id + ", name=" + name + ", address=" + address + ", phoneNumber=" + phoneNumber + ", mobileNumber=" + mobileNumber + ", locationId=" + locationId + ", amenityId=" + amenityId + ", latitude=" + latitude + ", longitude=" + longitude + ", cityId=" + cityId + ", userId=" + userId + ", lastUpdatedTimeStamp=" + lastUpdatedTimeStamp + '}';
    }

}
