/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.amenitycode;

import java.util.Objects;

/**
 *
 * @author hp2
 */
public class AmenityCode {

    private Integer id;
    private String name;
    private AmenityDetailTab amenityDetailTab;

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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.id);
        hash = 29 * hash + Objects.hashCode(this.name);
        hash = 29 * hash + Objects.hashCode(this.amenityDetailTab);
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
        final AmenityCode other = (AmenityCode) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (this.amenityDetailTab != other.amenityDetailTab) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "AmenityCode{" + "id=" + id + ", name=" + name + ", amenityDetailTab=" + amenityDetailTab + '}';
    }

    
}
