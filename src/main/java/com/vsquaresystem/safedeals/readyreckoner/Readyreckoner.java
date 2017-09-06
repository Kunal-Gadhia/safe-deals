package com.vsquaresystem.safedeals.readyreckoner;

import java.util.Date;
import java.util.Objects;

public class Readyreckoner {

    private Integer id;
    private Integer locationId;
    private Double rrYear;
    private Double rrRateLand;
    private Double rrRatePlot;
    private Double rrRateApartment;
    private Integer userId;
    private Date lastUpdatedTimeStamp;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public Double getRrYear() {
        return rrYear;
    }

    public void setRrYear(Double rrYear) {
        this.rrYear = rrYear;
    }

    public Double getRrRateLand() {
        return rrRateLand;
    }

    public void setRrRateLand(Double rrRateLand) {
        this.rrRateLand = rrRateLand;
    }

    public Double getRrRatePlot() {
        return rrRatePlot;
    }

    public void setRrRatePlot(Double rrRatePlot) {
        this.rrRatePlot = rrRatePlot;
    }

    public Double getRrRateApartment() {
        return rrRateApartment;
    }

    public void setRrRateApartment(Double rrRateApartment) {
        this.rrRateApartment = rrRateApartment;
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
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.id);
        hash = 17 * hash + Objects.hashCode(this.locationId);
        hash = 17 * hash + Objects.hashCode(this.rrYear);
        hash = 17 * hash + Objects.hashCode(this.rrRateLand);
        hash = 17 * hash + Objects.hashCode(this.rrRatePlot);
        hash = 17 * hash + Objects.hashCode(this.rrRateApartment);
        hash = 17 * hash + Objects.hashCode(this.userId);
        hash = 17 * hash + Objects.hashCode(this.lastUpdatedTimeStamp);
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
        final Readyreckoner other = (Readyreckoner) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.locationId, other.locationId)) {
            return false;
        }
        if (!Objects.equals(this.rrYear, other.rrYear)) {
            return false;
        }
        if (!Objects.equals(this.rrRateLand, other.rrRateLand)) {
            return false;
        }
        if (!Objects.equals(this.rrRatePlot, other.rrRatePlot)) {
            return false;
        }
        if (!Objects.equals(this.rrRateApartment, other.rrRateApartment)) {
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
        return "Readyreckoner{" + "id=" + id + ", locationId=" + locationId + ", rrYear=" + rrYear + ", rrRateLand=" + rrRateLand + ", rrRatePlot=" + rrRatePlot + ", rrRateApartment=" + rrRateApartment + ", userId=" + userId + ", lastUpdatedTimeStamp=" + lastUpdatedTimeStamp + '}';
    }

}
