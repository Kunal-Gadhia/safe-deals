package com.vsquaresystem.safedeals.businessassociate;

import java.util.Date;
import java.util.Objects;

public class BusinessAssociate {

    private Integer id;
    private String name;
    private String address;
    private Integer cityId;
    private Integer locationId;
    private String email;
    private String phoneNumber1;
    private String phoneNumber2;
    private String fax;
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

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber1() {
        return phoneNumber1;
    }

    public void setPhoneNumber1(String phoneNumber1) {
        this.phoneNumber1 = phoneNumber1;
    }

    public String getPhoneNumber2() {
        return phoneNumber2;
    }

    public void setPhoneNumber2(String phoneNumber2) {
        this.phoneNumber2 = phoneNumber2;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
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
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + Objects.hashCode(this.name);
        hash = 97 * hash + Objects.hashCode(this.address);
        hash = 97 * hash + Objects.hashCode(this.cityId);
        hash = 97 * hash + Objects.hashCode(this.locationId);
        hash = 97 * hash + Objects.hashCode(this.email);
        hash = 97 * hash + Objects.hashCode(this.phoneNumber1);
        hash = 97 * hash + Objects.hashCode(this.phoneNumber2);
        hash = 97 * hash + Objects.hashCode(this.fax);
        hash = 97 * hash + Objects.hashCode(this.userId);
        hash = 97 * hash + Objects.hashCode(this.lastUpdatedTimeStamp);
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
        final BusinessAssociate other = (BusinessAssociate) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        if (!Objects.equals(this.cityId, other.cityId)) {
            return false;
        }
        if (!Objects.equals(this.locationId, other.locationId)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.phoneNumber1, other.phoneNumber1)) {
            return false;
        }
        if (!Objects.equals(this.phoneNumber2, other.phoneNumber2)) {
            return false;
        }
        if (!Objects.equals(this.fax, other.fax)) {
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
        return "BusinessAssociate{" + "id=" + id + ", name=" + name + ", address=" + address + ", cityId=" + cityId + ", locationId=" + locationId + ", email=" + email + ", phoneNumber1=" + phoneNumber1 + ", phoneNumber2=" + phoneNumber2 + ", fax=" + fax + ", userId=" + userId + ", lastUpdatedTimeStamp=" + lastUpdatedTimeStamp + '}';
    }

}
