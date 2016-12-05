package com.vsquaresystem.safedeals.incomeslab;


import java.util.Objects;

public class Incomeslab {

    private Integer id;
    private Double minRange;
    private Double maxRange;
    private Integer bankId;
    private Float percentageDeduction;
 

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getMinRange() {
        return minRange;
    }

    public void setMinRange(Double minRange) {
        this.minRange = minRange;
    }

    public Double getMaxRange() {
        return maxRange;
    }

    public void setMaxRange(Double maxRange) {
        this.maxRange = maxRange;
    }

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public Float getPercentageDeduction() {
        return percentageDeduction;
    }

    public void setPercentageDeduction(Float percentageDeduction) {
        this.percentageDeduction = percentageDeduction;
    }

    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.id);
        hash = 79 * hash + Objects.hashCode(this.minRange);
        hash = 79 * hash + Objects.hashCode(this.maxRange);
        hash = 79 * hash + Objects.hashCode(this.bankId);
        hash = 79 * hash + Objects.hashCode(this.percentageDeduction);
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
        final Incomeslab other = (Incomeslab) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.minRange, other.minRange)) {
            return false;
        }
        if (!Objects.equals(this.maxRange, other.maxRange)) {
            return false;
        }
        if (!Objects.equals(this.bankId, other.bankId)) {
            return false;
        }
        if (!Objects.equals(this.percentageDeduction, other.percentageDeduction)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Incomeslab{" + "id=" + id + ", minRange=" + minRange + ", maxRange=" + maxRange + ", bankId=" + bankId + ", percentageDeduction=" + percentageDeduction + '}';
    }  
            
}
