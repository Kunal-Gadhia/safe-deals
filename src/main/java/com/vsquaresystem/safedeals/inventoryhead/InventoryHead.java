package com.vsquaresystem.safedeals.inventoryhead;

import java.util.Objects;

public class InventoryHead {

    private Integer id;
    private Integer projectId;
    private Integer noOfBhk;
    private Integer propertyCategoryId;
    private Double totalArea;
    private Double pricePerSqft;
    private Double offeredPrice;
    private String buildingName;
    private Integer totalUnits;

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

    public Double getTotalArea() {
        return totalArea;
    }

    public void setTotalArea(Double totalArea) {
        this.totalArea = totalArea;
    }

    public Double getPricePerSqft() {
        return pricePerSqft;
    }

    public void setPricePerSqft(Double pricePerSqft) {
        this.pricePerSqft = pricePerSqft;
    }

    public Double getOfferedPrice() {
        return offeredPrice;
    }

    public void setOfferedPrice(Double offeredPrice) {
        this.offeredPrice = offeredPrice;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public Integer getTotalUnits() {
        return totalUnits;
    }

    public void setTotalUnits(Integer totalUnits) {
        this.totalUnits = totalUnits;
    }

    @Override
    public String toString() {
        return "InventoryHead{" + "id=" + id + ", projectId=" + projectId + ", noOfBhk=" + noOfBhk + ", propertyCategoryId=" + propertyCategoryId + ", totalArea=" + totalArea + ", pricePerSqft=" + pricePerSqft + ", offeredPrice=" + offeredPrice + ", buildingName=" + buildingName + ", totalUnits=" + totalUnits + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.id);
        hash = 23 * hash + Objects.hashCode(this.projectId);
        hash = 23 * hash + Objects.hashCode(this.noOfBhk);
        hash = 23 * hash + Objects.hashCode(this.propertyCategoryId);
        hash = 23 * hash + Objects.hashCode(this.totalArea);
        hash = 23 * hash + Objects.hashCode(this.pricePerSqft);
        hash = 23 * hash + Objects.hashCode(this.offeredPrice);
        hash = 23 * hash + Objects.hashCode(this.buildingName);
        hash = 23 * hash + Objects.hashCode(this.totalUnits);
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
        final InventoryHead other = (InventoryHead) obj;
        if (!Objects.equals(this.buildingName, other.buildingName)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.projectId, other.projectId)) {
            return false;
        }
        if (!Objects.equals(this.noOfBhk, other.noOfBhk)) {
            return false;
        }
        if (!Objects.equals(this.propertyCategoryId, other.propertyCategoryId)) {
            return false;
        }
        if (!Objects.equals(this.totalArea, other.totalArea)) {
            return false;
        }
        if (!Objects.equals(this.pricePerSqft, other.pricePerSqft)) {
            return false;
        }
        if (!Objects.equals(this.offeredPrice, other.offeredPrice)) {
            return false;
        }
        if (!Objects.equals(this.totalUnits, other.totalUnits)) {
            return false;
        }
        return true;
    }

}
