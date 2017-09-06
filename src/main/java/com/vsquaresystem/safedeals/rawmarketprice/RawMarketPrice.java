/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.rawmarketprice;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author hp-pc
 */
public class RawMarketPrice {

    private Integer id;
    private Integer cityId;
    private String locationName;
    private Integer safedealZoneId;
    private Integer locationTypeId;
    private List<Integer> locationCategories;
    private String description;
    private Integer year;
    private Integer month;
    private Double mpAgriLandLowest;
    private Double mpAgriLandHighest;
    private Double mpPlotLowest;
    private Double mpPlotHighest;
    private Double mpResidentialLowest;
    private Double mpResidentialHighest;
    private Double mpCommercialLowest;
    private Double mpCommercialHighest;
    private Boolean isCommercialCenter;
    private String majorApproachRoad;
    private String sourceOfWater;
    private String publicTransport;
    private String advantage;
    private String disadvantage;
    private Integer population;
    private Integer migrationRate;
    private Integer userId;
    private Date lastUpdatedTimeStamp;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Integer getSafedealZoneId() {
        return safedealZoneId;
    }

    public void setSafedealZoneId(Integer safedealZoneId) {
        this.safedealZoneId = safedealZoneId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Double getMpAgriLandLowest() {
        return mpAgriLandLowest;
    }

    public void setMpAgriLandLowest(Double mpAgriLandLowest) {
        this.mpAgriLandLowest = mpAgriLandLowest;
    }

    public Double getMpAgriLandHighest() {
        return mpAgriLandHighest;
    }

    public void setMpAgriLandHighest(Double mpAgriLandHighest) {
        this.mpAgriLandHighest = mpAgriLandHighest;
    }

    public Double getMpPlotLowest() {
        return mpPlotLowest;
    }

    public void setMpPlotLowest(Double mpPlotLowest) {
        this.mpPlotLowest = mpPlotLowest;
    }

    public Double getMpPlotHighest() {
        return mpPlotHighest;
    }

    public void setMpPlotHighest(Double mpPlotHighest) {
        this.mpPlotHighest = mpPlotHighest;
    }

    public Double getMpResidentialLowest() {
        return mpResidentialLowest;
    }

    public void setMpResidentialLowest(Double mpResidentialLowest) {
        this.mpResidentialLowest = mpResidentialLowest;
    }

    public Double getMpResidentialHighest() {
        return mpResidentialHighest;
    }

    public void setMpResidentialHighest(Double mpResidentialHighest) {
        this.mpResidentialHighest = mpResidentialHighest;
    }

    public Double getMpCommercialLowest() {
        return mpCommercialLowest;
    }

    public void setMpCommercialLowest(Double mpCommercialLowest) {
        this.mpCommercialLowest = mpCommercialLowest;
    }

    public Double getMpCommercialHighest() {
        return mpCommercialHighest;
    }

    public void setMpCommercialHighest(Double mpCommercialHighest) {
        this.mpCommercialHighest = mpCommercialHighest;
    }

    public Boolean getIsCommercialCenter() {
        return isCommercialCenter;
    }

    public void setIsCommercialCenter(Boolean isCommercialCenter) {
        this.isCommercialCenter = isCommercialCenter;
    }

    public String getMajorApproachRoad() {
        return majorApproachRoad;
    }

    public void setMajorApproachRoad(String majorApproachRoad) {
        this.majorApproachRoad = majorApproachRoad;
    }

    public String getSourceOfWater() {
        return sourceOfWater;
    }

    public void setSourceOfWater(String sourceOfWater) {
        this.sourceOfWater = sourceOfWater;
    }

    public String getPublicTransport() {
        return publicTransport;
    }

    public void setPublicTransport(String publicTransport) {
        this.publicTransport = publicTransport;
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

    public Integer getMigrationRate() {
        return migrationRate;
    }

    public void setMigrationRate(Integer migrationRate) {
        this.migrationRate = migrationRate;
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
        hash = 53 * hash + Objects.hashCode(this.id);
        hash = 53 * hash + Objects.hashCode(this.cityId);
        hash = 53 * hash + Objects.hashCode(this.locationName);
        hash = 53 * hash + Objects.hashCode(this.safedealZoneId);
        hash = 53 * hash + Objects.hashCode(this.locationTypeId);
        hash = 53 * hash + Objects.hashCode(this.locationCategories);
        hash = 53 * hash + Objects.hashCode(this.description);
        hash = 53 * hash + Objects.hashCode(this.year);
        hash = 53 * hash + Objects.hashCode(this.month);
        hash = 53 * hash + Objects.hashCode(this.mpAgriLandLowest);
        hash = 53 * hash + Objects.hashCode(this.mpAgriLandHighest);
        hash = 53 * hash + Objects.hashCode(this.mpPlotLowest);
        hash = 53 * hash + Objects.hashCode(this.mpPlotHighest);
        hash = 53 * hash + Objects.hashCode(this.mpResidentialLowest);
        hash = 53 * hash + Objects.hashCode(this.mpResidentialHighest);
        hash = 53 * hash + Objects.hashCode(this.mpCommercialLowest);
        hash = 53 * hash + Objects.hashCode(this.mpCommercialHighest);
        hash = 53 * hash + Objects.hashCode(this.isCommercialCenter);
        hash = 53 * hash + Objects.hashCode(this.majorApproachRoad);
        hash = 53 * hash + Objects.hashCode(this.sourceOfWater);
        hash = 53 * hash + Objects.hashCode(this.publicTransport);
        hash = 53 * hash + Objects.hashCode(this.advantage);
        hash = 53 * hash + Objects.hashCode(this.disadvantage);
        hash = 53 * hash + Objects.hashCode(this.population);
        hash = 53 * hash + Objects.hashCode(this.migrationRate);
        hash = 53 * hash + Objects.hashCode(this.userId);
        hash = 53 * hash + Objects.hashCode(this.lastUpdatedTimeStamp);
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
        final RawMarketPrice other = (RawMarketPrice) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.cityId, other.cityId)) {
            return false;
        }
        if (!Objects.equals(this.locationName, other.locationName)) {
            return false;
        }
        if (!Objects.equals(this.safedealZoneId, other.safedealZoneId)) {
            return false;
        }
        if (!Objects.equals(this.locationTypeId, other.locationTypeId)) {
            return false;
        }
        if (!Objects.equals(this.locationCategories, other.locationCategories)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.year, other.year)) {
            return false;
        }
        if (!Objects.equals(this.month, other.month)) {
            return false;
        }
        if (!Objects.equals(this.mpAgriLandLowest, other.mpAgriLandLowest)) {
            return false;
        }
        if (!Objects.equals(this.mpAgriLandHighest, other.mpAgriLandHighest)) {
            return false;
        }
        if (!Objects.equals(this.mpPlotLowest, other.mpPlotLowest)) {
            return false;
        }
        if (!Objects.equals(this.mpPlotHighest, other.mpPlotHighest)) {
            return false;
        }
        if (!Objects.equals(this.mpResidentialLowest, other.mpResidentialLowest)) {
            return false;
        }
        if (!Objects.equals(this.mpResidentialHighest, other.mpResidentialHighest)) {
            return false;
        }
        if (!Objects.equals(this.mpCommercialLowest, other.mpCommercialLowest)) {
            return false;
        }
        if (!Objects.equals(this.mpCommercialHighest, other.mpCommercialHighest)) {
            return false;
        }
        if (!Objects.equals(this.isCommercialCenter, other.isCommercialCenter)) {
            return false;
        }
        if (!Objects.equals(this.majorApproachRoad, other.majorApproachRoad)) {
            return false;
        }
        if (!Objects.equals(this.sourceOfWater, other.sourceOfWater)) {
            return false;
        }
        if (!Objects.equals(this.publicTransport, other.publicTransport)) {
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
        if (!Objects.equals(this.migrationRate, other.migrationRate)) {
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
        return "RawMarketPrice{" + "id=" + id + ", cityId=" + cityId + ", locationName=" + locationName + ", safedealZoneId=" + safedealZoneId + ", locationTypeId=" + locationTypeId + ", locationCategories=" + locationCategories + ", description=" + description + ", year=" + year + ", month=" + month + ", mpAgriLandLowest=" + mpAgriLandLowest + ", mpAgriLandHighest=" + mpAgriLandHighest + ", mpPlotLowest=" + mpPlotLowest + ", mpPlotHighest=" + mpPlotHighest + ", mpResidentialLowest=" + mpResidentialLowest + ", mpResidentialHighest=" + mpResidentialHighest + ", mpCommercialLowest=" + mpCommercialLowest + ", mpCommercialHighest=" + mpCommercialHighest + ", isCommercialCenter=" + isCommercialCenter + ", majorApproachRoad=" + majorApproachRoad + ", sourceOfWater=" + sourceOfWater + ", publicTransport=" + publicTransport + ", advantage=" + advantage + ", disadvantage=" + disadvantage + ", population=" + population + ", migrationRate=" + migrationRate + ", userId=" + userId + ", lastUpdatedTimeStamp=" + lastUpdatedTimeStamp + '}';
    }

}
