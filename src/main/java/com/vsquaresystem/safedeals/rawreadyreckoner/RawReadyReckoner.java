package com.vsquaresystem.safedeals.rawreadyreckoner;

import java.util.List;
import java.util.Objects;

public class RawReadyReckoner {

    private Integer id;
    private Integer cityId;
    private String location;
    private Integer safedealZoneId;
    private Integer locationTypeId;
    private List<Integer> locationCategories;
    private Double rrYear;
    private Double rrRateLand;
    private Double rrRatePlot;
    private Double rrRateApartment;

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    @Override
    public String toString() {
        return "RawReadyReckoner{" + "id=" + id + ", cityId=" + cityId + ", location=" + location + ", safedealZoneId=" + safedealZoneId + ", locationTypeId=" + locationTypeId + ", locationCategories=" + locationCategories + ", rrYear=" + rrYear + ", rrRateLand=" + rrRateLand + ", rrRatePlot=" + rrRatePlot + ", rrRateApartment=" + rrRateApartment + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.id);
        hash = 23 * hash + Objects.hashCode(this.cityId);
        hash = 23 * hash + Objects.hashCode(this.location);
        hash = 23 * hash + Objects.hashCode(this.safedealZoneId);
        hash = 23 * hash + Objects.hashCode(this.locationTypeId);
        hash = 23 * hash + Objects.hashCode(this.locationCategories);
        hash = 23 * hash + Objects.hashCode(this.rrYear);
        hash = 23 * hash + Objects.hashCode(this.rrRateLand);
        hash = 23 * hash + Objects.hashCode(this.rrRatePlot);
        hash = 23 * hash + Objects.hashCode(this.rrRateApartment);
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
        final RawReadyReckoner other = (RawReadyReckoner) obj;
        if (!Objects.equals(this.location, other.location)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.cityId, other.cityId)) {
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
        return true;
    }

    
    
}
