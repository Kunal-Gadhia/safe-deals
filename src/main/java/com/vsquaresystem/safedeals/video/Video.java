package com.vsquaresystem.safedeals.video;

import java.util.Objects;

public class Video {

    private Integer id;
    private String name;
    private String description;
    private String videoUrl;
    private Boolean isIntroVideo;
    private Integer projectId;
    private Integer propertyId;

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

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public Boolean getIsIntroVideo() {
        return isIntroVideo;
    }

    public void setIsIntroVideo(Boolean isIntroVideo) {
        this.isIntroVideo = isIntroVideo;
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

    @Override
    public String toString() {
        return "Video{" + "id=" + id + ", name=" + name + ", description=" + description + ", videoUrl=" + videoUrl + ", isIntroVideo=" + isIntroVideo + ", projectId=" + projectId + ", propertyId=" + propertyId + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.id);
        hash = 73 * hash + Objects.hashCode(this.name);
        hash = 73 * hash + Objects.hashCode(this.description);
        hash = 73 * hash + Objects.hashCode(this.videoUrl);
        hash = 73 * hash + Objects.hashCode(this.isIntroVideo);
        hash = 73 * hash + Objects.hashCode(this.projectId);
        hash = 73 * hash + Objects.hashCode(this.propertyId);
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
        final Video other = (Video) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.videoUrl, other.videoUrl)) {
            return false;
        }
        if (!Objects.equals(this.isIntroVideo, other.isIntroVideo)) {
            return false;
        }
        if (!Objects.equals(this.projectId, other.projectId)) {
            return false;
        }
        if (!Objects.equals(this.propertyId, other.propertyId)) {
            return false;
        }
        return true;
    }

}
