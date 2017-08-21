package com.vsquaresystem.safedeals.project;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Project {

    private Integer id;
    private String projectId;
    private String name;
    private Integer stateId;
    private Integer cityId;
    private Integer locationId;
    private String subLocation;
    private ProjectType projectType;
    private ProjectScale projectScale;
    private List<Integer> propertiesType;
    private Date bookingStartDate;
    private ConstructionStage constructionStage;
    private Date completionDate;
    private Integer totalBuildings;
    private Integer totalFloors;
    private Integer totalUnits;
    private Integer majorApproachRoad;
    private Double offeredPrice;
    private Double discount;
    private Date offerValidTill;
    private String paymentSchedule;
    private List<Integer> workplaces;
    private List<Integer> basicAmenities;
    private List<Integer> luxuryAmenities;
    private List<String> ownershipProof;
    private List<Integer> approvedBanks;
    private Boolean sdVerified;
    private List<Integer> privateAmenities;
    private String projectTestimonial;
    private Double salableArea;
    private Double carpetArea;
    private Double buildUpArea;
    private Integer balconyCount;
    private Integer toiletCount;
    private Boolean openTerrace;
    private Boolean openLand;
    private Double latitude;
    private Double longitude;
    private Boolean featuredProject;
    private Boolean bus;
    private Boolean auto;
    private Boolean taxi;
    private Boolean metro;
    private Double distance;
    private Integer unit;
    private Double totalArea;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public String getSubLocation() {
        return subLocation;
    }

    public void setSubLocation(String subLocation) {
        this.subLocation = subLocation;
    }

    public ProjectType getProjectType() {
        return projectType;
    }

    public void setProjectType(ProjectType projectType) {
        this.projectType = projectType;
    }

    public ProjectScale getProjectScale() {
        return projectScale;
    }

    public void setProjectScale(ProjectScale projectScale) {
        this.projectScale = projectScale;
    }

    public List<Integer> getPropertiesType() {
        return propertiesType;
    }

    public void setPropertiesType(List<Integer> propertiesType) {
        this.propertiesType = propertiesType;
    }

    public Date getBookingStartDate() {
        return bookingStartDate;
    }

    public void setBookingStartDate(Date bookingStartDate) {
        this.bookingStartDate = bookingStartDate;
    }

    public ConstructionStage getConstructionStage() {
        return constructionStage;
    }

    public void setConstructionStage(ConstructionStage constructionStage) {
        this.constructionStage = constructionStage;
    }

    public Date getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }

    public Integer getTotalBuildings() {
        return totalBuildings;
    }

    public void setTotalBuildings(Integer totalBuildings) {
        this.totalBuildings = totalBuildings;
    }

    public Integer getTotalFloors() {
        return totalFloors;
    }

    public void setTotalFloors(Integer totalFloors) {
        this.totalFloors = totalFloors;
    }

    public Integer getTotalUnits() {
        return totalUnits;
    }

    public void setTotalUnits(Integer totalUnits) {
        this.totalUnits = totalUnits;
    }

    public Integer getMajorApproachRoad() {
        return majorApproachRoad;
    }

    public void setMajorApproachRoad(Integer majorApproachRoad) {
        this.majorApproachRoad = majorApproachRoad;
    }

    public Double getOfferedPrice() {
        return offeredPrice;
    }

    public void setOfferedPrice(Double offeredPrice) {
        this.offeredPrice = offeredPrice;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Date getOfferValidTill() {
        return offerValidTill;
    }

    public void setOfferValidTill(Date offerValidTill) {
        this.offerValidTill = offerValidTill;
    }

    public String getPaymentSchedule() {
        return paymentSchedule;
    }

    public void setPaymentSchedule(String paymentSchedule) {
        this.paymentSchedule = paymentSchedule;
    }

    public List<Integer> getWorkplaces() {
        return workplaces;
    }

    public void setWorkplaces(List<Integer> workplaces) {
        this.workplaces = workplaces;
    }

    public List<Integer> getBasicAmenities() {
        return basicAmenities;
    }

    public void setBasicAmenities(List<Integer> basicAmenities) {
        this.basicAmenities = basicAmenities;
    }

    public List<Integer> getLuxuryAmenities() {
        return luxuryAmenities;
    }

    public void setLuxuryAmenities(List<Integer> luxuryAmenities) {
        this.luxuryAmenities = luxuryAmenities;
    }

    public List<String> getOwnershipProof() {
        return ownershipProof;
    }

    public void setOwnershipProof(List<String> ownershipProof) {
        this.ownershipProof = ownershipProof;
    }

    public List<Integer> getApprovedBanks() {
        return approvedBanks;
    }

    public void setApprovedBanks(List<Integer> approvedBanks) {
        this.approvedBanks = approvedBanks;
    }

    public Boolean getSdVerified() {
        return sdVerified;
    }

    public void setSdVerified(Boolean sdVerified) {
        this.sdVerified = sdVerified;
    }

    public List<Integer> getPrivateAmenities() {
        return privateAmenities;
    }

    public void setPrivateAmenities(List<Integer> privateAmenities) {
        this.privateAmenities = privateAmenities;
    }

    public String getProjectTestimonial() {
        return projectTestimonial;
    }

    public void setProjectTestimonial(String projectTestimonial) {
        this.projectTestimonial = projectTestimonial;
    }

    public Double getSalableArea() {
        return salableArea;
    }

    public void setSalableArea(Double salableArea) {
        this.salableArea = salableArea;
    }

    public Double getCarpetArea() {
        return carpetArea;
    }

    public void setCarpetArea(Double carpetArea) {
        this.carpetArea = carpetArea;
    }

    public Double getBuildUpArea() {
        return buildUpArea;
    }

    public void setBuildUpArea(Double buildUpArea) {
        this.buildUpArea = buildUpArea;
    }

    public Integer getBalconyCount() {
        return balconyCount;
    }

    public void setBalconyCount(Integer balconyCount) {
        this.balconyCount = balconyCount;
    }

    public Integer getToiletCount() {
        return toiletCount;
    }

    public void setToiletCount(Integer toiletCount) {
        this.toiletCount = toiletCount;
    }

    public Boolean getOpenTerrace() {
        return openTerrace;
    }

    public void setOpenTerrace(Boolean openTerrace) {
        this.openTerrace = openTerrace;
    }

    public Boolean getOpenLand() {
        return openLand;
    }

    public void setOpenLand(Boolean openLand) {
        this.openLand = openLand;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Boolean getFeaturedProject() {
        return featuredProject;
    }

    public void setFeaturedProject(Boolean featuredProject) {
        this.featuredProject = featuredProject;
    }

    public Boolean getBus() {
        return bus;
    }

    public void setBus(Boolean bus) {
        this.bus = bus;
    }

    public Boolean getAuto() {
        return auto;
    }

    public void setAuto(Boolean auto) {
        this.auto = auto;
    }

    public Boolean getTaxi() {
        return taxi;
    }

    public void setTaxi(Boolean taxi) {
        this.taxi = taxi;
    }

    public Boolean getMetro() {
        return metro;
    }

    public void setMetro(Boolean metro) {
        this.metro = metro;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Integer getUnit() {
        return unit;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    public Double getTotalArea() {
        return totalArea;
    }

    public void setTotalArea(Double totalArea) {
        this.totalArea = totalArea;
    }

    @Override
    public String toString() {
        return "Project{" + "id=" + id + ", projectId=" + projectId + ", name=" + name + ", stateId=" + stateId + ", cityId=" + cityId + ", locationId=" + locationId + ", subLocation=" + subLocation + ", projectType=" + projectType + ", projectScale=" + projectScale + ", propertiesType=" + propertiesType + ", bookingStartDate=" + bookingStartDate + ", constructionStage=" + constructionStage + ", completionDate=" + completionDate + ", totalBuildings=" + totalBuildings + ", totalFloors=" + totalFloors + ", totalUnits=" + totalUnits + ", majorApproachRoad=" + majorApproachRoad + ", offeredPrice=" + offeredPrice + ", discount=" + discount + ", offerValidTill=" + offerValidTill + ", paymentSchedule=" + paymentSchedule + ", workplaces=" + workplaces + ", basicAmenities=" + basicAmenities + ", luxuryAmenities=" + luxuryAmenities + ", ownershipProof=" + ownershipProof + ", approvedBanks=" + approvedBanks + ", sdVerified=" + sdVerified + ", privateAmenities=" + privateAmenities + ", projectTestimonial=" + projectTestimonial + ", salableArea=" + salableArea + ", carpetArea=" + carpetArea + ", buildUpArea=" + buildUpArea + ", balconyCount=" + balconyCount + ", toiletCount=" + toiletCount + ", openTerrace=" + openTerrace + ", openLand=" + openLand + ", latitude=" + latitude + ", longitude=" + longitude + ", featuredProject=" + featuredProject + ", bus=" + bus + ", auto=" + auto + ", taxi=" + taxi + ", metro=" + metro + ", distance=" + distance + ", unit=" + unit + ", totalArea=" + totalArea + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.id);
        hash = 29 * hash + Objects.hashCode(this.projectId);
        hash = 29 * hash + Objects.hashCode(this.name);
        hash = 29 * hash + Objects.hashCode(this.stateId);
        hash = 29 * hash + Objects.hashCode(this.cityId);
        hash = 29 * hash + Objects.hashCode(this.locationId);
        hash = 29 * hash + Objects.hashCode(this.subLocation);
        hash = 29 * hash + Objects.hashCode(this.projectType);
        hash = 29 * hash + Objects.hashCode(this.projectScale);
        hash = 29 * hash + Objects.hashCode(this.propertiesType);
        hash = 29 * hash + Objects.hashCode(this.bookingStartDate);
        hash = 29 * hash + Objects.hashCode(this.constructionStage);
        hash = 29 * hash + Objects.hashCode(this.completionDate);
        hash = 29 * hash + Objects.hashCode(this.totalBuildings);
        hash = 29 * hash + Objects.hashCode(this.totalFloors);
        hash = 29 * hash + Objects.hashCode(this.totalUnits);
        hash = 29 * hash + Objects.hashCode(this.majorApproachRoad);
        hash = 29 * hash + Objects.hashCode(this.offeredPrice);
        hash = 29 * hash + Objects.hashCode(this.discount);
        hash = 29 * hash + Objects.hashCode(this.offerValidTill);
        hash = 29 * hash + Objects.hashCode(this.paymentSchedule);
        hash = 29 * hash + Objects.hashCode(this.workplaces);
        hash = 29 * hash + Objects.hashCode(this.basicAmenities);
        hash = 29 * hash + Objects.hashCode(this.luxuryAmenities);
        hash = 29 * hash + Objects.hashCode(this.ownershipProof);
        hash = 29 * hash + Objects.hashCode(this.approvedBanks);
        hash = 29 * hash + Objects.hashCode(this.sdVerified);
        hash = 29 * hash + Objects.hashCode(this.privateAmenities);
        hash = 29 * hash + Objects.hashCode(this.projectTestimonial);
        hash = 29 * hash + Objects.hashCode(this.salableArea);
        hash = 29 * hash + Objects.hashCode(this.carpetArea);
        hash = 29 * hash + Objects.hashCode(this.buildUpArea);
        hash = 29 * hash + Objects.hashCode(this.balconyCount);
        hash = 29 * hash + Objects.hashCode(this.toiletCount);
        hash = 29 * hash + Objects.hashCode(this.openTerrace);
        hash = 29 * hash + Objects.hashCode(this.openLand);
        hash = 29 * hash + Objects.hashCode(this.latitude);
        hash = 29 * hash + Objects.hashCode(this.longitude);
        hash = 29 * hash + Objects.hashCode(this.featuredProject);
        hash = 29 * hash + Objects.hashCode(this.bus);
        hash = 29 * hash + Objects.hashCode(this.auto);
        hash = 29 * hash + Objects.hashCode(this.taxi);
        hash = 29 * hash + Objects.hashCode(this.metro);
        hash = 29 * hash + Objects.hashCode(this.distance);
        hash = 29 * hash + Objects.hashCode(this.unit);
        hash = 29 * hash + Objects.hashCode(this.totalArea);
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
        final Project other = (Project) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.projectId, other.projectId)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.stateId, other.stateId)) {
            return false;
        }
        if (!Objects.equals(this.cityId, other.cityId)) {
            return false;
        }
        if (!Objects.equals(this.locationId, other.locationId)) {
            return false;
        }
        if (!Objects.equals(this.subLocation, other.subLocation)) {
            return false;
        }
        if (this.projectType != other.projectType) {
            return false;
        }
        if (this.projectScale != other.projectScale) {
            return false;
        }
        if (!Objects.equals(this.propertiesType, other.propertiesType)) {
            return false;
        }
        if (!Objects.equals(this.bookingStartDate, other.bookingStartDate)) {
            return false;
        }
        if (this.constructionStage != other.constructionStage) {
            return false;
        }
        if (!Objects.equals(this.completionDate, other.completionDate)) {
            return false;
        }
        if (!Objects.equals(this.totalBuildings, other.totalBuildings)) {
            return false;
        }
        if (!Objects.equals(this.totalFloors, other.totalFloors)) {
            return false;
        }
        if (!Objects.equals(this.totalUnits, other.totalUnits)) {
            return false;
        }
        if (!Objects.equals(this.majorApproachRoad, other.majorApproachRoad)) {
            return false;
        }
        if (!Objects.equals(this.offeredPrice, other.offeredPrice)) {
            return false;
        }
        if (!Objects.equals(this.discount, other.discount)) {
            return false;
        }
        if (!Objects.equals(this.offerValidTill, other.offerValidTill)) {
            return false;
        }
        if (!Objects.equals(this.paymentSchedule, other.paymentSchedule)) {
            return false;
        }
        if (!Objects.equals(this.workplaces, other.workplaces)) {
            return false;
        }
        if (!Objects.equals(this.basicAmenities, other.basicAmenities)) {
            return false;
        }
        if (!Objects.equals(this.luxuryAmenities, other.luxuryAmenities)) {
            return false;
        }
        if (!Objects.equals(this.ownershipProof, other.ownershipProof)) {
            return false;
        }
        if (!Objects.equals(this.approvedBanks, other.approvedBanks)) {
            return false;
        }
        if (!Objects.equals(this.sdVerified, other.sdVerified)) {
            return false;
        }
        if (!Objects.equals(this.privateAmenities, other.privateAmenities)) {
            return false;
        }
        if (!Objects.equals(this.projectTestimonial, other.projectTestimonial)) {
            return false;
        }
        if (!Objects.equals(this.salableArea, other.salableArea)) {
            return false;
        }
        if (!Objects.equals(this.carpetArea, other.carpetArea)) {
            return false;
        }
        if (!Objects.equals(this.buildUpArea, other.buildUpArea)) {
            return false;
        }
        if (!Objects.equals(this.balconyCount, other.balconyCount)) {
            return false;
        }
        if (!Objects.equals(this.toiletCount, other.toiletCount)) {
            return false;
        }
        if (!Objects.equals(this.openTerrace, other.openTerrace)) {
            return false;
        }
        if (!Objects.equals(this.openLand, other.openLand)) {
            return false;
        }
        if (!Objects.equals(this.latitude, other.latitude)) {
            return false;
        }
        if (!Objects.equals(this.longitude, other.longitude)) {
            return false;
        }
        if (!Objects.equals(this.featuredProject, other.featuredProject)) {
            return false;
        }
        if (!Objects.equals(this.bus, other.bus)) {
            return false;
        }
        if (!Objects.equals(this.auto, other.auto)) {
            return false;
        }
        if (!Objects.equals(this.taxi, other.taxi)) {
            return false;
        }
        if (!Objects.equals(this.metro, other.metro)) {
            return false;
        }
        if (!Objects.equals(this.distance, other.distance)) {
            return false;
        }
        if (!Objects.equals(this.unit, other.unit)) {
            return false;
        }
        if (!Objects.equals(this.totalArea, other.totalArea)) {
            return false;
        }
        return true;
    }

}
