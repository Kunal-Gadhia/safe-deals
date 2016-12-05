package com.vsquaresystem.safedeals.salaryrange;

import java.util.Objects;

public class SalaryRange {
    
    private Integer id;
    private Integer minSalary;
    private Integer maxSalary;
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(Integer minSalary) {
        this.minSalary = minSalary;
    }

    public Integer getMaxSalary() {
        return maxSalary;
    }

    public void setMaxSalary(Integer maxSalary) {
        this.maxSalary = maxSalary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "SalaryRange{" + "id=" + id + ", minSalary=" + minSalary + ", maxSalary=" + maxSalary + ", description=" + description + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.id);
        hash = 23 * hash + Objects.hashCode(this.minSalary);
        hash = 23 * hash + Objects.hashCode(this.maxSalary);
        hash = 23 * hash + Objects.hashCode(this.description);
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
        final SalaryRange other = (SalaryRange) obj;
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.minSalary, other.minSalary)) {
            return false;
        }
        if (!Objects.equals(this.maxSalary, other.maxSalary)) {
            return false;
        }
        return true;
    }
}