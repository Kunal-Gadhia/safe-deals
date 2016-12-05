
package com.vsquaresystem.safedeals.readyreckoner;

import java.util.Objects;

public class Readyreckoner {
    private Integer id;
    private Integer locationId;
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

    @Override
    public String toString() {
        return "Readyreckoner{" + "id=" + id + ", locationId=" + locationId + ", rrYear=" + rrYear + ", rrRateLand=" + rrRateLand + ", rrRatePlot=" + rrRatePlot + ", rrRateApartment=" + rrRateApartment + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.id);
        hash = 67 * hash + Objects.hashCode(this.locationId);
        hash = 67 * hash + Objects.hashCode(this.rrYear);
        hash = 67 * hash + Objects.hashCode(this.rrRateLand);
        hash = 67 * hash + Objects.hashCode(this.rrRatePlot);
        hash = 67 * hash + Objects.hashCode(this.rrRateApartment);
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
        return true;
    }

    
    
}
