/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.branch;

import java.util.Objects;

/**
 *
 * @author hp2
 */
public class Branch {
    
    private Integer id;
    private String name;
    private String bankId;
    private String ifscCode;
    private Integer micrCode;
    private String phoneNo;
    private String fax;
    private String email;
    private Integer locationId;
    private String address;
    private Integer cityId;

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

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public Integer getMicrCode() {
        return micrCode;
    }

    public void setMicrCode(Integer micrCode) {
        this.micrCode = micrCode;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.id);
        hash = 59 * hash + Objects.hashCode(this.name);
        hash = 59 * hash + Objects.hashCode(this.bankId);
        hash = 59 * hash + Objects.hashCode(this.ifscCode);
        hash = 59 * hash + Objects.hashCode(this.micrCode);
        hash = 59 * hash + Objects.hashCode(this.phoneNo);
        hash = 59 * hash + Objects.hashCode(this.fax);
        hash = 59 * hash + Objects.hashCode(this.email);
        hash = 59 * hash + Objects.hashCode(this.locationId);
        hash = 59 * hash + Objects.hashCode(this.address);
        hash = 59 * hash + Objects.hashCode(this.cityId);
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
        final Branch other = (Branch) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.bankId, other.bankId)) {
            return false;
        }
        if (!Objects.equals(this.ifscCode, other.ifscCode)) {
            return false;
        }
        if (!Objects.equals(this.micrCode, other.micrCode)) {
            return false;
        }
        if (!Objects.equals(this.phoneNo, other.phoneNo)) {
            return false;
        }
        if (!Objects.equals(this.fax, other.fax)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.locationId, other.locationId)) {
            return false;
        }
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        if (!Objects.equals(this.cityId, other.cityId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Branch{" + "id=" + id + ", name=" + name + ", bankId=" + bankId + ", ifscCode=" + ifscCode + ", micrCode=" + micrCode + ", phoneNo=" + phoneNo + ", fax=" + fax + ", email=" + email + ", locationId=" + locationId + ", address=" + address + ", cityId=" + cityId + '}';
    }

    
}

