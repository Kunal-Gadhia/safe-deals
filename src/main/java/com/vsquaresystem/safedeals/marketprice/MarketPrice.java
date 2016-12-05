package com.vsquaresystem.safedeals.marketprice;

import java.util.Objects;

public class MarketPrice {

    private Integer id;
    private Integer locationId;
    private Integer cityId;
    private Integer year;
    private Integer month;
    private double mpAgriLandLowest;
    private double mpAgriLandHighest;
    private double mpAgriLandAverage;
    private double mpPlotLowest;
    private double mpPlotHighest;
    private double mpPlotAverage;
    private double mpResidentialLowest;
    private double mpResidentialHighest;
    private double mpResidentialAverage;
    private double mpCommercialLowest;
    private double mpCommercialHighest;
    private double mpCommercialAverage;

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

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
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

    public double getMpAgriLandLowest() {
        return mpAgriLandLowest;
    }

    public void setMpAgriLandLowest(double mpAgriLandLowest) {
        this.mpAgriLandLowest = mpAgriLandLowest;
    }

    public double getMpAgriLandHighest() {
        return mpAgriLandHighest;
    }

    public void setMpAgriLandHighest(double mpAgriLandHighest) {
        this.mpAgriLandHighest = mpAgriLandHighest;
    }

    public double getMpAgriLandAverage() {
        return mpAgriLandAverage;
    }

    public void setMpAgriLandAverage(double mpAgriLandAverage) {
        this.mpAgriLandAverage = mpAgriLandAverage;
    }

    public double getMpPlotLowest() {
        return mpPlotLowest;
    }

    public void setMpPlotLowest(double mpPlotLowest) {
        this.mpPlotLowest = mpPlotLowest;
    }

    public double getMpPlotHighest() {
        return mpPlotHighest;
    }

    public void setMpPlotHighest(double mpPlotHighest) {
        this.mpPlotHighest = mpPlotHighest;
    }

    public double getMpPlotAverage() {
        return mpPlotAverage;
    }

    public void setMpPlotAverage(double mpPlotAverage) {
        this.mpPlotAverage = mpPlotAverage;
    }

    public double getMpResidentialLowest() {
        return mpResidentialLowest;
    }

    public void setMpResidentialLowest(double mpResidentialLowest) {
        this.mpResidentialLowest = mpResidentialLowest;
    }

    public double getMpResidentialHighest() {
        return mpResidentialHighest;
    }

    public void setMpResidentialHighest(double mpResidentialHighest) {
        this.mpResidentialHighest = mpResidentialHighest;
    }

    public double getMpResidentialAverage() {
        return mpResidentialAverage;
    }

    public void setMpResidentialAverage(double mpResidentialAverage) {
        this.mpResidentialAverage = mpResidentialAverage;
    }

    public double getMpCommercialLowest() {
        return mpCommercialLowest;
    }

    public void setMpCommercialLowest(double mpCommercialLowest) {
        this.mpCommercialLowest = mpCommercialLowest;
    }

    public double getMpCommercialHighest() {
        return mpCommercialHighest;
    }

    public void setMpCommercialHighest(double mpCommercialHighest) {
        this.mpCommercialHighest = mpCommercialHighest;
    }

    public double getMpCommercialAverage() {
        return mpCommercialAverage;
    }

    public void setMpCommercialAverage(double mpCommercialAverage) {
        this.mpCommercialAverage = mpCommercialAverage;
    }

