package com.vsquaresystem.safedeals.amenity;

import java.util.Date;
import java.util.Objects;

public class Amenity {

    private Integer id;
    private String name;
    private String description;
    private Integer amenityCodeId;
    private AmenityType amenityType;
    private String icon;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getAmenityCodeId() {
        return amenityCodeId;
    }

    public void setAmenityCodeId(Integer amenityCodeId) {
        this.amenityCodeId = amenityCodeId;
    }

    public AmenityType getAmenityType() {
        return amenityType;
    }

    public void setAmenityType(AmenityType amenityType) {
        this.amenityType = amenityType;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
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
        hash = 43 * hash + Objects.hashCode(this.id);
        hash = 43 * hash + Objects.hashCode(this.name);
        hash = 43 * hash + Objects.hashCode(this.description);
        hash = 43 * hash + Objects.hashCode(this.amenityCodeId);
        hash = 43 * hash + Objects.hashCode(this.amenityType);
        hash = 43 * hash + Objects.hashCode(this.icon);
        hash = 43 * hash + Objects.hashCode(this.userId);
        hash = 43 * hash + Objects.hashCode(this.lastUpdatedTimeStamp);
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
        final Amenity other = (Amenity) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.amenityCodeId, other.amenityCodeId)) {
            return false;
        }
        if (this.amenityType != other.amenityType) {
            return false;
        }
        if (!Objects.equals(this.icon, other.icon)) {
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
        return "Amenity{" + "id=" + id + ", name=" + name + ", description=" + description + ", amenityCodeId=" + amenityCodeId + ", amenityType=" + amenityType + ", icon=" + icon + ", userId=" + userId + ", lastUpdatedTimeStamp=" + lastUpdatedTimeStamp + '}';
    }

}
