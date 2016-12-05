package com.vsquaresystem.safedeals.location;

import java.util.List;
import java.util.Objects;

public class Location {

    private Integer id;
    private String name;
    private String description;
    private Integer cityId;
    private Integer locationTypeId;
    private List<Integer> locationCategories;
    private Integer safedealZoneId;
    private String majorApproachRoad;
    private String advantage;
    private String disadvantage;
    private Integer population;
    private Double latitude;
    private Double longitude;
    private SourceOfWater sourceOfWater;
    private PublicTransport publicTransport;
    private Double migrationRatePerAnnum;
    private Double distanceFromCentreOfCity;
    private Boolean isCommercialCenter;
    private Double distanceFromCommercialCenter;
    private String imageUrl;

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

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getLocationTypeId() {
        return locationTypeId;
    }

    public void setLocationTypeId(Integer locationTypeId) {
        this.locationTypeId = locationTypeId;
    }

    public List<Integer> getLocationCategories() {
        return locationCategories;
    }

    public void setLocationCategories(List<Integer> locationCategories) {
        this.locationCategories = locationCategories;
    }

    public Integer getSafedealZoneId() {
        return safedealZoneId;
    }

    public void setSafedealZoneId(Integer safedealZoneId) {
        this.safedealZoneId = safedealZoneId;
    }

    public String getMajorApproachRoad() {
        return majorApproachRoad;
    }

    public void setMajorApproachRoad(String majorApproachRoad) {
        this.majorApproachRoad = majorApproachRoad;
    }

    public String getAdvantage() {
        return advantage;
    }

    public void setAdvantage(String advantage) {
        this.advantage = advantage;
    }

    public String getDisadvantage() {
        return disadvantage;
    }

    public void setDisadvantage(String disadvantage) {
        this.disadvantage = disadvantage;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
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

    public SourceOfWater getSourceOfWater() {
        return sourceOfWater;
    }

    public void setSourceOfWater(SourceOfWater sourceOfWater) {
        this.sourceOfWater = sourceOfWater;
    }

    public PublicTransport getPublicTransport() {
        return publicTransport;
    }

    public void setPublicTransport(PublicTransport publicTransport) {
        this.publicTransport = publicTransport;
    }

    public Double getMigrationRatePerAnnum() {
        return migrationRatePerAnnum;
    }

    public void setMigrationRatePerAnnum(Double migrationRatePerAnnum) {
        this.migrationRatePerAnnum = migrationRatePerAnnum;
    }

    public Double getDistanceFromCentreOfCity() {
        return distanceFromCentreOfCity;
    }

    public void setDistanceFromCentreOfCity(Double distanceFromCentreOfCity) {
        this.distanceFromCentreOfCity = distanceFromCentreOfCity;
    }

    public Boolean getIsCommercialCenter() {
        return isCommercialCenter;
    }

    public void setIsCommercialCenter(Boolean isCommercialCenter) {
        this.isCommercialCenter = isCommercialCenter;
    }

    public Double getDistanceFromCommercialCenter() {
        return distanceFromCommercialCenter;
    }

    public void setDistanceFromCommercialCenter(Double distanceFromCommercialCenter) {
        this.distanceFromCommercialCenter = distanceFromCommercialCenter;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.id);
        hash = 29 * hash + Objects.hashCode(this.name);
        hash = 29 * hash + Objects.hashCode(this.description);
        hash = 29 * hash + Objects.hashCode(this.cityId);
        hash = 29 * hash + Objects.hashCode(this.locationTypeId);
        hash = 29 * hash + Objects.hashCode(this.locationCategories);
        hash = 29 * hash + Objects.hashCode(this.safedealZoneId);
        hash = 29 * hash + Objects.hashCode(this.majorApproachRoad);
        hash = 29 * hash + Objects.hashCode(this.advantage);
        hash = 29 * hash + Objects.hashCode(this.disadvantage);
        hash = 29 * hash + Objects.hashCode(this.population);
        hash = 29 * hash + Objects.hashCode(this.latitude);
        hash = 29 * hash + Objects.hashCode(this.longitude);
        hash = 29 * hash + Objects.hashCode(this.sourceOfWater);
        hash = 29 * hash + Objects.hashCode(this.publicTransport);
        hash = 29 * hash + Objects.hashCode(this.migrationRatePerAnnum);
        hash = 29 * hash + Objects.hashCode(this.distanceFromCentreOfCity);
        hash = 29 * hash + Objects.hashCode(this.isCommercialCenter);
        hash = 29 * hash + Objects.hashCode(this.distanceFromCommercialCenter);
        hash = 29 * hash + Objects.hashCode(this.imageUrl);
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
        final Location other = (Location) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.cityId, other.cityId)) {
            return false;
        }
        if (!Objects.equals(this.locationTypeId, other.locationTypeId)) {
            return false;
        }
        if (!Objects.equals(this.locationCategories, other.locationCategories)) {
            return false;
        }
        if (!Objects.equals(this.safedealZoneId, other.safedealZoneId)) {
            return false;
        }
        if (!Objects.equals(this.majorApproachRoad, other.majorApproachRoad)) {
            return false;
        }
        if (!Objects.equals(this.advantage, other.advantage)) {
            return false;
        }
        if (!Objects.equals(this.disadvantage, other.disadvantage)) {
            return false;
        }
        if (!Objects.equals(this.population, other.population)) {
            return false;
        }
        if (!Objects.equals(this.latitude, other.latitude)) {
            return false;
        }
        if (!Objects.equals(this.longitude, other.longitude)) {
            return false;
        }
        if (this.sourceOfWater != other.sourceOfWater) {
            return false;
        }
        if (this.publicTransport != other.publicTransport) {
            return false;
        }
        if (!Objects.equals(this.migrationRatePerAnnum, other.migrationRatePerAnnum)) {
            return false;
        }
        if (!Objects.equals(this.distanceFromCentreOfCity, other.distanceFromCentreOfCity)) {
            return false;
        }
        if (!Objects.equals(this.isCommercialCenter, other.isCommercialCenter)) {
            return false;
        }
        if (!Objects.equals(this.distanceFromCommercialCenter, other.distanceFromCommercialCenter)) {
            return false;
        }
        if (!Objects.equals(this.imageUrl, other.imageUrl)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Location{" + "id=" + id + ", name=" + name + ", description=" + description + ", cityId=" + cityId + ", locationTypeId=" + locationTypeId + ", locationCategories=" + locationCategories + ", safedealZoneId=" + safedealZoneId + ", majorApproachRoad=" + majorApproachRoad + ", advantage=" + advantage + ", disadvantage=" + disadvantage + ", population=" + population + ", latitude=" + latitude + ", longitude=" + longitude + ", sourceOfWater=" + sourceOfWater + ", publicTransport=" + publicTransport + ", migrationRatePerAnnum=" + migrationRatePerAnnum + ", distanceFromCentreOfCity=" + distanceFromCentreOfCity + ", isCommercialCenter=" + isCommercialCenter + ", distanceFromCommercialCenter=" + distanceFromCommercialCenter + ", imageUrl=" + imageUrl + '}';
    }

    
    
    
}