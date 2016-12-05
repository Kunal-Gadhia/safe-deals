package com.vsquaresystem.safedeals.amenitydetail;

public class AmenityDetailFilter {
    private Integer locationId;
    private Integer amenityId;
    private String name;
    
    public AmenityDetailFilter(Integer locationId, Integer amenityId, String name){
        this.locationId = locationId;
        this.amenityId = amenityId;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "AmenityDetailFilter{" + "locationId=" + locationId + ", amenityId=" + amenityId + ", name=" + name + '}';
    }
}