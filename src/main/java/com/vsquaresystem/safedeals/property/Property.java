/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.property;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author hp-pc
 */
public class Property {

    private Integer id;
    private String name;
    private Integer cityId;
    private Integer locationId;
    private PropertyType propertyType;
    private Integer propertySize;
    private double size;
    private double priceRange;
    private Date buildingAge;
    private Integer totalFloors;
    private EntryFacing entryFacing;
    private BuildingCondition buildingCondition;
    private Integer projectId;
    private Integer majorApproachRoad;
    private List<Integer> publicTransport;
    private Double offeredPrice;
    private Double discount;
    private Date offerValidTill;
    private String paymentSchedule;
    private List<Integer> workplaces;
    private List<Integer> projectsNearby;
    private List<Integer> basicAmenities;
    private List<Integer> luxuryAmenities;
    private List<String> ownershipProof;
    private List<Integer> approvedBanks;
    private Boolean sdVerified;
    private List<Integer> privateAmenities;
    private Boolean sellerCommisionAgreement;
    private Double salableArea;
    private Double carpetArea;
    private Double buildUpArea;
    private Integer balconyCount;
    private Integer toiletCount;
    private Boolean openTerrace;
    private Boolean openLand;
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

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public PropertyType getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(PropertyType propertyType) {
        this.propertyType = propertyType;
    }

    public Integer getPropertySize() {
        return propertySize;
    }

