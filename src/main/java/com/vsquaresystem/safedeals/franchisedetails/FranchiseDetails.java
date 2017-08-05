/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.franchisedetails;

import java.util.Objects;

/**
 *
 * @author hp
 */
public class FranchiseDetails {

    private Integer id;
    private Integer userId;
    private String mission;
    private String vision;
    private String moto;
    private String fbLink;
    private String twitterLink;
    private String youTubeLink;
    private String websiteLink;
    private String mainOfficeAddress;
    private String branchOfficeAddress;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getMission() {
        return mission;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }

    public String getVision() {
        return vision;
    }

    public void setVision(String vision) {
        this.vision = vision;
    }

    public String getMoto() {
        return moto;
    }

    public void setMoto(String moto) {
        this.moto = moto;
    }

    public String getFbLink() {
        return fbLink;
    }

    public void setFbLink(String fbLink) {
        this.fbLink = fbLink;
    }

    public String getTwitterLink() {
        return twitterLink;
    }

    public void setTwitterLink(String twitterLink) {
        this.twitterLink = twitterLink;
    }

    public String getYouTubeLink() {
        return youTubeLink;
    }

    public void setYouTubeLink(String youTubeLink) {
        this.youTubeLink = youTubeLink;
    }

    public String getWebsiteLink() {
        return websiteLink;
    }

    public void setWebsiteLink(String websiteLink) {
        this.websiteLink = websiteLink;
    }

    public String getMainOfficeAddress() {
        return mainOfficeAddress;
    }

    public void setMainOfficeAddress(String mainOfficeAddress) {
        this.mainOfficeAddress = mainOfficeAddress;
    }

    public String getBranchOfficeAddress() {
        return branchOfficeAddress;
    }

    public void setBranchOfficeAddress(String branchOfficeAddress) {
        this.branchOfficeAddress = branchOfficeAddress;
    }

    @Override
    public String toString() {
        return "FranchiseDetails{" + "id=" + id + ", userId=" + userId + ", mission=" + mission + ", vision=" + vision + ", moto=" + moto + ", fbLink=" + fbLink + ", twitterLink=" + twitterLink + ", youTubeLink=" + youTubeLink + ", websiteLink=" + websiteLink + ", mainOfficeAddress=" + mainOfficeAddress + ", branchOfficeAddress=" + branchOfficeAddress + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.id);
        hash = 31 * hash + Objects.hashCode(this.userId);
        hash = 31 * hash + Objects.hashCode(this.mission);
        hash = 31 * hash + Objects.hashCode(this.vision);
        hash = 31 * hash + Objects.hashCode(this.moto);
        hash = 31 * hash + Objects.hashCode(this.fbLink);
        hash = 31 * hash + Objects.hashCode(this.twitterLink);
        hash = 31 * hash + Objects.hashCode(this.youTubeLink);
        hash = 31 * hash + Objects.hashCode(this.websiteLink);
        hash = 31 * hash + Objects.hashCode(this.mainOfficeAddress);
        hash = 31 * hash + Objects.hashCode(this.branchOfficeAddress);
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
        final FranchiseDetails other = (FranchiseDetails) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.userId, other.userId)) {
            return false;
        }
        if (!Objects.equals(this.mission, other.mission)) {
            return false;
        }
        if (!Objects.equals(this.vision, other.vision)) {
            return false;
        }
        if (!Objects.equals(this.moto, other.moto)) {
            return false;
        }
        if (!Objects.equals(this.fbLink, other.fbLink)) {
            return false;
        }
        if (!Objects.equals(this.twitterLink, other.twitterLink)) {
            return false;
        }
        if (!Objects.equals(this.youTubeLink, other.youTubeLink)) {
            return false;
        }
        if (!Objects.equals(this.websiteLink, other.websiteLink)) {
            return false;
        }
        if (!Objects.equals(this.mainOfficeAddress, other.mainOfficeAddress)) {
            return false;
        }
        if (!Objects.equals(this.branchOfficeAddress, other.branchOfficeAddress)) {
            return false;
        }
        return true;
    }

}
