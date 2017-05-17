/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.road;

import java.util.Objects;

/**
 *
 * @author hp2
 */
public class Road {
    private Integer id;
    private String name;
    private Integer size;
    private RoadCondition roadCondition;
    private RoadType roadType;

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

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public RoadCondition getRoadCondition() {
        return roadCondition;
    }

    public void setRoadCondition(RoadCondition roadCondition) {
        this.roadCondition = roadCondition;
    }

    public RoadType getRoadType() {
        return roadType;
    }

    public void setRoadType(RoadType roadType) {
        this.roadType = roadType;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.id);
        hash = 73 * hash + Objects.hashCode(this.name);
        hash = 73 * hash + Objects.hashCode(this.size);
        hash = 73 * hash + Objects.hashCode(this.roadCondition);
        hash = 73 * hash + Objects.hashCode(this.roadType);
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
        final Road other = (Road) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.size, other.size)) {
            return false;
        }
        if (this.roadCondition != other.roadCondition) {
            return false;
        }
        if (this.roadType != other.roadType) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Road{" + "id=" + id + ", name=" + name + ", size=" + size + ", roadCondition=" + roadCondition + ", roadType=" + roadType + '}';
    }
    
}
