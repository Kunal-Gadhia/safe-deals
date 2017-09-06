package com.vsquaresystem.safedeals.video;

import java.util.Date;
import java.util.Objects;

public class Video {

    private Integer id;
    private String name;
    private String description;
    private String videoUrl;
    private Boolean isIntroVideo;
    private Integer projectId;
    private Integer propertyId;
    private Integer userId;
    private Date lastUpdatedTimeStamp;

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
        hash = 13 * hash + Objects.hashCode(this.id);
        hash = 13 * hash + Objects.hashCode(this.name);
        hash = 13 * hash + Objects.hashCode(this.description);
        hash = 13 * hash + Objects.hashCode(this.videoUrl);
        hash = 13 * hash + Objects.hashCode(this.isIntroVideo);
        hash = 13 * hash + Objects.hashCode(this.projectId);
        hash = 13 * hash + Objects.hashCode(this.propertyId);
        hash = 13 * hash + Objects.hashCode(this.userId);
        hash = 13 * hash + Objects.hashCode(this.lastUpdatedTimeStamp);
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
        return "Video{" + "id=" + id + ", name=" + name + ", description=" + description + ", videoUrl=" + videoUrl + ", isIntroVideo=" + isIntroVideo + ", projectId=" + projectId + ", propertyId=" + propertyId + ", userId=" + userId + ", lastUpdatedTimeStamp=" + lastUpdatedTimeStamp + '}';
    }

}
