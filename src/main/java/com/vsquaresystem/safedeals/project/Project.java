package com.vsquaresystem.safedeals.project;

import java.util.Date;
import java.util.Objects;

public class Project {

    private Integer id;
    private String name;
    private String description;
    private Integer locationId;
    private Integer cityId;
    private Date dateOfConstruction;
    private Date dateOfPossession;
    private Double latitude;
    private Double longitude;
    private Double projectCost;

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

    public Double getProjectCost() {
        return projectCost;
    }

    public void setProjectCost(Double projectCost) {
        this.projectCost = projectCost;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.id);
        hash = 29 * hash + Objects.hashCode(this.name);
        hash = 29 * hash + Objects.hashCode(this.description);
        hash = 29 * hash + Objects.hashCode(this.locationId);
        hash = 29 * hash + Objects.hashCode(this.cityId);
        hash = 29 * hash + Objects.hashCode(this.dateOfConstruction);
        hash = 29 * hash + Objects.hashCode(this.dateOfPossession);
        hash = 29 * hash + Objects.hashCode(this.latitude);
        hash = 29 * hash + Objects.hashCode(this.longitude);
        hash = 29 * hash + Objects.hashCode(this.projectCost);
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
        final Project other = (Project) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.locationId, other.locationId)) {
            return false;
        }
        if (!Objects.equals(this.cityId, other.cityId)) {
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
        if (!Objects.equals(this.projectCost, other.projectCost)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Project{" + "id=" + id + ", name=" + name + ", description=" + description + ", locationId=" + locationId + ", cityId=" + cityId + ", dateOfConstruction=" + dateOfConstruction + ", dateOfPossession=" + dateOfPossession + ", latitude=" + latitude + ", longitude=" + longitude + ", projectCost=" + projectCost + '}';
    }
}
