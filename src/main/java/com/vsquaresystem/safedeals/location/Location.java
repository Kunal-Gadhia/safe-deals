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
    private DemandPotential demandPotential;
    private Boolean powerPlant;
    private Boolean medicineIndustry;
    private Boolean steelIndustry;
    private Boolean filthyLake;
    private Boolean lowLyingArea;
    private Boolean dumpYard;
    private Boolean stp;
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

    public DemandPotential getDemandPotential() {
        return demandPotential;
    }

    public void setDemandPotential(DemandPotential demandPotential) {
        this.demandPotential = demandPotential;
    }

    public Boolean getPowerPlant() {
        return powerPlant;
    }

    public void setPowerPlant(Boolean powerPlant) {
        this.powerPlant = powerPlant;
    }

    public Boolean getMedicineIndustry() {
        return medicineIndustry;
    }

    public void setMedicineIndustry(Boolean medicineIndustry) {
        this.medicineIndustry = medicineIndustry;
    }

    public Boolean getSteelIndustry() {
        return steelIndustry;
    }

    public void setSteelIndustry(Boolean steelIndustry) {
        this.steelIndustry = steelIndustry;
    }

    public Boolean getFilthyLake() {
        return filthyLake;
    }

    public void setFilthyLake(Boolean filthyLake) {
        this.filthyLake = filthyLake;
    }

    public Boolean getLowLyingArea() {
        return lowLyingArea;
    }

    public void setLowLyingArea(Boolean lowLyingArea) {
        this.lowLyingArea = lowLyingArea;
    }

    public Boolean getDumpYard() {
        return dumpYard;
    }

    public void setDumpYard(Boolean dumpYard) {
        this.dumpYard = dumpYard;
    }

    public Boolean getStp() {
        return stp;
    }

    public void setStp(Boolean stp) {
        this.stp = stp;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + Objects.hashCode(this.id);
        hash = 13 * hash + Objects.hashCode(this.name);
        hash = 13 * hash + Objects.hashCode(this.description);
        hash = 13 * hash + Objects.hashCode(this.cityId);
        hash = 13 * hash + Objects.hashCode(this.locationTypeId);
        hash = 13 * hash + Objects.hashCode(this.locationCategories);
        hash = 13 * hash + Objects.hashCode(this.safedealZoneId);
        hash = 13 * hash + Objects.hashCode(this.majorApproachRoad);
        hash = 13 * hash + Objects.hashCode(this.advantage);
        hash = 13 * hash + Objects.hashCode(this.disadvantage);
        hash = 13 * hash + Objects.hashCode(this.population);
        hash = 13 * hash + Objects.hashCode(this.latitude);
        hash = 13 * hash + Objects.hashCode(this.longitude);
        hash = 13 * hash + Objects.hashCode(this.sourceOfWater);
        hash = 13 * hash + Objects.hashCode(this.publicTransport);
        hash = 13 * hash + Objects.hashCode(this.migrationRatePerAnnum);
        hash = 13 * hash + Objects.hashCode(this.distanceFromCentreOfCity);
        hash = 13 * hash + Objects.hashCode(this.isCommercialCenter);
        hash = 13 * hash + Objects.hashCode(this.distanceFromCommercialCenter);
        hash = 13 * hash + Objects.hashCode(this.demandPotential);
        hash = 13 * hash + Objects.hashCode(this.powerPlant);
        hash = 13 * hash + Objects.hashCode(this.medicineIndustry);
        hash = 13 * hash + Objects.hashCode(this.steelIndustry);
        hash = 13 * hash + Objects.hashCode(this.filthyLake);
        hash = 13 * hash + Objects.hashCode(this.lowLyingArea);
        hash = 13 * hash + Objects.hashCode(this.dumpYard);
        hash = 13 * hash + Objects.hashCode(this.stp);
        hash = 13 * hash + Objects.hashCode(this.imageUrl);
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
        if (this.demandPotential != other.demandPotential) {
            return false;
        }
        if (!Objects.equals(this.powerPlant, other.powerPlant)) {
            return false;
        }
        if (!Objects.equals(this.medicineIndustry, other.medicineIndustry)) {
            return false;
        }
        if (!Objects.equals(this.steelIndustry, other.steelIndustry)) {
            return false;
        }
        if (!Objects.equals(this.filthyLake, other.filthyLake)) {
            return false;
        }
        if (!Objects.equals(this.lowLyingArea, other.lowLyingArea)) {
            return false;
        }
        if (!Objects.equals(this.dumpYard, other.dumpYard)) {
            return false;
        }
        if (!Objects.equals(this.stp, other.stp)) {
            return false;
        }
        if (!Objects.equals(this.imageUrl, other.imageUrl)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Location{" + "id=" + id + ", name=" + name + ", description=" + description + ", cityId=" + cityId + ", locationTypeId=" + locationTypeId + ", locationCategories=" + locationCategories + ", safedealZoneId=" + safedealZoneId + ", majorApproachRoad=" + majorApproachRoad + ", advantage=" + advantage + ", disadvantage=" + disadvantage + ", population=" + population + ", latitude=" + latitude + ", longitude=" + longitude + ", sourceOfWater=" + sourceOfWater + ", publicTransport=" + publicTransport + ", migrationRatePerAnnum=" + migrationRatePerAnnum + ", distanceFromCentreOfCity=" + distanceFromCentreOfCity + ", isCommercialCenter=" + isCommercialCenter + ", distanceFromCommercialCenter=" + distanceFromCommercialCenter + ", demandPotential=" + demandPotential + ", powerPlant=" + powerPlant + ", medicineIndustry=" + medicineIndustry + ", steelIndustry=" + steelIndustry + ", filthyLake=" + filthyLake + ", lowLyingArea=" + lowLyingArea + ", dumpYard=" + dumpYard + ", stp=" + stp + ", imageUrl=" + imageUrl + '}';
    }
    
}