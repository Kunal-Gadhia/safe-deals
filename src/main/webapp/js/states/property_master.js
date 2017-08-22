angular.module("safedeals.states.property_master", ['ngComboDatePicker'])
        .config(function ($stateProvider, templateRoot) {
            $stateProvider.state('admin.masters_property', {
                'url': '/property_master?offset',
                'templateUrl': templateRoot + '/masters/property/list.html',
                'controller': 'PropertyListController'
            });
            $stateProvider.state('admin.masters_property.add', {
                'url': '/add',
                'templateUrl': templateRoot + '/masters/property/form.html',
                'controller': 'PropertyAddController'
            });
            $stateProvider.state('admin.masters_property.edit', {
                'url': '/:propertyId/edit',
                'templateUrl': templateRoot + '/masters/property/form.html',
                'controller': 'PropertyEditController'
            });
            $stateProvider.state('admin.masters_property.delete', {
                'url': '/:propertyId/delete',
                'templateUrl': templateRoot + '/masters/property/delete.html',
                'controller': 'PropertyDeleteController'
            });
            $stateProvider.state('admin.masters_property.photo', {
                'url': '/:propertyId/photo',
                'templateUrl': templateRoot + '/masters/property/photo.html',
                'controller': 'PropertyPhotoController'
            });
            $stateProvider.state('admin.masters_property.view', {
                'url': '/:propertyId/view',
                'templateUrl': templateRoot + '/masters/property/view.html',
                'controller': 'PropertyViewController'
            });
            $stateProvider.state('admin.masters_property.info', {
                'url': '/:propertyId/info',
                'templateUrl': templateRoot + '/masters/property/info.html',
                'controller': 'PropertyInfoController'
            });
        })
        .controller('PropertyInfoController', function (PropertyService, PrivateAmenitiesService, BankService, AmenityDetailService, TransportationService, RoadService, ProjectService, CityService, LocationService, $scope, $stateParams, $state) {
            $scope.editableProperty = PropertyService.get({
                'id': $stateParams.propertyId
            }, function (property) {
                $scope.editableProperty.city = CityService.get({
                    'id': $scope.editableProperty.cityId
                });
                $scope.editableProperty.location = LocationService.get({
                    'id': $scope.editableProperty.locationId
                });
                $scope.editableProperty.project = ProjectService.get({
                    'id': $scope.editableProperty.projectId
                });
                $scope.editableProperty.road = RoadService.get({
                    'id': $scope.editableProperty.majorApproachRoad
                });
                property.publicTransportObjects = [];
                angular.forEach(property.publicTransport, function (publicTransport) {
                    property.publicTransportObjects.push(TransportationService.get({
                        'id': publicTransport
                    }));
                });

                property.workplacesObjects = [];
                angular.forEach(property.workplaces, function (workplaces) {
                    property.workplacesObjects.push(AmenityDetailService.get({
                        'id': workplaces
                    }));
                });

                property.projectsNearbyObjects = [];
                angular.forEach(property.projectsNearby, function (projectsNearby) {
                    property.projectsNearbyObjects.push(ProjectService.get({
                        'id': projectsNearby
                    }));
                });

                property.basicAmenitiesObjects = [];
                angular.forEach(property.basicAmenities, function (basicAmenities) {
                    property.basicAmenitiesObjects.push(AmenityDetailService.get({
                        'id': basicAmenities
                    }));
                });

                property.luxuryAmenitiesObjects = [];
                angular.forEach(property.luxuryAmenities, function (luxuryAmenities) {
                    property.luxuryAmenitiesObjects.push(AmenityDetailService.get({
                        'id': luxuryAmenities
                    }));
                });

                property.approvedBanksObjects = [];
                angular.forEach(property.approvedBanks, function (approvedBank) {
                    property.approvedBanksObjects.push(BankService.get({
                        'id': approvedBank
                    }));
                });

                property.privateAmenitiesObjects = [];
                angular.forEach(property.privateAmenities, function (privateAmenity) {
                    property.privateAmenitiesObjects.push(PrivateAmenitiesService.get({
                        'id': privateAmenity
                    }));
                });

//                property.propertiesSizeObjects = [];
            })

        })
        .controller('PropertyViewController', function ($scope, $stateParams, $state) {
            $scope.property = {};
            $scope.property.id = $stateParams.propertyId;
            $scope.goBack = function () {
                $state.go('admin.masters_property', {}, {'reload': true});
            };
        })
        .controller('PropertyPhotoController', function (restRoot, FileUploader, $scope, $stateParams, $state) {
            $scope.enableSaveButton = false;
            $scope.goBack = function () {
                $state.go('admin.masters_property', {}, {'reload': true});
            };
            var uploader = $scope.fileUploader = new FileUploader({
                url: restRoot + '/property/' + $stateParams.propertyId + '/attachment',
                autoUpload: true,
                alias: 'attachment'
            });
            uploader.onBeforeUploadItem = function (item) {
                $scope.uploadInProgress = true;
                $scope.uploadSuccess = false;
                console.log("before upload item:", item);
                console.log("uploader", uploader);
            };
            uploader.onErrorItem = function ($scope) {
                $scope.uploadFailed = true;
                $scope.uploadInProgress = false;
                $scope.uploadSuccess = false;
//                    $state.go('.', {}, {'reload': true});
                console.log("upload error");
//                $scope.refreshRawMarketPrice();
            };
            uploader.onCompleteItem = function ($scope, response, status) {
                console.log("Status :%O", status);
                if (status === 200) {
                    console.log("Coming to 200 ??");
                    $scope.uploadInProgress = false;
                    $scope.uploadFailed = false;
                    $scope.uploadSuccess = true;
                    $scope.enableSaveButton = true;
                    console.log("In Progress :" + $scope.uploadInProgress);
                    console.log("Failed :" + $scope.uploadFailed);
                    console.log("Success :" + $scope.uploadSuccess);
                    console.log("Save Button :" + $scope.enableSaveButton);
                } else if (status === 500)
                {
                    $scope.uploadInProgress = false;
                    $scope.uploadFailed = false;
//                    $scope.uploadWarning = true;
                } else {
                    console.log("Coming to else??");
                    $scope.uploadInProgress = false;
                    $scope.uploadFailed = true;
                }

                console.log("upload completion", response);
            };
        })
        .controller('PropertyListController', function (CityService, LocationService, CountryService, StateService, PropertyService, $scope, $stateParams, $state, paginationLimit) {
            if (
                    $stateParams.offset === undefined ||
                    isNaN($stateParams.offset) ||
                    new Number($stateParams.offset) < 0)
            {
                $scope.currentOffset = 0;
            } else {
                $scope.currentOffset = new Number($stateParams.offset);
            }

            $scope.nextOffset = $scope.currentOffset + 5;

            $scope.nextProperties = PropertyService.query({
                'offset': $scope.nextOffset
            });

            $scope.properties = PropertyService.query({
                'offset': $scope.currentOffset
            }, function (property) {
                angular.forEach($scope.properties, function (properties) {

                    if (properties.cityId !== null) {
                        properties.city = CityService.get({
                            'id': properties.cityId
                        });
                    }
                    if (properties.locationId !== null) {
                        properties.location = LocationService.get({
                            'id': properties.locationId
                        });
                    }
                });
            });
            console.log("Properties List :%O", $scope.properties);
//            , function () {
//                angular.forEach($scope.properties, function (property) {
////                    property.country = CountryService.get({
////                        'id': property.countryId
////                    });
////                    property.state = StateService.get({
////                        'id': property.stateId
////                    });
//                    property.city = CityService.get({
//                        'id': property.cityId
//                    });
//                });
//            });

            $scope.nextPage = function () {
                $scope.currentOffset += paginationLimit;
                $state.go(".", {'offset': $scope.currentOffset}, {'reload': true});
            };
            $scope.previousPage = function () {
                if ($scope.currentOffset <= 0) {
                    return;
                }
                $scope.currentOffset -= paginationLimit;
                $state.go(".", {'offset': $scope.currentOffset}, {'reload': true});
            };
        })
        .controller('PropertyAddController', function (ProjectService, UnitService, PrivateAmenitiesService, BankService, AmenityDetailService, TransportationService, RoadService, PropertyTypeService, LocationService, CityService, StateService, CountryService, PropertyService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editableProperty = {};
            $scope.projectsNearbyDisplay = [];
            $scope.editableProperty.projectsNearby = [];
            $scope.publicTransportDisplay = [];
            $scope.editableProperty.publicTransport = [];
            $scope.workplacesDisplay = [];
            $scope.editableProperty.workplaces = [];
            $scope.basicAmenitiesDisplay = [];
            $scope.editableProperty.basicAmenities = [];
            $scope.luxuryAmenitiesDisplay = [];
            $scope.editableProperty.luxuryAmenities = [];
            $scope.approvedBanksDisplay = [];
            $scope.editableProperty.approvedBanks = [];
            $scope.amenitiesWithinProjectDisplay = [];
            $scope.editableProperty.privateAmenities = [];
            $scope.possessionDateBox = false;
            $scope.yearOfConstructionBox = false;
            $scope.locationSteps = [
                'Basic Details',
                'Connectivity',
                'Costing',
                'Neighbourhood',
                'Livability',
                'Marketability',
                'Unit Details',
                'Seller Commision Agreement'
            ];

            $scope.$watch('editableProperty.constructionStage', function (data) {
                console.log("Construction Stage :%O", data);
                if (data === "UNDER_CONSTRUCTION") {
                    $scope.possessionDateBox = true;
                    $scope.yearOfConstructionBox = false;
                }
                if (data === "READY_TO_MOVE") {
                    $scope.possessionDateBox = false;
                    $scope.yearOfConstructionBox = true;
                }
                if (data === "") {
                    $scope.possessionDateBox = false;
                    $scope.yearOfConstructionBox = false;
                }
            });
            $scope.selection = $scope.locationSteps[0];
            $scope.myValue = true;
            
//OLD DATEPICKER             
//            $scope.datePicker = {
//                opened: false,
//                toggle: function () {
//                    this.opened = !this.opened;
//                }
//            };
//            $scope.completionDatePicker = {
//                opened: false,
//                toggle: function () {
//                    this.opened = !this.opened;
//                }
//            };
//            $scope.offerValidTill = {
//                opened: false,
//                toggle: function () {
//                    this.opened = !this.opened;
//                }
//            };
//            $scope.possessionDate = {
//                opened: false,
//                toggle: function () {
//                    this.opened = !this.opened;
//                }
//            };
//            $scope.yearOfConstruction = {
//                opened: false,
//                toggle: function () {
//                    this.opened = !this.opened;
//                }
//            };
            
            $scope.saveProperty = function (property) {
                console.log("Property :%O", property);
                PropertyService.save(property, function () {
                    $state.go('admin.masters_property', null, {'reload': true});
                });
//                ProjectService.save(project, function () {
//                    $state.go('admin.masters_project', null, {'reload': true});
//                });
            };
            $scope.setState = function (state) {
                console.log("xyz", state);
                $scope.stateId = state.id;
                $scope.state = state;
                console.log("$scope.state ", $scope.state);
            };
            $scope.setPropertyType = function (property) {
                console.log("Property :%O", property);
                $scope.editableProperty.propertySize = property.id;
                $scope.editableProperty.propertySizeObject = property;
            };
//            $scope.removePropertyType = function (propertyType) {
//                console.log("Getting the thing :%O", propertyType);
//                var index = $scope.propertiesTypeDisplay.indexOf(propertyType);
//                var index1 = $scope.editableProperty.propertiesType.indexOf(propertyType.id);
//                $scope.propertiesTypeDisplay.splice(index, 1);
//                $scope.editableProperty.propertiesType.splice(index1, 1);
//                console.log("Updated Type Display :%O", $scope.propertiesTypeDisplay);
//                console.log("Updated %O", $scope.editableProperty.propertiesType);
//            };
            $scope.setWorkplaces = function (workplaces) {
                console.log("xyz", workplaces);
                $scope.workplacesDisplay.push(workplaces);
                $scope.workplaces = "";
                $scope.editableProperty.workplaces.push(workplaces.id);
            };
            $scope.removeWorkplaces = function (workplaces) {
                console.log("Getting the thing :%O", workplaces);
                var index = $scope.workplacesDisplay.indexOf(workplaces);
                var index1 = $scope.editableProperty.workplaces.indexOf(workplaces.id);
                $scope.workplacesDisplay.splice(index, 1);
                $scope.editableProperty.workplaces.splice(index1, 1);
                console.log("Updated Type Display :%O", $scope.workplacesDisplay);
                console.log("Updated %O", $scope.editableProperty.workplaces);
            };
            $scope.setBasicAmenities = function (basicAmenities) {
                console.log("xyz", basicAmenities);
                $scope.basicAmenitiesDisplay.push(basicAmenities);
                $scope.$parent.basicAmenities = "";
                $scope.editableProperty.basicAmenities.push(basicAmenities.id);
            };
            $scope.removeBasicAmenities = function (basicAmenities) {
                console.log("Getting the thing :%O", basicAmenities);
                var index = $scope.basicAmenitiesDisplay.indexOf(basicAmenities);
                var index1 = $scope.editableProperty.basicAmenities.indexOf(basicAmenities.id);
                $scope.basicAmenitiesDisplay.splice(index, 1);
                $scope.editableProperty.basicAmenities.splice(index1, 1);
                console.log("Updated Type Display :%O", $scope.basicAmenitiesDisplay);
                console.log("Updated %O", $scope.editableProperty.basicAmenities);
            };
            $scope.setLuxuryAmenities = function (luxuryAmenities) {
                console.log("xyz", luxuryAmenities);
                $scope.luxuryAmenitiesDisplay.push(luxuryAmenities);
                $scope.luxuryAmenities = "";
                $scope.editableProperty.luxuryAmenities.push(luxuryAmenities.id);
            };
            $scope.removeLuxuryAmenities = function (luxuryAmenities) {
                console.log("Getting the thing :%O", luxuryAmenities);
                var index = $scope.luxuryAmenitiesDisplay.indexOf(luxuryAmenities);
                var index1 = $scope.editableProperty.luxuryAmenities.indexOf(luxuryAmenities.id);
                $scope.luxuryAmenitiesDisplay.splice(index, 1);
                $scope.editableProperty.luxuryAmenities.splice(index1, 1);
                console.log("Updated Type Display :%O", $scope.luxuryAmenitiesDisplay);
                console.log("Updated %O", $scope.editableProperty.luxuryAmenities);
            };
            $scope.setApprovedBanks = function (approvedBanks) {
                console.log("xyz", approvedBanks);
                $scope.approvedBanksDisplay.push(approvedBanks);
                $scope.approvedBanks = "";
                $scope.editableProperty.approvedBanks.push(approvedBanks.id);
                console.log("List :%O", $scope.approvedBanksDisplay);
            };
            $scope.removeApprovedBanks = function (approvedBanks) {
                console.log("Getting the thing :%O", approvedBanks);
                var index = $scope.approvedBanksDisplay.indexOf(approvedBanks);
                var index1 = $scope.editableProperty.approvedBanks.indexOf(approvedBanks.id);
                $scope.approvedBanksDisplay.splice(index, 1);
                $scope.editableProperty.approvedBanks.splice(index1, 1);
                console.log("Updated Type Display :%O", $scope.approvedBanksDisplay);
                console.log("Updated %O", $scope.editableProperty.approvedBanks);
            };
            $scope.setAmenitiesWithinProject = function (amenitiesWithinProject) {
                console.log("xyz", amenitiesWithinProject);
                $scope.amenitiesWithinProjectDisplay.push(amenitiesWithinProject);
                $scope.amenitiesWithinProject = "";
                $scope.editableProperty.privateAmenities.push(amenitiesWithinProject.id);
                console.log("List :%O", $scope.amenitiesWithinProjectDisplay);
            };
            $scope.removeAmenitiesWithinProject = function (amenitiesWithinProjects) {
                console.log("Getting the thing :%O", amenitiesWithinProjects);
                var index = $scope.amenitiesWithinProjectDisplay.indexOf(amenitiesWithinProjects);
                var index1 = $scope.editableProperty.privateAmenities.indexOf(amenitiesWithinProjects.id);
                $scope.amenitiesWithinProjectDisplay.splice(index, 1);
                $scope.editableProperty.privateAmenities.splice(index1, 1);
                console.log("Updated Type Display :%O", $scope.amenitiesWithinProjectDisplay);
                console.log("Updated %O", $scope.editableProperty.privateAmenities);
            };
            $scope.setTransportation = function (transportation) {
                console.log("xyz t", transportation);
                $scope.publicTransportDisplay.push(transportation);
                $scope.publicTransport = null;
//                $scope.clear();
                $scope.editableProperty.publicTransport.push(transportation.id);
                console.log("Main Array :%O", $scope.editableProperty.publicTransport);
                console.log("Public Transport Array :%O", $scope.publicTransportDisplay);
            };
            $scope.removeTransportation = function (transportation) {
                console.log("Getting the thing :%O", transportation);
                var index = $scope.publicTransportDisplay.indexOf(transportation);
                var index1 = $scope.editableProperty.publicTransport.indexOf(transportation.id);
                $scope.publicTransportDisplay.splice(index, 1);
                $scope.editableProperty.publicTransport.splice(index1, 1);
                console.log("Updated Type Display :%O", $scope.publicTransportDisplay);
                console.log("Updated %O", $scope.editableProperty.publicTransport);
            };
            $scope.setNearbyProjects = function (nearbyProjects) {
                console.log("xyz t", nearbyProjects);
                $scope.projectsNearbyDisplay.push(nearbyProjects);
                $scope.projectsNearby = null;
//                $scope.clear();
                console.log("ID :%O", nearbyProjects.id);
                $scope.editableProperty.projectsNearby.push(nearbyProjects.id);
                console.log("Main Array :%O", $scope.editableProperty.projectsNearby);
                console.log("Projects Nearby Array :%O", $scope.projectsNearbyDisplay);
            };
            $scope.removeProjectsNearby = function (projectsNearby) {
                console.log("Getting the thing :%O", projectsNearby);
                var index = $scope.projectsNearbyDisplay.indexOf(projectsNearby);
                var index1 = $scope.editableProperty.projectsNearby.indexOf(projectsNearby.id);
                $scope.projectsNearbyDisplay.splice(index, 1);
                $scope.editableProperty.projectsNearby.splice(index1, 1);
                console.log("Updated Type Display :%O", $scope.projectsNearbyDisplay);
                console.log("Updated %O", $scope.editableProperty.projectsNearby);
            };
            $scope.setCity = function (city) {
                console.log("xyz", city);
                $scope.editableProperty.cityId = city.id;
                $scope.editableProperty.city = city;
                console.log("$scope.editableProperty.city ", $scope.editableProperty.city);
            };
            $scope.setLocation = function (location) {
                $scope.editableProperty.locationId = location.id;
                $scope.editableProperty.location = location;
                console.log("$scope.editableProperty.location ", $scope.editableProperty.location);
            };
            $scope.setRoad = function (road) {
                $scope.editableProperty.majorApproachRoad = road.id;
                $scope.editableProperty.road = road;
            };
            $scope.setProject = function (project) {
                $scope.editableProperty.projectId = project.id;
                $scope.editableProperty.project = project;
            };
            $scope.searchStates = function (searchTerm) {
                return StateService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };
            $scope.searchPropertyType = function (searchTerm) {
                console.log("Hello");
                return PropertyTypeService.findByNumberOfBhkLike({
                    'numberOfBhkLike': searchTerm
                }).$promise;
            };
            $scope.searchRoad = function (searchTerm) {
                console.log("Hello");
                return RoadService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };
            $scope.searchTransportation = function (searchTerm) {
                return TransportationService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };
            $scope.searchProject = function (searchTerm) {
                return ProjectService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };
            $scope.searchNearbyProjects = function (searchTerm) {
                return ProjectService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };
            $scope.searchWorkplaces = function (searchTerm) {
                return AmenityDetailService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };
            $scope.searchBasicAmenities = function (searchTerm) {
                return AmenityDetailService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };
            $scope.searchLuxuryAmenities = function (searchTerm) {
                return AmenityDetailService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };
            $scope.searchApprovedBanks = function (searchTerm) {
                return BankService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };
            $scope.searchAmenitiesWithinProject = function (searchTerm) {
                return PrivateAmenitiesService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };
            $scope.searchCities = function (searchTerm) {
                console.log("State Id :%O", $scope.editableProperty.stateId);
                if ($scope.stateId === undefined) {
                    return CityService.findByNameLike({
                        'name': searchTerm
                    }).$promise;
                } else {
                    return CityService.findByNameAndStateId({
                        'name': searchTerm,
                        'stateId': $scope.stateId
                    }).$promise;
                }
            };
            $scope.searchLocations = function (searchTerm) {
                console.log("Search Term :%O", searchTerm);
                console.log("City Id :%O", $scope.editableProperty.cityId);
                if ($scope.editableProperty.cityId === undefined) {
                    console.log("Coming to if ??");
                    return LocationService.findByNameLike({
                        'name': searchTerm
                    }).$promise;
                } else {
                    console.log("Coming to Else ??");
                    return LocationService.findByNameAndCityId({
                        'name': searchTerm,
                        'cityId': $scope.editableProperty.cityId
                    }).$promise;
                }
            };
            $scope.setUnit = function (unit) {
                $scope.editableProperty.unitObject = unit;
                $scope.editableProperty.unit = unit.abbreviation ;
            };
            $scope.searchUnit = function (searchTerm) {
                return UnitService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };
            
            $scope.getLocationStep = function (locationstep) {
                console.log("Location Step :%O", locationstep);
                $scope.selection = locationstep;
                if (locationstep === "Basic Details") {
                    console.log("Hello baisc details");
//                    $scope.amenityCodes = AmenityCodeService.findByTabName({
//                       'name' : AMENITIES
//                    });
                    $scope.myValue = true;
                } else if (locationstep === "Connectivity") {
                    console.log("Hello Connectivity");
                    $scope.connectivity = true;
                    $scope.myValue = false;
                    $scope.costing = false;
                    $scope.neighbourhood = false;
                    $scope.livability = false;
                    $scope.marketability = false;
                    $scope.sellerCommisionAgreement = false;
                    $scope.unitDetails = false;
//                    $scope.clear = function(){
//                        console.log("Coming to clear function??");
//                      $scope.transportation = "";  
//                    };
                }
                else if (locationstep === "Costing") {
                    console.log("Hello Costing");
                    $scope.costing = true;
                    $scope.connectivity = false;
                    $scope.myValue = false;
                    $scope.neighbourhood = false;
                    $scope.livability = false;
                    $scope.marketability = false;
                    $scope.sellerCommisionAgreement = false;
                    $scope.unitDetails = false;
                }
                else if (locationstep === "Neighbourhood") {
                    console.log("Hello neighbourhood for rental");
                    $scope.neighbourhood = true;
                    $scope.myValue = false;
                    $scope.costing = false;
                    $scope.connectivity = false;
                    $scope.marketability = false;
                    $scope.livability = false;
                    $scope.sellerCommisionAgreement = false;
                    $scope.unitDetails = false;
                }
                else if (locationstep === "Livability") {
                    console.log("Hello demand for livability");
                    $scope.livability = true;
                    $scope.myValue = false;
                    $scope.costing = false;
                    $scope.connectivity = false;
                    $scope.marketability = false;
                    $scope.neighbourhood = false;
                    $scope.sellerCommisionAgreement = false;
                    $scope.unitDetails = false;
                }
                else if (locationstep === "Marketability") {
                    console.log("Marketability");
                    $scope.marketability = true;
                    $scope.myValue = false;
                    $scope.costing = false;
                    $scope.connectivity = false;
                    $scope.neighbourhood = false;
                    $scope.livability = false;
                    $scope.sellerCommisionAgreement = false;
                    $scope.unitDetails = false;
                }
                else if (locationstep === "Seller Commision Agreement") {
                    console.log("Hello Seller COmmision Agreement");
                    $scope.sellerCommisionAgreement = true;
                    $scope.myValue = false;
                    $scope.costing = false;
                    $scope.connectivity = false;
                    $scope.neighbourhood = false;
                    $scope.livability = false;
                    $scope.marketability = false;
                    $scope.unitDetails = false;
                }
                else if (locationstep === "Unit Details") {
                    console.log("Hello Unit Details");
                    $scope.unitDetails = true;
                    $scope.myValue = false;
                    $scope.costing = false;
                    $scope.connectivity = false;
                    $scope.neighbourhood = false;
                    $scope.livability = false;
                    $scope.marketability = false;
                    $scope.sellerCommisionAgreement = false;
                }
                else {
                    console.log("Nothing");
                    $scope.livability = false;
                    $scope.myValue = false;
                    $scope.costing = false;
                    $scope.connectivity = false;
                    $scope.neighbourhood = false;
                    $scope.marketability = false;
                    $scope.sellerCommisionAgreement = false;
                    $scope.unitDetails = false;
                }
            };
        })
        .controller('PropertyEditController', function (ProjectService, PrivateAmenitiesService, BankService, AmenityDetailService, TransportationService, RoadService, PropertyTypeService, LocationService, CityService, StateService, CountryService, PropertyService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editableProperty = PropertyService.get({
                'id': $stateParams.propertyId
            }, function () {
                $scope.editableProperty.city = CityService.get({
                    id: $scope.editableProperty.cityId
                }, function (data) {
                    $scope.state = StateService.get({
                        'id': data.stateId
                    });
                });
                $scope.editableProperty.location = LocationService.get({
                    id: $scope.editableProperty.locationId
                });
                $scope.editableProperty.road = RoadService.get({
                    id: $scope.editableProperty.majorApproachRoad
                });
                $scope.editableProperty.propertySizeObject = PropertyTypeService.get({
                    id: $scope.editableProperty.propertySize
                });
                $scope.editableProperty.project = ProjectService.get({
                    'id': $scope.editableProperty.projectId
                });
                var buildingAge = new Date($scope.editableProperty.buildingAge);
                var buildingAgeLong = buildingAge * 1;
                $scope.editableProperty.buildingAge = buildingAgeLong;

                var offerValidTill = new Date($scope.editableProperty.offerValidTill);
                var offerValidTillLong = offerValidTill * 1;
                $scope.editableProperty.offerValidTill = offerValidTillLong;
                
                var possessionDate = new Date($scope.editableProperty.possessionDate);
                var possessionDateLong = possessionDate * 1;
                $scope.editableProperty.possessionDate =possessionDateLong;
                
                var yearOfConstruction = new Date($scope.editableProperty.yearOfConstruction);
                var yearOfConstructionLong = yearOfConstruction * 1;
                $scope.editableProperty.yearOfConstruction =yearOfConstructionLong;

                $scope.publicTransportDisplay = [];
                angular.forEach($scope.editableProperty.publicTransport, function (publicTransport) {
                    TransportationService.get({
                        'id': publicTransport
                    }, function (data) {
                        $scope.publicTransportDisplay.push(data);
                    });
                });
                $scope.workplacesDisplay = [];
                angular.forEach($scope.editableProperty.workplaces, function (workplace) {
                    AmenityDetailService.get({
                        'id': workplace
                    }, function (data) {
                        $scope.workplacesDisplay.push(data);
                    });
                });
                $scope.basicAmenitiesDisplay = [];
                angular.forEach($scope.editableProperty.basicAmenities, function (basicAmenities) {
                    AmenityDetailService.get({
                        'id': basicAmenities
                    }, function (data) {
                        $scope.basicAmenitiesDisplay.push(data);
                    });
                });
                $scope.luxuryAmenitiesDisplay = [];
                angular.forEach($scope.editableProperty.luxuryAmenities, function (luxuryAmenities) {
                    AmenityDetailService.get({
                        'id': luxuryAmenities
                    }, function (data) {
                        $scope.luxuryAmenitiesDisplay.push(data);
                    });
                });
                $scope.approvedBanksDisplay = [];
                angular.forEach($scope.editableProperty.approvedBanks, function (approvedbanks) {
                    BankService.get({
                        'id': approvedbanks
                    }, function (data) {
                        $scope.approvedBanksDisplay.push(data);
                    });
                });
                $scope.amenitiesWithinProjectDisplay = [];
                angular.forEach($scope.editableProperty.privateAmenities, function (privateAmenities) {
                    console.log("Amenities Within porject :%O", privateAmenities);
                    PrivateAmenitiesService.get({
                        'id': privateAmenities
                    }, function (data) {
                        $scope.amenitiesWithinProjectDisplay.push(data);
                    });
                });
                $scope.projectsNearbyDisplay = [];
                angular.forEach($scope.editableProperty.projectsNearby, function (projectsNearby) {
                    console.log("Nearby Projects :%O", projectsNearby);
                    ProjectService.get({
                        'id': projectsNearby
                    }, function (data) {
                        $scope.projectsNearbyDisplay.push(data);
                    });
                });
            });
//            $scope.projectsNearbyDisplay = [];
            $scope.editableProperty.projectsNearby = [];
//            $scope.publicTransportDisplay = [];
            $scope.editableProperty.publicTransport = [];
//            $scope.workplacesDisplay = [];
            $scope.editableProperty.workplaces = [];
//            $scope.basicAmenitiesDisplay = [];
            $scope.editableProperty.basicAmenities = [];
//            $scope.luxuryAmenitiesDisplay = [];
            $scope.editableProperty.luxuryAmenities = [];
//            $scope.approvedBanksDisplay = [];
            $scope.editableProperty.approvedBanks = [];
//            $scope.amenitiesWithinProjectDisplay = [];
            $scope.editableProperty.privateAmenities = [];
            $scope.$watch('editableProperty.constructionStage', function (data) {
                console.log("Construction Stage :%O", data);
                if (data === "UNDER_CONSTRUCTION") {
                    $scope.possessionDateBox = true;
                    $scope.yearOfConstructionBox = false;
                }
                if (data === "READY_TO_MOVE") {
                    $scope.possessionDateBox = false;
                    $scope.yearOfConstructionBox = true;
                }
                if (data === "") {
                    $scope.possessionDateBox = false;
                    $scope.yearOfConstructionBox = false;
                }
            });
            $scope.locationSteps = [
                'Basic Details',
                'Connectivity',
                'Costing',
                'Neighbourhood',
                'Livability',
                'Marketability',
                'Unit Details',
                'Seller Commision Agreement'
            ];
            $scope.selection = $scope.locationSteps[0];
            $scope.myValue = true;
            $scope.datePicker = {
                opened: false,
                toggle: function () {
                    this.opened = !this.opened;
                }
            };
            $scope.completionDatePicker = {
                opened: false,
                toggle: function () {
                    this.opened = !this.opened;
                }
            };
            $scope.offerValidTill = {
                opened: false,
                toggle: function () {
                    this.opened = !this.opened;
                }
            };
            $scope.possessionDate = {
                opened: false,
                toggle: function () {
                    this.opened = !this.opened;
                }
            };
            $scope.yearOfConstruction = {
                opened: false,
                toggle: function () {
                    this.opened = !this.opened;
                }
            };
            $scope.saveProperty = function (property) {
                console.log("Property :%O", property);
                property.$save(property, function () {
                    $state.go('admin.masters_property', null, {'reload': true});
                });
            };
            $scope.setState = function (state) {
                console.log("xyz", state);
                $scope.stateId = state.id;
                $scope.state = state;
                console.log("$scope.state ", $scope.state);
            };
            $scope.setPropertyType = function (property) {
                console.log("Property :%O", property);
                $scope.editableProperty.propertySize = property.id;
                $scope.editableProperty.propertySizeObject = property;
            };
//            $scope.removePropertyType = function (propertyType) {
//                console.log("Getting the thing :%O", propertyType);
//                var index = $scope.propertiesTypeDisplay.indexOf(propertyType);
//                var index1 = $scope.editableProperty.propertiesType.indexOf(propertyType.id);
//                $scope.propertiesTypeDisplay.splice(index, 1);
//                $scope.editableProperty.propertiesType.splice(index1, 1);
//                console.log("Updated Type Display :%O", $scope.propertiesTypeDisplay);
//                console.log("Updated %O", $scope.editableProperty.propertiesType);
//            };
            $scope.setWorkplaces = function (workplaces) {
                console.log("xyz", workplaces);
                $scope.workplacesDisplay.push(workplaces);
                $scope.workplaces = "";
                $scope.editableProperty.workplaces.push(workplaces.id);
            };
            $scope.removeWorkplaces = function (workplaces) {
                console.log("Getting the thing :%O", workplaces);
                var index = $scope.workplacesDisplay.indexOf(workplaces);
                var index1 = $scope.editableProperty.workplaces.indexOf(workplaces.id);
                $scope.workplacesDisplay.splice(index, 1);
                $scope.editableProperty.workplaces.splice(index1, 1);
                console.log("Updated Type Display :%O", $scope.workplacesDisplay);
                console.log("Updated %O", $scope.editableProperty.workplaces);
            };
            $scope.setBasicAmenities = function (basicAmenities) {
                console.log("xyz", basicAmenities);
                $scope.basicAmenitiesDisplay.push(basicAmenities);
                $scope.$parent.basicAmenities = "";
                $scope.editableProperty.basicAmenities.push(basicAmenities.id);
            };
            $scope.removeBasicAmenities = function (basicAmenities) {
                console.log("Getting the thing :%O", basicAmenities);
                var index = $scope.basicAmenitiesDisplay.indexOf(basicAmenities);
                var index1 = $scope.editableProperty.basicAmenities.indexOf(basicAmenities.id);
                $scope.basicAmenitiesDisplay.splice(index, 1);
                $scope.editableProperty.basicAmenities.splice(index1, 1);
                console.log("Updated Type Display :%O", $scope.basicAmenitiesDisplay);
                console.log("Updated %O", $scope.editableProperty.basicAmenities);
            };
            $scope.setLuxuryAmenities = function (luxuryAmenities) {
                console.log("xyz", luxuryAmenities);
                $scope.luxuryAmenitiesDisplay.push(luxuryAmenities);
                $scope.luxuryAmenities = "";
                $scope.editableProperty.luxuryAmenities.push(luxuryAmenities.id);
            };
            $scope.removeLuxuryAmenities = function (luxuryAmenities) {
                console.log("Getting the thing :%O", luxuryAmenities);
                var index = $scope.luxuryAmenitiesDisplay.indexOf(luxuryAmenities);
                var index1 = $scope.editableProperty.luxuryAmenities.indexOf(luxuryAmenities.id);
                $scope.luxuryAmenitiesDisplay.splice(index, 1);
                $scope.editableProperty.luxuryAmenities.splice(index1, 1);
                console.log("Updated Type Display :%O", $scope.luxuryAmenitiesDisplay);
                console.log("Updated %O", $scope.editableProperty.luxuryAmenities);
            };
            $scope.setApprovedBanks = function (approvedBanks) {
                console.log("xyz", approvedBanks);
                $scope.approvedBanksDisplay.push(approvedBanks);
                $scope.approvedBanks = "";
                $scope.editableProperty.approvedBanks.push(approvedBanks.id);
                console.log("List :%O", $scope.approvedBanksDisplay);
            };
            $scope.removeApprovedBanks = function (approvedBanks) {
                console.log("Getting the thing :%O", approvedBanks);
                var index = $scope.approvedBanksDisplay.indexOf(approvedBanks);
                var index1 = $scope.editableProperty.approvedBanks.indexOf(approvedBanks.id);
                $scope.approvedBanksDisplay.splice(index, 1);
                $scope.editableProperty.approvedBanks.splice(index1, 1);
                console.log("Updated Type Display :%O", $scope.approvedBanksDisplay);
                console.log("Updated %O", $scope.editableProperty.approvedBanks);
            };
            $scope.setAmenitiesWithinProject = function (amenitiesWithinProject) {
                console.log("xyz", amenitiesWithinProject);
                $scope.amenitiesWithinProjectDisplay.push(amenitiesWithinProject);
                $scope.amenitiesWithinProject = "";
                $scope.editableProperty.privateAmenities.push(amenitiesWithinProject.id);
                console.log("List :%O", $scope.amenitiesWithinProjectDisplay);
            };
            $scope.removeAmenitiesWithinProject = function (amenitiesWithinProjects) {
                console.log("Getting the thing :%O", amenitiesWithinProjects);
                var index = $scope.amenitiesWithinProjectDisplay.indexOf(amenitiesWithinProjects);
                var index1 = $scope.editableProperty.privateAmenities.indexOf(amenitiesWithinProjects.id);
                $scope.amenitiesWithinProjectDisplay.splice(index, 1);
                $scope.editableProperty.privateAmenities.splice(index1, 1);
                console.log("Updated Type Display :%O", $scope.amenitiesWithinProjectDisplay);
                console.log("Updated %O", $scope.editableProperty.privateAmenities);
            };
            $scope.setTransportation = function (transportation) {
                console.log("xyz t", transportation);
                $scope.publicTransportDisplay.push(transportation);
                $scope.publicTransport = null;
//                $scope.clear();
                $scope.editableProperty.publicTransport.push(transportation.id);
                console.log("Main Array :%O", $scope.editableProperty.publicTransport);
                console.log("Public Transport Array :%O", $scope.publicTransportDisplay);
            };
            $scope.removeTransportation = function (transportation) {
                console.log("Getting the thing :%O", transportation);
                var index = $scope.publicTransportDisplay.indexOf(transportation);
                var index1 = $scope.editableProperty.publicTransport.indexOf(transportation.id);
                $scope.publicTransportDisplay.splice(index, 1);
                $scope.editableProperty.publicTransport.splice(index1, 1);
                console.log("Updated Type Display :%O", $scope.publicTransportDisplay);
                console.log("Updated %O", $scope.editableProperty.publicTransport);
            };
            $scope.setNearbyProjects = function (nearbyProjects) {
                console.log("xyz t", nearbyProjects);
                $scope.projectsNearbyDisplay.push(nearbyProjects);
                $scope.projectsNearby = null;
//                $scope.clear();
                console.log("ID :%O", nearbyProjects.id);
                $scope.editableProperty.projectsNearby.push(nearbyProjects.id);
                console.log("Main Array :%O", $scope.editableProperty.projectsNearby);
                console.log("Projects Nearby Array :%O", $scope.projectsNearbyDisplay);
            };
            $scope.removeProjectsNearby = function (projectsNearby) {
                console.log("Getting the thing :%O", projectsNearby);
                var index = $scope.projectsNearbyDisplay.indexOf(projectsNearby);
                var index1 = $scope.editableProperty.projectsNearby.indexOf(projectsNearby.id);
                $scope.projectsNearbyDisplay.splice(index, 1);
                $scope.editableProperty.projectsNearby.splice(index1, 1);
                console.log("Updated Type Display :%O", $scope.projectsNearbyDisplay);
                console.log("Updated %O", $scope.editableProperty.projectsNearby);
            };
            $scope.setCity = function (city) {
                console.log("xyz", city);
                $scope.editableProperty.cityId = city.id;
                $scope.editableProperty.city = city;
                console.log("$scope.editableProperty.city ", $scope.editableProperty.city);
            };
            $scope.setLocation = function (location) {
                $scope.editableProperty.locationId = location.id;
                $scope.editableProperty.location = location;
                console.log("$scope.editableProperty.location ", $scope.editableProperty.location);
            };
            $scope.setRoad = function (road) {
                $scope.editableProperty.majorApproachRoad = road.id;
                $scope.editableProperty.road = road;
            };
            $scope.setProject = function (project) {
                $scope.editableProperty.projectId = project.id;
                $scope.editableProperty.project = project;
            };
            $scope.searchStates = function (searchTerm) {
                return StateService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };
            $scope.searchPropertyType = function (searchTerm) {
                console.log("Hello");
                return PropertyTypeService.findByNumberOfBhkLike({
                    'numberOfBhkLike': searchTerm
                }).$promise;
            };
            $scope.searchRoad = function (searchTerm) {
                console.log("Hello");
                return RoadService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };
            $scope.searchTransportation = function (searchTerm) {
                return TransportationService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };
            $scope.searchProject = function (searchTerm) {
                return ProjectService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };
            $scope.searchNearbyProjects = function (searchTerm) {
                return ProjectService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };
            $scope.searchWorkplaces = function (searchTerm) {
                return AmenityDetailService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };
            $scope.searchBasicAmenities = function (searchTerm) {
                return AmenityDetailService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };
            $scope.searchLuxuryAmenities = function (searchTerm) {
                return AmenityDetailService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };
            $scope.searchApprovedBanks = function (searchTerm) {
                return BankService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };
            $scope.searchAmenitiesWithinProject = function (searchTerm) {
                return PrivateAmenitiesService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };
            $scope.searchCities = function (searchTerm) {
                console.log("State Id :%O", $scope.editableProperty.stateId);
                if ($scope.stateId === undefined) {
                    return CityService.findByNameLike({
                        'name': searchTerm
                    }).$promise;
                } else {
                    return CityService.findByNameAndStateId({
                        'name': searchTerm,
                        'stateId': $scope.stateId
                    }).$promise;
                }
            };
            $scope.searchLocations = function (searchTerm) {
                console.log("Search Term :%O", searchTerm);
                console.log("City Id :%O", $scope.editableProperty.cityId);
                if ($scope.editableProperty.cityId === undefined) {
                    console.log("Coming to if ??");
                    return LocationService.findByNameLike({
                        'name': searchTerm
                    }).$promise;
                } else {
                    console.log("Coming to Else ??");
                    return LocationService.findByNameAndCityId({
                        'name': searchTerm,
                        'cityId': $scope.editableProperty.cityId
                    }).$promise;
                }
            };
             $scope.setUnit = function (unit) {
                $scope.editableProperty.unitObject = unit;
                $scope.editableProperty.unit = unit.id;
            };
            $scope.searchUnit = function (searchTerm) {
                return UnitService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };
            $scope.getLocationStep = function (locationstep) {
                console.log("Location Step :%O", locationstep);
                $scope.selection = locationstep;
                if (locationstep === "Basic Details") {
                    console.log("Hello baisc details");
//                    $scope.amenityCodes = AmenityCodeService.findByTabName({
//                       'name' : AMENITIES
//                    });
                    $scope.myValue = true;
                } else if (locationstep === "Connectivity") {
                    console.log("Hello Connectivity");
                    $scope.connectivity = true;
                    $scope.myValue = false;
                    $scope.costing = false;
                    $scope.neighbourhood = false;
                    $scope.livability = false;
                    $scope.marketability = false;
                    $scope.sellerCommisionAgreement = false;
                    $scope.unitDetails = false;
//                    $scope.clear = function(){
//                        console.log("Coming to clear function??");
//                      $scope.transportation = "";  
//                    };
                }
                else if (locationstep === "Costing") {
                    console.log("Hello Costing");
                    $scope.costing = true;
                    $scope.connectivity = false;
                    $scope.myValue = false;
                    $scope.neighbourhood = false;
                    $scope.livability = false;
                    $scope.marketability = false;
                    $scope.sellerCommisionAgreement = false;
                    $scope.unitDetails = false;
                }
                else if (locationstep === "Neighbourhood") {
                    console.log("Hello demand for rental");
                    $scope.neighbourhood = true;
                    $scope.myValue = false;
                    $scope.costing = false;
                    $scope.connectivity = false;
                    $scope.marketability = false;
                    $scope.livability = false;
                    $scope.sellerCommisionAgreement = false;
                    $scope.unitDetails = false;
                }
                else if (locationstep === "Livability") {
                    console.log("Hello demand for livability");
                    $scope.livability = true;
                    $scope.myValue = false;
                    $scope.costing = false;
                    $scope.connectivity = false;
                    $scope.marketability = false;
                    $scope.neighbourhood = false;
                    $scope.sellerCommisionAgreement = false;
                    $scope.unitDetails = false;
                }
                else if (locationstep === "Marketability") {
                    console.log("Marketability");
                    $scope.marketability = true;
                    $scope.myValue = false;
                    $scope.costing = false;
                    $scope.connectivity = false;
                    $scope.neighbourhood = false;
                    $scope.livability = false;
                    $scope.sellerCommisionAgreement = false;
                    $scope.unitDetails = false;
                }
                else if (locationstep === "Seller Commision Agreement") {
                    console.log("Hello Seller COmmision Agreement");
                    $scope.sellerCommisionAgreement = true;
                    $scope.myValue = false;
                    $scope.costing = false;
                    $scope.connectivity = false;
                    $scope.neighbourhood = false;
                    $scope.livability = false;
                    $scope.marketability = false;
                    $scope.unitDetails = false;
                }
                else if (locationstep === "Unit Details") {
                    console.log("Hello Unit Details");
                    $scope.unitDetails = true;
                    $scope.myValue = false;
                    $scope.costing = false;
                    $scope.connectivity = false;
                    $scope.neighbourhood = false;
                    $scope.livability = false;
                    $scope.marketability = false;
                    $scope.sellerCommisionAgreement = false;
                }
                else {
                    console.log("Nothing");
                    $scope.livability = false;
                    $scope.myValue = false;
                    $scope.costing = false;
                    $scope.connectivity = false;
                    $scope.neighbourhood = false;
                    $scope.marketability = false;
                    $scope.sellerCommisionAgreement = false;
                    $scope.unitDetails = false;
                }
            };
//            $scope.countries = CountryService.query();
//            $scope.states = StateService.query();
//            $scope.cities = CityService.query();
//
//            $scope.editableProperty = PropertyService.get({'id': $stateParams.propertyId});
//
//            $scope.datePicker = {
//                opened: false,
//                toggle: function () {
//                    this.opened = !this.opened;
//                }
//            };
////            $scope.setCountry = function (country) {
////                $scope.editableProperty.countryId = country.id;
////                $scope.editableProperty.country = country;
////            };
////            $scope.setState = function (state) {
////                $scope.editableProperty.stateId = state.id;
////                $scope.editableProperty.state = state;
////            };
//            $scope.setCity = function (city) {
//                $scope.editableProperty.cityId = city.id;
//                $scope.editableProperty.city = city;
//            };
//            $scope.saveProperty = function (property) {
//                property.$save(function () {
//                    $state.go('admin.masters_property', null, {'reload': true});
//                });
//            };
        })
        .controller('PropertyDeleteController', function (PropertyService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editableProperty = PropertyService.get({'id': $stateParams.propertyId});

            $scope.deleteProperty = function (property) {
                property.$delete(function () {
                    $state.go('admin.masters_property', null, {'reload': true});
                });
            };
        });
