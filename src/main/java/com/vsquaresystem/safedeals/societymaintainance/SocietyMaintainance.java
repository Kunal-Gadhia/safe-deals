/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.societymaintainance;

import java.util.Objects;

/**
 *
 * @author hp
 */
public class SocietyMaintainance {

    private Integer id;
    private String maintainanceName;
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMaintainanceName() {
        return maintainanceName;
    }

    public void setMaintainanceName(String maintainanceName) {
        this.maintainanceName = maintainanceName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.id);
        hash = 31 * hash + Objects.hashCode(this.maintainanceName);
        hash = 31 * hash + Objects.hashCode(this.description);
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
        final SocietyMaintainance other = (SocietyMaintainance) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.maintainanceName, other.maintainanceName)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SocietyMaintainance{" + "id=" + id + ", maintainanceName=" + maintainanceName + ", description=" + description + '}';
    }

}
