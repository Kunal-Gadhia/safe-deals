package com.vsquaresystem.safedeals.workplacecategory;

import java.util.Date;
import java.util.Objects;

public class WorkplaceCategory {

    private Integer id;
    private Integer workplaceAreaId;
    private String name;
    private String description;
    private Integer userId;
    private Date lastUpdatedTimeStamp;

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
        int hash = 3;
        hash = 43 * hash + Objects.hashCode(this.id);
        hash = 43 * hash + Objects.hashCode(this.workplaceAreaId);
        hash = 43 * hash + Objects.hashCode(this.name);
        hash = 43 * hash + Objects.hashCode(this.description);
        hash = 43 * hash + Objects.hashCode(this.userId);
        hash = 43 * hash + Objects.hashCode(this.lastUpdatedTimeStamp);
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
        return "WorkplaceCategory{" + "id=" + id + ", workplaceAreaId=" + workplaceAreaId + ", name=" + name + ", description=" + description + ", userId=" + userId + ", lastUpdatedTimeStamp=" + lastUpdatedTimeStamp + '}';
    }

}
