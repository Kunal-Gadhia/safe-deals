/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.testimonial;

import java.util.List;
/**
 *
 * @author Ajay
 */
public class Testimonial {

    private Integer id;
    private String name;
    private String description;
    private List<String> attachment;

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

    public List<String> getAttachment() {
        return attachment;
    }

    public void setAttachment(List<String> attachment) {
        this.attachment = attachment;
    }

    @Override
    public String toString() {
        return "Testimonial{" + "id=" + id + ", name=" + name + ", description=" + description + ", attachment=" + attachment + '}';
    }

}
