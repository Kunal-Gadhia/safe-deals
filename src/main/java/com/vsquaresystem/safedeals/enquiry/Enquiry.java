/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.enquiry;

import java.util.Objects;

/**
 *
 * @author hp4
 */
public class Enquiry {

    private Integer id;
    private String name;
    private String mobileNo;
    private String email;
    private ModeOfContact modeOfContact;
    private Category category;
    private String propertyGuidance;
    private String propertySelling;
    private String propertyBuying;

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

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ModeOfContact getModeOfContact() {
        return modeOfContact;
    }

    public void setModeOfContact(ModeOfContact modeOfContact) {
        this.modeOfContact = modeOfContact;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getPropertyGuidance() {
        return propertyGuidance;
    }

    public void setPropertyGuidance(String propertyGuidance) {
        this.propertyGuidance = propertyGuidance;
    }

    public String getPropertySelling() {
        return propertySelling;
    }

    public void setPropertySelling(String propertySelling) {
        this.propertySelling = propertySelling;
    }

    public String getPropertyBuying() {
        return propertyBuying;
    }

    public void setPropertyBuying(String propertyBuying) {
        this.propertyBuying = propertyBuying;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.id);
        hash = 17 * hash + Objects.hashCode(this.name);
        hash = 17 * hash + Objects.hashCode(this.mobileNo);
        hash = 17 * hash + Objects.hashCode(this.email);
        hash = 17 * hash + Objects.hashCode(this.modeOfContact);
        hash = 17 * hash + Objects.hashCode(this.category);
        hash = 17 * hash + Objects.hashCode(this.propertyGuidance);
        hash = 17 * hash + Objects.hashCode(this.propertySelling);
        hash = 17 * hash + Objects.hashCode(this.propertyBuying);
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
        final Enquiry other = (Enquiry) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.mobileNo, other.mobileNo)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (this.modeOfContact != other.modeOfContact) {
            return false;
        }
        if (this.category != other.category) {
            return false;
        }
        if (!Objects.equals(this.propertyGuidance, other.propertyGuidance)) {
            return false;
        }
        if (!Objects.equals(this.propertySelling, other.propertySelling)) {
            return false;
        }
        if (!Objects.equals(this.propertyBuying, other.propertyBuying)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Enquiry{" + "id=" + id + ", name=" + name + ", mobileNo=" + mobileNo + ", email=" + email + ", modeOfContact=" + modeOfContact + ", category=" + category + ", propertyGuidance=" + propertyGuidance + ", propertySelling=" + propertySelling + ", propertyBuying=" + propertyBuying + '}';
    }

}
