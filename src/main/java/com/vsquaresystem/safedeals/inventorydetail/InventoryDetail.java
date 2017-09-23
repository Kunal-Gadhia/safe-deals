/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.inventorydetail;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author hp
 */
public class InventoryDetail {

    private Integer id;
    private Integer inventoryHeadId;
    private String description;
    private Facing facing;
    private String floorNo;
    private Integer noOfBalcony;
    private Integer noOfWashroom;
    private Boolean openTerrace;
    private Boolean openLand;
    private Boolean isAvailable;
    private Boolean isReserved;
    private Boolean isSold;
    private Double offerAmount;
    private Date validity;
    private Double extraCharges;
    private Integer unitNo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getInventoryHeadId() {
        return inventoryHeadId;
    }

    public void setInventoryHeadId(Integer inventoryHeadId) {
        this.inventoryHeadId = inventoryHeadId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Facing getFacing() {
        return facing;
    }

    public void setFacing(Facing facing) {
        this.facing = facing;
    }

    public String getFloorNo() {
        return floorNo;
    }

    public void setFloorNo(String floorNo) {
        this.floorNo = floorNo;
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

    public Boolean getOpenLand() {
        return openLand;
    }

    public void setOpenLand(Boolean openLand) {
        this.openLand = openLand;
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

    public Double getOfferAmount() {
        return offerAmount;
    }

    public void setOfferAmount(Double offerAmount) {
        this.offerAmount = offerAmount;
    }

    public Date getValidity() {
        return validity;
    }

    public void setValidity(Date validity) {
        this.validity = validity;
    }

    public Double getExtraCharges() {
        return extraCharges;
    }

    public void setExtraCharges(Double extraCharges) {
        this.extraCharges = extraCharges;
    }

    public Integer getUnitNo() {
        return unitNo;
    }

    public void setUnitNo(Integer unitNo) {
        this.unitNo = unitNo;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.id);
        hash = 41 * hash + Objects.hashCode(this.inventoryHeadId);
        hash = 41 * hash + Objects.hashCode(this.description);
        hash = 41 * hash + Objects.hashCode(this.facing);
        hash = 41 * hash + Objects.hashCode(this.floorNo);
        hash = 41 * hash + Objects.hashCode(this.noOfBalcony);
        hash = 41 * hash + Objects.hashCode(this.noOfWashroom);
        hash = 41 * hash + Objects.hashCode(this.openTerrace);
        hash = 41 * hash + Objects.hashCode(this.openLand);
        hash = 41 * hash + Objects.hashCode(this.isAvailable);
        hash = 41 * hash + Objects.hashCode(this.isReserved);
        hash = 41 * hash + Objects.hashCode(this.isSold);
        hash = 41 * hash + Objects.hashCode(this.offerAmount);
        hash = 41 * hash + Objects.hashCode(this.validity);
        hash = 41 * hash + Objects.hashCode(this.extraCharges);
        hash = 41 * hash + Objects.hashCode(this.unitNo);
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
        final InventoryDetail other = (InventoryDetail) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.inventoryHeadId, other.inventoryHeadId)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (this.facing != other.facing) {
            return false;
        }
        if (!Objects.equals(this.floorNo, other.floorNo)) {
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
        if (!Objects.equals(this.openLand, other.openLand)) {
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
        if (!Objects.equals(this.offerAmount, other.offerAmount)) {
            return false;
        }
        if (!Objects.equals(this.validity, other.validity)) {
            return false;
        }
        if (!Objects.equals(this.extraCharges, other.extraCharges)) {
            return false;
        }
        if (!Objects.equals(this.unitNo, other.unitNo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "InventoryDetail{" + "id=" + id + ", inventoryHeadId=" + inventoryHeadId + ", description=" + description + ", facing=" + facing + ", floorNo=" + floorNo + ", noOfBalcony=" + noOfBalcony + ", noOfWashroom=" + noOfWashroom + ", openTerrace=" + openTerrace + ", openLand=" + openLand + ", isAvailable=" + isAvailable + ", isReserved=" + isReserved + ", isSold=" + isSold + ", offerAmount=" + offerAmount + ", validity=" + validity + ", extraCharges=" + extraCharges + ", unitNo=" + unitNo + '}';
    }

}
