/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.testimonial;

import java.util.Objects;

/**
 *
 * @author Ajay
 */
public class TestimonialConfig {

    private Integer id;
    private String name;
    private String description;
    private String profession;
    private String designation;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    @Override
    public String toString() {
        return "TestimonialConfig{" + "id=" + id + ", name=" + name + ", description=" + description + ", profession=" + profession + ", designation=" + designation + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + Objects.hashCode(this.id);
        hash = 43 * hash + Objects.hashCode(this.name);
        hash = 43 * hash + Objects.hashCode(this.description);
        hash = 43 * hash + Objects.hashCode(this.profession);
        hash = 43 * hash + Objects.hashCode(this.designation);
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
        final TestimonialConfig other = (TestimonialConfig) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.profession, other.profession)) {
            return false;
        }
        if (!Objects.equals(this.designation, other.designation)) {
            return false;
        }
        return true;
    }

}
