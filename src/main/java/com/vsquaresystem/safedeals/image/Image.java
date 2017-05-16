/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.image;

import java.util.List;
import java.util.Objects;

/**
 *
 * @author hp
 */
public class Image {

    private Integer id;
    private String name;
    private Integer projectId;
    private Integer propertyId;
    private Integer locationId;
    private Document documentName;
    private List<String> photoPath;

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

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Integer propertyId) {
        this.propertyId = propertyId;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public Document getDocumentName() {
        return documentName;
    }

    public void setDocumentName(Document documentName) {
        this.documentName = documentName;
    }

    public List<String> getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(List<String> photoPath) {
        this.photoPath = photoPath;
    }

    @Override
    public String toString() {
        return "Image{" + "id=" + id + ", name=" + name + ", projectId=" + projectId + ", propertyId=" + propertyId + ", locationId=" + locationId + ", documentName=" + documentName + ", photoPath=" + photoPath + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + Objects.hashCode(this.id);
        hash = 19 * hash + Objects.hashCode(this.name);
        hash = 19 * hash + Objects.hashCode(this.projectId);
        hash = 19 * hash + Objects.hashCode(this.propertyId);
        hash = 19 * hash + Objects.hashCode(this.locationId);
        hash = 19 * hash + Objects.hashCode(this.documentName);
        hash = 19 * hash + Objects.hashCode(this.photoPath);
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
        final Image other = (Image) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.projectId, other.projectId)) {
            return false;
        }
        if (!Objects.equals(this.propertyId, other.propertyId)) {
            return false;
        }
        if (!Objects.equals(this.locationId, other.locationId)) {
            return false;
        }
        if (this.documentName != other.documentName) {
            return false;
        }
        if (!Objects.equals(this.photoPath, other.photoPath)) {
            return false;
        }
        return true;
    }

}
