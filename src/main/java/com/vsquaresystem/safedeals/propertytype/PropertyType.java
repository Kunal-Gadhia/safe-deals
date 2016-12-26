package com.vsquaresystem.safedeals.propertytype;

import java.util.Objects;

public class PropertyType {
    private Integer id;
    private String numberOfBhk;
    private Integer carpetArea;
    private String priceRange;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumberOfBhk() {
        return numberOfBhk;
    }

    public void setNumberOfBhk(String numberOfBhk) {
        this.numberOfBhk = numberOfBhk;
    }

    public Integer getCarpetArea() {
        return carpetArea;
    }

    public void setCarpetArea(Integer carpetArea) {
        this.carpetArea = carpetArea;
    }

    public String getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(String priceRange) {
        this.priceRange = priceRange;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.id);
        hash = 47 * hash + Objects.hashCode(this.numberOfBhk);
        hash = 47 * hash + Objects.hashCode(this.carpetArea);
        hash = 47 * hash + Objects.hashCode(this.priceRange);
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
        final PropertyType other = (PropertyType) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.numberOfBhk, other.numberOfBhk)) {
            return false;
        }
        if (!Objects.equals(this.carpetArea, other.carpetArea)) {
            return false;
        }
        if (!Objects.equals(this.priceRange, other.priceRange)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PropertyType{" + "id=" + id + ", numberOfBhk=" + numberOfBhk + ", carpetArea=" + carpetArea + ", priceRange=" + priceRange + '}';
    }
    
    
    
}
