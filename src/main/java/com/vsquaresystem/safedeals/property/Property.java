/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.property;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author hp-pc
 */
public class Property {

    private Integer id;
    private String name;
    private String description;
    private Double propertyCost;
    private Integer locationId;
    private Integer cityId;
    private String propertyType;
    private Date dateOfConstruction;
    private Date dateOfPossession;
    private Double latitude;
    private Double longitude;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPropertyCost() {
        return propertyCost;
    }

    public void setPropertyCost(Double propertyCost) {
        this.propertyCost = propertyCost;
    }
    
    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public Date getDateOfConstruction() {
        return dateOfConstruction;
    }

    public void setDateOfConstruction(Date dateOfConstruction) {
        this.dateOfConstruction = dateOfConstruction;
    }

    public Date getDateOfPossession() {
        return dateOfPossession;
    }

    public void setDateOfPossession(Date dateOfPossession) {
        this.dateOfPossession = dateOfPossession;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.id);
        hash = 11 * hash + Objects.hashCode(this.name);
        hash = 11 * hash + Objects.hashCode(this.description);
        hash = 11 * hash + Objects.hashCode(this.propertyCost);
        hash = 11 * hash + Objects.hashCode(this.locationId);
        hash = 11 * hash + Objects.hashCode(this.cityId);
        hash = 11 * hash + Objects.hashCode(this.propertyType);
        hash = 11 * hash + Objects.hashCode(this.dateOfConstruction);
        hash = 11 * hash + Objects.hashCode(this.dateOfPossession);
        hash = 11 * hash + Objects.hashCode(this.latitude);
        hash = 11 * hash + Objects.hashCode(this.longitude);
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
        final Property other = (Property) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.propertyCost, other.propertyCost)) {
            return false;
        }
        if (!Objects.equals(this.locationId, other.locationId)) {
            return false;
        }
        if (!Objects.equals(this.cityId, other.cityId)) {
            return false;
        }
        if (!Objects.equals(this.propertyType, other.propertyType)) {
            return false;
        }
        if (!Objects.equals(this.dateOfConstruction, other.dateOfConstruction)) {
            return false;
        }
        if (!Objects.equals(this.dateOfPossession, other.dateOfPossession)) {
            return false;
        }
        if (!Objects.equals(this.latitude, other.latitude)) {
            return false;
        }
        if (!Objects.equals(this.longitude, other.longitude)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Property{" + "id=" + id + ", name=" + name + ", description=" + description + ", propertyCost=" + propertyCost + ", locationId=" + locationId + ", cityId=" + cityId + ", propertyType=" + propertyType + ", dateOfConstruction=" + dateOfConstruction + ", dateOfPossession=" + dateOfPossession + ", latitude=" + latitude + ", longitude=" + longitude + '}';
    }
}