    public void setPropertySize(Integer propertySize) {
        this.propertySize = propertySize;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public double getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(double priceRange) {
        this.priceRange = priceRange;
    }

    public Date getBuildingAge() {
        return buildingAge;
    }

    public void setBuildingAge(Date buildingAge) {
        this.buildingAge = buildingAge;
    }

    public Integer getTotalFloors() {
        return totalFloors;
    }

    public void setTotalFloors(Integer totalFloors) {
        this.totalFloors = totalFloors;
    }

    public EntryFacing getEntryFacing() {
        return entryFacing;
    }

    public void setEntryFacing(EntryFacing entryFacing) {
        this.entryFacing = entryFacing;
    }

    public BuildingCondition getBuildingCondition() {
        return buildingCondition;
    }

    public void setBuildingCondition(BuildingCondition buildingCondition) {
        this.buildingCondition = buildingCondition;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getMajorApproachRoad() {
        return majorApproachRoad;
    }

    public void setMajorApproachRoad(Integer majorApproachRoad) {
        this.majorApproachRoad = majorApproachRoad;
    }

    public List<Integer> getPublicTransport() {
        return publicTransport;
    }

    public void setPublicTransport(List<Integer> publicTransport) {
        this.publicTransport = publicTransport;
    }

    public Double getOfferedPrice() {
        return offeredPrice;
    }

    public void setOfferedPrice(Double offeredPrice) {
        this.offeredPrice = offeredPrice;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Date getOfferValidTill() {
        return offerValidTill;
    }

    public void setOfferValidTill(Date offerValidTill) {
        this.offerValidTill = offerValidTill;
    }

    public String getPaymentSchedule() {
        return paymentSchedule;
    }

    public void setPaymentSchedule(String paymentSchedule) {
        this.paymentSchedule = paymentSchedule;
    }

    public List<Integer> getWorkplaces() {
        return workplaces;
    }

    public void setWorkplaces(List<Integer> workplaces) {
        this.workplaces = workplaces;
    }

    public List<Integer> getProjectsNearby() {
        return projectsNearby;
    }

    public void setProjectsNearby(List<Integer> projectsNearby) {
        this.projectsNearby = projectsNearby;
    }

    public List<Integer> getBasicAmenities() {
        return basicAmenities;
    }

    public void setBasicAmenities(List<Integer> basicAmenities) {
        this.basicAmenities = basicAmenities;
    }

    public List<Integer> getLuxuryAmenities() {
        return luxuryAmenities;
    }

    public void setLuxuryAmenities(List<Integer> luxuryAmenities) {
        this.luxuryAmenities = luxuryAmenities;
    }

    public List<String> getOwnershipProof() {
        return ownershipProof;
    }

    public void setOwnershipProof(List<String> ownershipProof) {
        this.ownershipProof = ownershipProof;
    }

    public List<Integer> getApprovedBanks() {
        return approvedBanks;
    }

    public void setApprovedBanks(List<Integer> approvedBanks) {
        this.approvedBanks = approvedBanks;
    }

    public Boolean getSdVerified() {
        return sdVerified;
    }

    public void setSdVerified(Boolean sdVerified) {
        this.sdVerified = sdVerified;
    }

    public List<Integer> getPrivateAmenities() {
        return privateAmenities;
    }

    public void setPrivateAmenities(List<Integer> privateAmenities) {
        this.privateAmenities = privateAmenities;
    }

    public Boolean getSellerCommisionAgreement() {
        return sellerCommisionAgreement;
    }

    public void setSellerCommisionAgreement(Boolean sellerCommisionAgreement) {
        this.sellerCommisionAgreement = sellerCommisionAgreement;
    }

    public Double getSalableArea() {
        return salableArea;
    }

    public void setSalableArea(Double salableArea) {
        this.salableArea = salableArea;
    }

    public Double getCarpetArea() {
        return carpetArea;
    }

    public void setCarpetArea(Double carpetArea) {
        this.carpetArea = carpetArea;
    }

    public Double getBuildUpArea() {
        return buildUpArea;
    }

    public void setBuildUpArea(Double buildUpArea) {
        this.buildUpArea = buildUpArea;
    }

    public Integer getBalconyCount() {
        return balconyCount;
    }

    public void setBalconyCount(Integer balconyCount) {
        this.balconyCount = balconyCount;
    }

    public Integer getToiletCount() {
        return toiletCount;
    }

    public void setToiletCount(Integer toiletCount) {
        this.toiletCount = toiletCount;
    }

    public Boolean getOpenTerrace() {
        return openTerrace;
    }

    public void setOpenTerrace(Boolean openTerrace) {
        this.openTerrace = openTerrace;
    }

    public Boolean getOpenLand() {
        return openLand;
    }

    public void setOpenLand(Boolean openLand) {
        this.openLand = openLand;
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
        hash = 59 * hash + Objects.hashCode(this.id);
        hash = 59 * hash + Objects.hashCode(this.name);
        hash = 59 * hash + Objects.hashCode(this.cityId);
        hash = 59 * hash + Objects.hashCode(this.locationId);
        hash = 59 * hash + Objects.hashCode(this.propertyType);
        hash = 59 * hash + Objects.hashCode(this.propertySize);
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.size) ^ (Double.doubleToLongBits(this.size) >>> 32));
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.priceRange) ^ (Double.doubleToLongBits(this.priceRange) >>> 32));
        hash = 59 * hash + Objects.hashCode(this.buildingAge);
        hash = 59 * hash + Objects.hashCode(this.totalFloors);
        hash = 59 * hash + Objects.hashCode(this.entryFacing);
        hash = 59 * hash + Objects.hashCode(this.buildingCondition);
        hash = 59 * hash + Objects.hashCode(this.projectId);
        hash = 59 * hash + Objects.hashCode(this.majorApproachRoad);
        hash = 59 * hash + Objects.hashCode(this.publicTransport);
        hash = 59 * hash + Objects.hashCode(this.offeredPrice);
        hash = 59 * hash + Objects.hashCode(this.discount);
        hash = 59 * hash + Objects.hashCode(this.offerValidTill);
        hash = 59 * hash + Objects.hashCode(this.paymentSchedule);
        hash = 59 * hash + Objects.hashCode(this.workplaces);
        hash = 59 * hash + Objects.hashCode(this.projectsNearby);
        hash = 59 * hash + Objects.hashCode(this.basicAmenities);
        hash = 59 * hash + Objects.hashCode(this.luxuryAmenities);
        hash = 59 * hash + Objects.hashCode(this.ownershipProof);
        hash = 59 * hash + Objects.hashCode(this.approvedBanks);
        hash = 59 * hash + Objects.hashCode(this.sdVerified);
        hash = 59 * hash + Objects.hashCode(this.privateAmenities);
        hash = 59 * hash + Objects.hashCode(this.sellerCommisionAgreement);
        hash = 59 * hash + Objects.hashCode(this.salableArea);
        hash = 59 * hash + Objects.hashCode(this.carpetArea);
        hash = 59 * hash + Objects.hashCode(this.buildUpArea);
        hash = 59 * hash + Objects.hashCode(this.balconyCount);
        hash = 59 * hash + Objects.hashCode(this.toiletCount);
        hash = 59 * hash + Objects.hashCode(this.openTerrace);
        hash = 59 * hash + Objects.hashCode(this.openLand);
        hash = 59 * hash + Objects.hashCode(this.latitude);
        hash = 59 * hash + Objects.hashCode(this.longitude);
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
        if (!Objects.equals(this.cityId, other.cityId)) {
            return false;
        }
        if (!Objects.equals(this.locationId, other.locationId)) {
            return false;
        }
        if (this.propertyType != other.propertyType) {
            return false;
        }
        if (!Objects.equals(this.propertySize, other.propertySize)) {
            return false;
        }
        if (Double.doubleToLongBits(this.size) != Double.doubleToLongBits(other.size)) {
            return false;
        }
        if (Double.doubleToLongBits(this.priceRange) != Double.doubleToLongBits(other.priceRange)) {
            return false;
        }
        if (!Objects.equals(this.buildingAge, other.buildingAge)) {
            return false;
        }
        if (!Objects.equals(this.totalFloors, other.totalFloors)) {
            return false;
        }
        if (this.entryFacing != other.entryFacing) {
            return false;
        }
        if (this.buildingCondition != other.buildingCondition) {
            return false;
        }
        if (!Objects.equals(this.projectId, other.projectId)) {
            return false;
        }
        if (!Objects.equals(this.majorApproachRoad, other.majorApproachRoad)) {
            return false;
        }
        if (!Objects.equals(this.publicTransport, other.publicTransport)) {
            return false;
        }
        if (!Objects.equals(this.offeredPrice, other.offeredPrice)) {
            return false;
        }
        if (!Objects.equals(this.discount, other.discount)) {
            return false;
        }
        if (!Objects.equals(this.offerValidTill, other.offerValidTill)) {
            return false;
        }
        if (!Objects.equals(this.paymentSchedule, other.paymentSchedule)) {
            return false;
        }
        if (!Objects.equals(this.workplaces, other.workplaces)) {
            return false;
        }
        if (!Objects.equals(this.projectsNearby, other.projectsNearby)) {
            return false;
        }
        if (!Objects.equals(this.basicAmenities, other.basicAmenities)) {
            return false;
        }
        if (!Objects.equals(this.luxuryAmenities, other.luxuryAmenities)) {
            return false;
        }
        if (!Objects.equals(this.ownershipProof, other.ownershipProof)) {
            return false;
        }
        if (!Objects.equals(this.approvedBanks, other.approvedBanks)) {
            return false;
        }
        if (!Objects.equals(this.sdVerified, other.sdVerified)) {
            return false;
        }
        if (!Objects.equals(this.privateAmenities, other.privateAmenities)) {
            return false;
        }
        if (!Objects.equals(this.sellerCommisionAgreement, other.sellerCommisionAgreement)) {
            return false;
        }
        if (!Objects.equals(this.salableArea, other.salableArea)) {
            return false;
        }
        if (!Objects.equals(this.carpetArea, other.carpetArea)) {
            return false;
        }
        if (!Objects.equals(this.buildUpArea, other.buildUpArea)) {
            return false;
        }
        if (!Objects.equals(this.balconyCount, other.balconyCount)) {
            return false;
        }
        if (!Objects.equals(this.toiletCount, other.toiletCount)) {
            return false;
        }
        if (!Objects.equals(this.openTerrace, other.openTerrace)) {
            return false;
        }
        if (!Objects.equals(this.openLand, other.openLand)) {
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
        return "Property{" + "id=" + id + ", name=" + name + ", cityId=" + cityId + ", locationId=" + locationId + ", propertyType=" + propertyType + ", propertySize=" + propertySize + ", size=" + size + ", priceRange=" + priceRange + ", buildingAge=" + buildingAge + ", totalFloors=" + totalFloors + ", entryFacing=" + entryFacing + ", buildingCondition=" + buildingCondition + ", projectId=" + projectId + ", majorApproachRoad=" + majorApproachRoad + ", publicTransport=" + publicTransport + ", offeredPrice=" + offeredPrice + ", discount=" + discount + ", offerValidTill=" + offerValidTill + ", paymentSchedule=" + paymentSchedule + ", workplaces=" + workplaces + ", projectsNearby=" + projectsNearby + ", basicAmenities=" + basicAmenities + ", luxuryAmenities=" + luxuryAmenities + ", ownershipProof=" + ownershipProof + ", approvedBanks=" + approvedBanks + ", sdVerified=" + sdVerified + ", privateAmenities=" + privateAmenities + ", sellerCommisionAgreement=" + sellerCommisionAgreement + ", salableArea=" + salableArea + ", carpetArea=" + carpetArea + ", buildUpArea=" + buildUpArea + ", balconyCount=" + balconyCount + ", toiletCount=" + toiletCount + ", openTerrace=" + openTerrace + ", openLand=" + openLand + ", latitude=" + latitude + ", longitude=" + longitude + '}';
    }

    
}
