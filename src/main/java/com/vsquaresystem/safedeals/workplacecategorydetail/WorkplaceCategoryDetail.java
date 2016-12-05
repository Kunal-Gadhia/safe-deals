package com.vsquaresystem.safedeals.workplacecategorydetail;

import java.util.Objects;

public class WorkplaceCategoryDetail {
    private Integer id;
    private Integer workplaceCategoryId;
    private Integer salaryRangeId;
    private Integer numberOfEmployee;
    private Integer numberOfStudent;
    private Integer numberOfBed;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWorkplaceCategoryId() {
        return workplaceCategoryId;
    }

    public void setWorkplaceCategoryId(Integer workplaceCategoryId) {
        this.workplaceCategoryId = workplaceCategoryId;
    }

    public Integer getSalaryRangeId() {
        return salaryRangeId;
    }

    public void setSalaryRangeId(Integer salaryRangeId) {
        this.salaryRangeId = salaryRangeId;
    }

    public Integer getNumberOfEmployee() {
        return numberOfEmployee;
    }

    public void setNumberOfEmployee(Integer numberOfEmployee) {
        this.numberOfEmployee = numberOfEmployee;
    }

    public Integer getNumberOfStudent() {
        return numberOfStudent;
    }

    public void setNumberOfStudent(Integer numberOfStudent) {
        this.numberOfStudent = numberOfStudent;
    }

    public Integer getNumberOfBed() {
        return numberOfBed;
    }

    public void setNumberOfBed(Integer numberOfBed) {
        this.numberOfBed = numberOfBed;
    }

    @Override
    public String toString() {
        return "WorkplaceCategoryDetail{" + "id=" + id + ", workplaceCategoryId=" + workplaceCategoryId + ", salaryRangeId=" + salaryRangeId + ", numberOfEmployee=" + numberOfEmployee + ", numberOfStudent=" + numberOfStudent + ", numberOfBed=" + numberOfBed + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.id);
        hash = 41 * hash + Objects.hashCode(this.workplaceCategoryId);
        hash = 41 * hash + Objects.hashCode(this.salaryRangeId);
        hash = 41 * hash + Objects.hashCode(this.numberOfEmployee);
        hash = 41 * hash + Objects.hashCode(this.numberOfStudent);
        hash = 41 * hash + Objects.hashCode(this.numberOfBed);
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
        final WorkplaceCategoryDetail other = (WorkplaceCategoryDetail) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.workplaceCategoryId, other.workplaceCategoryId)) {
            return false;
        }
        if (!Objects.equals(this.salaryRangeId, other.salaryRangeId)) {
            return false;
        }
        if (!Objects.equals(this.numberOfEmployee, other.numberOfEmployee)) {
            return false;
        }
        if (!Objects.equals(this.numberOfStudent, other.numberOfStudent)) {
            return false;
        }
        if (!Objects.equals(this.numberOfBed, other.numberOfBed)) {
            return false;
        }
        return true;
    }      

}
