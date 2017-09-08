package com.vsquaresystem.safedeals.inventory;

import java.util.Objects;

public class Inventory {

    private Integer id;
    private Integer projectId;
    private Integer noOfBhk;
    private Integer propertyCategoryId;
    private Integer unitNo;
    private Integer floorNo;
    private String buildingName;
    private Double pricePerSqft;
    private Double totalArea;
    private Double offeredPrice;
    private Integer noOfBalcony;
    private Integer noOfWashroom;
    private Boolean openTerrace;
    private Boolean isAvailable;
    private Boolean isReserved;
    private Boolean isSold;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getNoOfBhk() {
        return noOfBhk;
    }

    public void setNoOfBhk(Integer noOfBhk) {
        this.noOfBhk = noOfBhk;
    }

    public Integer getPropertyCategoryId() {
        return propertyCategoryId;
    }

    public void setPropertyCategoryId(Integer propertyCategoryId) {
        this.propertyCategoryId = propertyCategoryId;
    }

    public Integer getUnitNo() {
        return unitNo;
    }

    public void setUnitNo(Integer unitNo) {
        this.unitNo = unitNo;
    }

    public Integer getFloorNo() {
        return floorNo;
    }

    public void setFloorNo(Integer floorNo) {
        this.floorNo = floorNo;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public Double getPricePerSqft() {
        return pricePerSqft;
    }

    public void setPricePerSqft(Double pricePerSqft) {
        this.pricePerSqft = pricePerSqft;
    }

    public Double getTotalArea() {
        return totalArea;
    }

    public void setTotalArea(Double totalArea) {
        this.totalArea = totalArea;
    }

    public Double getOfferedPrice() {
        return offeredPrice;
    }

    public void setOfferedPrice(Double offeredPrice) {
        this.offeredPrice = offeredPrice;
    }

    public Integer getNoOfBalcony() {
        return noOfBalcony;
    }

    public void setNoOfBalcony(Integer noOfBalcony) {
        this.noOfBalcony = noOfBalcony;
    }

    public Integer getNoOfWashroom() {
        return noOfWashroom;
    }

    public void setNoOfWashroom(Integer noOfWashroom) {
        this.noOfWashroom = noOfWashroom;
    }

    public Boolean getOpenTerrace() {
        return openTerrace;
    }

    public void setOpenTerrace(Boolean openTerrace) {
        this.openTerrace = openTerrace;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public Boolean getIsReserved() {
        return isReserved;
    }

    public void setIsReserved(Boolean isReserved) {
        this.isReserved = isReserved;
    }

    public Boolean getIsSold() {
        return isSold;
    }

    public void setIsSold(Boolean isSold) {
        this.isSold = isSold;
    }

    @Override
    public String toString() {
        return "Inventory{" + "id=" + id + ", projectId=" + projectId + ", noOfBhk=" + noOfBhk + ", propertyCategoryId=" + propertyCategoryId + ", unitNo=" + unitNo + ", floorNo=" + floorNo + ", buildingName=" + buildingName + ", pricePerSqft=" + pricePerSqft + ", totalArea=" + totalArea + ", offeredPrice=" + offeredPrice + ", noOfBalcony=" + noOfBalcony + ", noOfWashroom=" + noOfWashroom + ", openTerrace=" + openTerrace + ", isAvailable=" + isAvailable + ", isReserved=" + isReserved + ", isSold=" + isSold + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.id);
        hash = 11 * hash + Objects.hashCode(this.projectId);
        hash = 11 * hash + Objects.hashCode(this.noOfBhk);
        hash = 11 * hash + Objects.hashCode(this.propertyCategoryId);
        hash = 11 * hash + Objects.hashCode(this.unitNo);
        hash = 11 * hash + Objects.hashCode(this.floorNo);
        hash = 11 * hash + Objects.hashCode(this.buildingName);
        hash = 11 * hash + Objects.hashCode(this.pricePerSqft);
        hash = 11 * hash + Objects.hashCode(this.totalArea);
        hash = 11 * hash + Objects.hashCode(this.offeredPrice);
        hash = 11 * hash + Objects.hashCode(this.noOfBalcony);
        hash = 11 * hash + Objects.hashCode(this.noOfWashroom);
        hash = 11 * hash + Objects.hashCode(this.openTerrace);
        hash = 11 * hash + Objects.hashCode(this.isAvailable);
        hash = 11 * hash + Objects.hashCode(this.isReserved);
        hash = 11 * hash + Objects.hashCode(this.isSold);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Inventory other = (Inventory) obj;
        if (!Objects.equals(this.noOfBhk, other.noOfBhk)) {
            return false;
        }
        if (!Objects.equals(this.buildingName, other.buildingName)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.projectId, other.projectId)) {
            return false;
        }
        if (!Objects.equals(this.propertyCategoryId, other.propertyCategoryId)) {
            return false;
        }

        if (!Objects.equals(this.unitNo, other.unitNo)) {
            return false;
        }
        if (!Objects.equals(this.floorNo, other.floorNo)) {
            return false;
        }
        if (!Objects.equals(this.pricePerSqft, other.pricePerSqft)) {
            return false;
        }
        if (!Objects.equals(this.totalArea, other.totalArea)) {
            return false;
        }
        if (!Objects.equals(this.offeredPrice, other.offeredPrice)) {
            return false;
        }
        if (!Objects.equals(this.noOfBalcony, other.noOfBalcony)) {
            return false;
        }
        if (!Objects.equals(this.noOfWashroom, other.noOfWashroom)) {
            return false;
        }
        if (!Objects.equals(this.openTerrace, other.openTerrace)) {
            return false;
        }
        if (!Objects.equals(this.isAvailable, other.isAvailable)) {
            return false;
        }
        if (!Objects.equals(this.isReserved, other.isReserved)) {
            return false;
        }
        if (!Objects.equals(this.isSold, other.isSold)) {
            return false;
        }
        return true;
    }

}