    @Override
    public String toString() {
        return "MarketPrice{" + "id=" + id + ", locationId=" + locationId + ", cityId=" + cityId + ", year=" + year + ", month=" + month + ", mpAgriLandLowest=" + mpAgriLandLowest + ", mpAgriLandHighest=" + mpAgriLandHighest + ", mpAgriLandAverage=" + mpAgriLandAverage + ", mpPlotLowest=" + mpPlotLowest + ", mpPlotHighest=" + mpPlotHighest + ", mpPlotAverage=" + mpPlotAverage + ", mpResidentialLowest=" + mpResidentialLowest + ", mpResidentialHighest=" + mpResidentialHighest + ", mpResidentialAverage=" + mpResidentialAverage + ", mpCommercialLowest=" + mpCommercialLowest + ", mpCommercialHighest=" + mpCommercialHighest + ", mpCommercialAverage=" + mpCommercialAverage + '}';
    }

    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.id);
        hash = 79 * hash + Objects.hashCode(this.locationId);
        hash = 79 * hash + Objects.hashCode(this.cityId);
        hash = 79 * hash + Objects.hashCode(this.year);
        hash = 79 * hash + Objects.hashCode(this.month);
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.mpAgriLandLowest) ^ (Double.doubleToLongBits(this.mpAgriLandLowest) >>> 32));
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.mpAgriLandHighest) ^ (Double.doubleToLongBits(this.mpAgriLandHighest) >>> 32));
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.mpAgriLandAverage) ^ (Double.doubleToLongBits(this.mpAgriLandAverage) >>> 32));
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.mpPlotLowest) ^ (Double.doubleToLongBits(this.mpPlotLowest) >>> 32));
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.mpPlotHighest) ^ (Double.doubleToLongBits(this.mpPlotHighest) >>> 32));
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.mpPlotAverage) ^ (Double.doubleToLongBits(this.mpPlotAverage) >>> 32));
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.mpResidentialLowest) ^ (Double.doubleToLongBits(this.mpResidentialLowest) >>> 32));
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.mpResidentialHighest) ^ (Double.doubleToLongBits(this.mpResidentialHighest) >>> 32));
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.mpResidentialAverage) ^ (Double.doubleToLongBits(this.mpResidentialAverage) >>> 32));
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.mpCommercialLowest) ^ (Double.doubleToLongBits(this.mpCommercialLowest) >>> 32));
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.mpCommercialHighest) ^ (Double.doubleToLongBits(this.mpCommercialHighest) >>> 32));
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.mpCommercialAverage) ^ (Double.doubleToLongBits(this.mpCommercialAverage) >>> 32));
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
        final MarketPrice other = (MarketPrice) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.locationId, other.locationId)) {
            return false;
        }
        if (!Objects.equals(this.cityId, other.cityId)) {
            return false;
        }
        if (!Objects.equals(this.year, other.year)) {
            return false;
        }
        if (!Objects.equals(this.month, other.month)) {
            return false;
        }
        if (Double.doubleToLongBits(this.mpAgriLandLowest) != Double.doubleToLongBits(other.mpAgriLandLowest)) {
            return false;
        }
        if (Double.doubleToLongBits(this.mpAgriLandHighest) != Double.doubleToLongBits(other.mpAgriLandHighest)) {
            return false;
        }
        if (Double.doubleToLongBits(this.mpAgriLandAverage) != Double.doubleToLongBits(other.mpAgriLandAverage)) {
            return false;
        }
        if (Double.doubleToLongBits(this.mpPlotLowest) != Double.doubleToLongBits(other.mpPlotLowest)) {
            return false;
        }
        if (Double.doubleToLongBits(this.mpPlotHighest) != Double.doubleToLongBits(other.mpPlotHighest)) {
            return false;
        }
        if (Double.doubleToLongBits(this.mpPlotAverage) != Double.doubleToLongBits(other.mpPlotAverage)) {
            return false;
        }
        if (Double.doubleToLongBits(this.mpResidentialLowest) != Double.doubleToLongBits(other.mpResidentialLowest)) {
            return false;
        }
        if (Double.doubleToLongBits(this.mpResidentialHighest) != Double.doubleToLongBits(other.mpResidentialHighest)) {
            return false;
        }
        if (Double.doubleToLongBits(this.mpResidentialAverage) != Double.doubleToLongBits(other.mpResidentialAverage)) {
            return false;
        }
        if (Double.doubleToLongBits(this.mpCommercialLowest) != Double.doubleToLongBits(other.mpCommercialLowest)) {
            return false;
        }
        if (Double.doubleToLongBits(this.mpCommercialHighest) != Double.doubleToLongBits(other.mpCommercialHighest)) {
            return false;
        }
        if (Double.doubleToLongBits(this.mpCommercialAverage) != Double.doubleToLongBits(other.mpCommercialAverage)) {
            return false;
        }
        return true;
    }

    
}
