package com.vsquaresystem.safedeals.bank;

import java.util.Objects;

public class Bank {

    private Integer id;
    private String name;
    private Double loanInterestRateForMale;
    private Double loanInterestRateForFemale;
    private Integer maxAgeForSalaried;
    private Integer maxAgeForBusinessman;
    private Integer maxLoanTenure;

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

    public Double getLoanInterestRateForMale() {
        return loanInterestRateForMale;
    }

    public void setLoanInterestRateForMale(Double loanInterestRateForMale) {
        this.loanInterestRateForMale = loanInterestRateForMale;
    }

    public Double getLoanInterestRateForFemale() {
        return loanInterestRateForFemale;
    }

    public void setLoanInterestRateForFemale(Double loanInterestRateForFemale) {
        this.loanInterestRateForFemale = loanInterestRateForFemale;
    }

    public Integer getMaxAgeForSalaried() {
        return maxAgeForSalaried;
    }

    public void setMaxAgeForSalaried(Integer maxAgeForSalaried) {
        this.maxAgeForSalaried = maxAgeForSalaried;
    }

    public Integer getMaxAgeForBusinessman() {
        return maxAgeForBusinessman;
    }

    public void setMaxAgeForBusinessman(Integer maxAgeForBusinessman) {
        this.maxAgeForBusinessman = maxAgeForBusinessman;
    }

    public Integer getMaxLoanTenure() {
        return maxLoanTenure;
    }

    public void setMaxLoanTenure(Integer maxLoanTenure) {
        this.maxLoanTenure = maxLoanTenure;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.id);
        hash = 43 * hash + Objects.hashCode(this.name);
        hash = 43 * hash + Objects.hashCode(this.loanInterestRateForMale);
        hash = 43 * hash + Objects.hashCode(this.loanInterestRateForFemale);
        hash = 43 * hash + Objects.hashCode(this.maxAgeForSalaried);
        hash = 43 * hash + Objects.hashCode(this.maxAgeForBusinessman);
        hash = 43 * hash + Objects.hashCode(this.maxLoanTenure);
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
        final Bank other = (Bank) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.loanInterestRateForMale, other.loanInterestRateForMale)) {
            return false;
        }
        if (!Objects.equals(this.loanInterestRateForFemale, other.loanInterestRateForFemale)) {
            return false;
        }
        if (!Objects.equals(this.maxAgeForSalaried, other.maxAgeForSalaried)) {
            return false;
        }
        if (!Objects.equals(this.maxAgeForBusinessman, other.maxAgeForBusinessman)) {
            return false;
        }
        if (!Objects.equals(this.maxLoanTenure, other.maxLoanTenure)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Bank{" + "id=" + id + ", name=" + name + ", loanInterestRateForMale=" + loanInterestRateForMale + ", loanInterestRateForFemale=" + loanInterestRateForFemale + ", maxAgeForSalaried=" + maxAgeForSalaried + ", maxAgeForBusinessman=" + maxAgeForBusinessman + ", maxLoanTenure=" + maxLoanTenure + '}';
    }

}
