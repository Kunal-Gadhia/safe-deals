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
    private ConstructionStage constructionStage;
    private Date possessionDate;
    private Date yearOfConstruction;
    private Integer floorNumber;
    private Integer totalFloors;
    private EntryFacing entryFacing;
//    private BuildingCondition buildingCondition;
    private Integer projectId;
    private Integer majorApproachRoad;
    private List<Integer> publicTransport;
    private Double offeredPrice;
    private Double discount;
    private Date offerValidTill;
    private String paymentSchedule;
    private Integer downpayment;
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

    public ConstructionStage getConstructionStage() {
        return constructionStage;
    }

    public void setConstructionStage(ConstructionStage constructionStage) {
        this.constructionStage = constructionStage;
    }

    public Date getPossessionDate() {
        return possessionDate;
    }

    public void setPossessionDate(Date possesionDate) {
        this.possessionDate = possesionDate;
    }

    public Date getYearOfConstruction() {
        return yearOfConstruction;
    }

    public void setYearOfConstruction(Date yearOfConstruction) {
        this.yearOfConstruction = yearOfConstruction;
    }

    public Integer getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(Integer floorNumber) {
        this.floorNumber = floorNumber;
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

    public Integer getDownpayment() {
        return downpayment;
    }

    public void setDownpayment(Integer downpayment) {
        this.downpayment = downpayment;
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
        hash = 29 * hash + Objects.hashCode(this.id);
        hash = 29 * hash + Objects.hashCode(this.name);
        hash = 29 * hash + Objects.hashCode(this.cityId);
        hash = 29 * hash + Objects.hashCode(this.locationId);
        hash = 29 * hash + Objects.hashCode(this.propertyType);
        hash = 29 * hash + Objects.hashCode(this.propertySize);
        hash = 29 * hash + (int) (Double.doubleToLongBits(this.size) ^ (Double.doubleToLongBits(this.size) >>> 32));
        hash = 29 * hash + (int) (Double.doubleToLongBits(this.priceRange) ^ (Double.doubleToLongBits(this.priceRange) >>> 32));
        hash = 29 * hash + Objects.hashCode(this.constructionStage);
        hash = 29 * hash + Objects.hashCode(this.possessionDate);
        hash = 29 * hash + Objects.hashCode(this.yearOfConstruction);
        hash = 29 * hash + Objects.hashCode(this.floorNumber);
        hash = 29 * hash + Objects.hashCode(this.totalFloors);
        hash = 29 * hash + Objects.hashCode(this.entryFacing);
        hash = 29 * hash + Objects.hashCode(this.projectId);
        hash = 29 * hash + Objects.hashCode(this.majorApproachRoad);
        hash = 29 * hash + Objects.hashCode(this.publicTransport);
        hash = 29 * hash + Objects.hashCode(this.offeredPrice);
        hash = 29 * hash + Objects.hashCode(this.discount);
        hash = 29 * hash + Objects.hashCode(this.offerValidTill);
        hash = 29 * hash + Objects.hashCode(this.paymentSchedule);
        hash = 29 * hash + Objects.hashCode(this.downpayment);
        hash = 29 * hash + Objects.hashCode(this.workplaces);
        hash = 29 * hash + Objects.hashCode(this.projectsNearby);
        hash = 29 * hash + Objects.hashCode(this.basicAmenities);
        hash = 29 * hash + Objects.hashCode(this.luxuryAmenities);
        hash = 29 * hash + Objects.hashCode(this.ownershipProof);
        hash = 29 * hash + Objects.hashCode(this.approvedBanks);
        hash = 29 * hash + Objects.hashCode(this.sdVerified);
        hash = 29 * hash + Objects.hashCode(this.privateAmenities);
        hash = 29 * hash + Objects.hashCode(this.sellerCommisionAgreement);
        hash = 29 * hash + Objects.hashCode(this.salableArea);
        hash = 29 * hash + Objects.hashCode(this.carpetArea);
        hash = 29 * hash + Objects.hashCode(this.buildUpArea);
        hash = 29 * hash + Objects.hashCode(this.balconyCount);
        hash = 29 * hash + Objects.hashCode(this.toiletCount);
        hash = 29 * hash + Objects.hashCode(this.openTerrace);
        hash = 29 * hash + Objects.hashCode(this.openLand);
        hash = 29 * hash + Objects.hashCode(this.latitude);
        hash = 29 * hash + Objects.hashCode(this.longitude);
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
        if (this.constructionStage != other.constructionStage) {
            return false;
        }
        if (!Objects.equals(this.possessionDate, other.possessionDate)) {
            return false;
        }
        if (!Objects.equals(this.yearOfConstruction, other.yearOfConstruction)) {
            return false;
        }
        if (!Objects.equals(this.floorNumber, other.floorNumber)) {
            return false;
        }
        if (!Objects.equals(this.totalFloors, other.totalFloors)) {
            return false;
        }
        if (this.entryFacing != other.entryFacing) {
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
        if (!Objects.equals(this.downpayment, other.downpayment)) {
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
        return "Property{" + "id=" + id + ", name=" + name + ", cityId=" + cityId + ", locationId=" + locationId + ", propertyType=" + propertyType + ", propertySize=" + propertySize + ", size=" + size + ", priceRange=" + priceRange + ", constructionStage=" + constructionStage + ", possessionDate=" + possessionDate + ", yearOfConstruction=" + yearOfConstruction + ", floorNumber=" + floorNumber + ", totalFloors=" + totalFloors + ", entryFacing=" + entryFacing + ", projectId=" + projectId + ", majorApproachRoad=" + majorApproachRoad + ", publicTransport=" + publicTransport + ", offeredPrice=" + offeredPrice + ", discount=" + discount + ", offerValidTill=" + offerValidTill + ", paymentSchedule=" + paymentSchedule + ", downpayment=" + downpayment + ", workplaces=" + workplaces + ", projectsNearby=" + projectsNearby + ", basicAmenities=" + basicAmenities + ", luxuryAmenities=" + luxuryAmenities + ", ownershipProof=" + ownershipProof + ", approvedBanks=" + approvedBanks + ", sdVerified=" + sdVerified + ", privateAmenities=" + privateAmenities + ", sellerCommisionAgreement=" + sellerCommisionAgreement + ", salableArea=" + salableArea + ", carpetArea=" + carpetArea + ", buildUpArea=" + buildUpArea + ", balconyCount=" + balconyCount + ", toiletCount=" + toiletCount + ", openTerrace=" + openTerrace + ", openLand=" + openLand + ", latitude=" + latitude + ", longitude=" + longitude + '}';
    }

    
}
