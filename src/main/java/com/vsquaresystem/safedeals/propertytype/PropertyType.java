package com.vsquaresystem.safedeals.propertytype;

import java.util.Objects;

public class PropertyType {
    private Integer id;
    private Integer numberOfRooms;
    private Integer carpetArea;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(Integer numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public Integer getCarpetArea() {
        return carpetArea;
    }

    public void setCarpetArea(Integer carpetArea) {
        this.carpetArea = carpetArea;
    }

    @Override
    public String toString() {
        return "PropertyType{" + "id=" + id + ", numberOfRooms=" + numberOfRooms + ", carpetArea=" + carpetArea + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.id);
        hash = 41 * hash + Objects.hashCode(this.numberOfRooms);
        hash = 41 * hash + Objects.hashCode(this.carpetArea);
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
        final PropertyType other = (PropertyType) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.numberOfRooms, other.numberOfRooms)) {
            return false;
        }
        if (!Objects.equals(this.carpetArea, other.carpetArea)) {
            return false;
        }
        return true;
    }

   
}
