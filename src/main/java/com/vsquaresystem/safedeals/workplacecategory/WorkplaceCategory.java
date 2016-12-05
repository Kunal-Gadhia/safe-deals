package com.vsquaresystem.safedeals.workplacecategory;

import java.util.Objects;

public class WorkplaceCategory {

    private Integer id;
    private Integer workplaceAreaId;
    private String name;
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWorkplaceAreaId() {
        return workplaceAreaId;
    }

    public void setWorkplaceAreaId(Integer workplaceAreaId) {
        this.workplaceAreaId = workplaceAreaId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.id);
        hash = 53 * hash + Objects.hashCode(this.workplaceAreaId);
        hash = 53 * hash + Objects.hashCode(this.name);
        hash = 53 * hash + Objects.hashCode(this.description);
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
        final WorkplaceCategory other = (WorkplaceCategory) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.workplaceAreaId, other.workplaceAreaId)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "WorkplaceCategory{" + "id=" + id + ", workplaceAreaId=" + workplaceAreaId + ", name=" + name + ", description=" + description + '}';
    }

}