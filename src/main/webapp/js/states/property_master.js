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

            });

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

            };
            uploader.onErrorItem = function ($scope) {
                $scope.uploadFailed = true;
                $scope.uploadInProgress = false;
                $scope.uploadSuccess = false;
            };
            uploader.onCompleteItem = function ($scope, response, status) {

                if (status === 200) {

                    $scope.uploadInProgress = false;
                    $scope.uploadFailed = false;
                    $scope.uploadSuccess = true;
                    $scope.enableSaveButton = true;

                } else if (status === 500)
                {
                    $scope.uploadInProgress = false;
                    $scope.uploadFailed = false;

                } else {

                    $scope.uploadInProgress = false;
                    $scope.uploadFailed = true;
                }

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
        .controller('PropertyAddController', function (ProjectService, LocationTypeService, UnitService, PrivateAmenitiesService, BankService, AmenityDetailService, TransportationService, RoadService, PropertyTypeService, LocationService, CityService, StateService, CountryService, PropertyService, $scope, $stateParams, $state, paginationLimit) {
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
            $scope.saveProperty = function (property) {

                PropertyService.save(property, function () {
                    $state.go('admin.masters_property', null, {'reload': true});
                });
            };
            $scope.setState = function (state) {

                $scope.stateId = state.id;
                $scope.state = state;

            };
            $scope.setPropertyType = function (property) {

                $scope.editableProperty.propertySize = property.id;
                $scope.editableProperty.propertySizeObject = property;
            };
            $scope.setWorkplaces = function (workplaces) {

                $scope.workplacesDisplay.push(workplaces);
                $scope.workplaces = "";
                $scope.editableProperty.workplaces.push(workplaces.id);
            };
            $scope.removeWorkplaces = function (workplaces) {

                var index = $scope.workplacesDisplay.indexOf(workplaces);
                var index1 = $scope.editableProperty.workplaces.indexOf(workplaces.id);
                $scope.workplacesDisplay.splice(index, 1);
                $scope.editableProperty.workplaces.splice(index1, 1);

            };
            $scope.setBasicAmenities = function (basicAmenities) {

                $scope.basicAmenitiesDisplay.push(basicAmenities);
                $scope.$parent.basicAmenities = "";
                $scope.editableProperty.basicAmenities.push(basicAmenities.id);
            };
            $scope.removeBasicAmenities = function (basicAmenities) {

                var index = $scope.basicAmenitiesDisplay.indexOf(basicAmenities);
                var index1 = $scope.editableProperty.basicAmenities.indexOf(basicAmenities.id);
                $scope.basicAmenitiesDisplay.splice(index, 1);
                $scope.editableProperty.basicAmenities.splice(index1, 1);

            };
            $scope.setLuxuryAmenities = function (luxuryAmenities) {

                $scope.luxuryAmenitiesDisplay.push(luxuryAmenities);
                $scope.luxuryAmenities = "";
                $scope.editableProperty.luxuryAmenities.push(luxuryAmenities.id);
            };
            $scope.removeLuxuryAmenities = function (luxuryAmenities) {

                var index = $scope.luxuryAmenitiesDisplay.indexOf(luxuryAmenities);
                var index1 = $scope.editableProperty.luxuryAmenities.indexOf(luxuryAmenities.id);
                $scope.luxuryAmenitiesDisplay.splice(index, 1);
                $scope.editableProperty.luxuryAmenities.splice(index1, 1);

            };
            $scope.setApprovedBanks = function (approvedBanks) {

                $scope.approvedBanksDisplay.push(approvedBanks);
                $scope.approvedBanks = "";
                $scope.editableProperty.approvedBanks.push(approvedBanks.id);

            };
            $scope.removeApprovedBanks = function (approvedBanks) {

                var index = $scope.approvedBanksDisplay.indexOf(approvedBanks);
                var index1 = $scope.editableProperty.approvedBanks.indexOf(approvedBanks.id);
                $scope.approvedBanksDisplay.splice(index, 1);
                $scope.editableProperty.approvedBanks.splice(index1, 1);

            };
            $scope.setAmenitiesWithinProject = function (amenitiesWithinProject) {

                $scope.amenitiesWithinProjectDisplay.push(amenitiesWithinProject);
                $scope.amenitiesWithinProject = "";
                $scope.editableProperty.privateAmenities.push(amenitiesWithinProject.id);

            };
            $scope.removeAmenitiesWithinProject = function (amenitiesWithinProjects) {

                var index = $scope.amenitiesWithinProjectDisplay.indexOf(amenitiesWithinProjects);
                var index1 = $scope.editableProperty.privateAmenities.indexOf(amenitiesWithinProjects.id);
                $scope.amenitiesWithinProjectDisplay.splice(index, 1);
                $scope.editableProperty.privateAmenities.splice(index1, 1);

            };
            $scope.setTransportation = function (transportation) {

                $scope.publicTransportDisplay.push(transportation);
                $scope.publicTransport = null;
                $scope.editableProperty.publicTransport.push(transportation.id);

            };
            $scope.removeTransportation = function (transportation) {

                var index = $scope.publicTransportDisplay.indexOf(transportation);
                var index1 = $scope.editableProperty.publicTransport.indexOf(transportation.id);
                $scope.publicTransportDisplay.splice(index, 1);
                $scope.editableProperty.publicTransport.splice(index1, 1);

            };
            $scope.setNearbyProjects = function (nearbyProjects) {

                $scope.projectsNearbyDisplay.push(nearbyProjects);
                $scope.projectsNearby = null;

                $scope.editableProperty.projectsNearby.push(nearbyProjects.id);

            };
            $scope.removeProjectsNearby = function (projectsNearby) {

                var index = $scope.projectsNearbyDisplay.indexOf(projectsNearby);
                var index1 = $scope.editableProperty.projectsNearby.indexOf(projectsNearby.id);
                $scope.projectsNearbyDisplay.splice(index, 1);
                $scope.editableProperty.projectsNearby.splice(index1, 1);

            };
            $scope.setCity = function (city) {

                $scope.editableProperty.cityId = city.id;
                $scope.editableProperty.city = city;

            };
            $scope.setLocation = function (location) {
                $scope.editableProperty.locationId = location.id;
                $scope.editableProperty.location = location;

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

                return PropertyTypeService.findByNumberOfBhkLike({
                    'numberOfBhkLike': searchTerm
                }).$promise;
            };
            $scope.searchRoad = function (searchTerm) {

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

                if ($scope.editableProperty.cityId === undefined) {

                    return LocationService.findByNameLike({
                        'name': searchTerm
                    }).$promise;
                } else {

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


            $scope.$watch('editableProperty.locationId', function (locationId) {

                LocationService.get({
                    'id': locationId
                }, function (locationObject) {

                    LocationTypeService.get({
                        'id': locationObject.locationTypeId
                    }, function (locationTypeObject) {
                        if (locationTypeObject.name === "WITHIN_CITY") {
                            $scope.editableProperty.bus = true;
                            $scope.editableProperty.auto = true;
                            $scope.editableProperty.taxi = true;
                            $scope.editableProperty.metro = true;
                        }
                        else {
                            $scope.editableProperty.bus = false;
                            $scope.editableProperty.auto = false;
                            $scope.editableProperty.taxi = false;
                            $scope.editableProperty.metro = false;
                        }
                    });
                });

            });


            $scope.getLocationStep = function (locationstep) {

                $scope.selection = locationstep;
                if (locationstep === "Basic Details") {

                    $scope.myValue = true;
                } else if (locationstep === "Connectivity") {

                    $scope.connectivity = true;
                    $scope.myValue = false;
                    $scope.costing = false;
                    $scope.neighbourhood = false;
                    $scope.livability = false;
                    $scope.marketability = false;
                    $scope.sellerCommisionAgreement = false;
                    $scope.unitDetails = false;
                }
                else if (locationstep === "Costing") {

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
        .controller('PropertyEditController', function (ProjectService, UnitService, LocationTypeService, PrivateAmenitiesService, BankService, AmenityDetailService, TransportationService, RoadService, PropertyTypeService, LocationService, CityService, StateService, CountryService, PropertyService, $scope, $stateParams, $state, paginationLimit) {
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
                $scope.editableProperty.possessionDate = possessionDateLong;

                var yearOfConstruction = new Date($scope.editableProperty.yearOfConstruction);
                var yearOfConstructionLong = yearOfConstruction * 1;
                $scope.editableProperty.yearOfConstruction = yearOfConstructionLong;

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

                    PrivateAmenitiesService.get({
                        'id': privateAmenities
                    }, function (data) {
                        $scope.amenitiesWithinProjectDisplay.push(data);
                    });
                });
                $scope.projectsNearbyDisplay = [];
                angular.forEach($scope.editableProperty.projectsNearby, function (projectsNearby) {

                    ProjectService.get({
                        'id': projectsNearby
                    }, function (data) {
                        $scope.projectsNearbyDisplay.push(data);
                    });
                });
            });

            $scope.editableProperty.projectsNearby = [];

            $scope.editableProperty.publicTransport = [];

            $scope.editableProperty.workplaces = [];

            $scope.editableProperty.basicAmenities = [];

            $scope.editableProperty.luxuryAmenities = [];

            $scope.editableProperty.approvedBanks = [];

            $scope.editableProperty.privateAmenities = [];
            $scope.$watch('editableProperty.constructionStage', function (data) {

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

                property.$save(property, function () {
                    $state.go('admin.masters_property', null, {'reload': true});
                });
            };
            $scope.setState = function (state) {

                $scope.stateId = state.id;
                $scope.state = state;

            };
            $scope.setPropertyType = function (property) {

                $scope.editableProperty.propertySize = property.id;
                $scope.editableProperty.propertySizeObject = property;
            };

            $scope.setWorkplaces = function (workplaces) {

                $scope.workplacesDisplay.push(workplaces);
                $scope.workplaces = "";
                $scope.editableProperty.workplaces.push(workplaces.id);
            };
            $scope.removeWorkplaces = function (workplaces) {

                var index = $scope.workplacesDisplay.indexOf(workplaces);
                var index1 = $scope.editableProperty.workplaces.indexOf(workplaces.id);
                $scope.workplacesDisplay.splice(index, 1);
                $scope.editableProperty.workplaces.splice(index1, 1);

            };
            $scope.setBasicAmenities = function (basicAmenities) {

                $scope.basicAmenitiesDisplay.push(basicAmenities);
                $scope.$parent.basicAmenities = "";
                $scope.editableProperty.basicAmenities.push(basicAmenities.id);
            };
            $scope.removeBasicAmenities = function (basicAmenities) {

                var index = $scope.basicAmenitiesDisplay.indexOf(basicAmenities);
                var index1 = $scope.editableProperty.basicAmenities.indexOf(basicAmenities.id);
                $scope.basicAmenitiesDisplay.splice(index, 1);
                $scope.editableProperty.basicAmenities.splice(index1, 1);

            };
            $scope.setLuxuryAmenities = function (luxuryAmenities) {

                $scope.luxuryAmenitiesDisplay.push(luxuryAmenities);
                $scope.luxuryAmenities = "";
                $scope.editableProperty.luxuryAmenities.push(luxuryAmenities.id);
            };
            $scope.removeLuxuryAmenities = function (luxuryAmenities) {

                var index = $scope.luxuryAmenitiesDisplay.indexOf(luxuryAmenities);
                var index1 = $scope.editableProperty.luxuryAmenities.indexOf(luxuryAmenities.id);
                $scope.luxuryAmenitiesDisplay.splice(index, 1);
                $scope.editableProperty.luxuryAmenities.splice(index1, 1);

            };
            $scope.setApprovedBanks = function (approvedBanks) {

                $scope.approvedBanksDisplay.push(approvedBanks);
                $scope.approvedBanks = "";
                $scope.editableProperty.approvedBanks.push(approvedBanks.id);

            };
            $scope.removeApprovedBanks = function (approvedBanks) {

                var index = $scope.approvedBanksDisplay.indexOf(approvedBanks);
                var index1 = $scope.editableProperty.approvedBanks.indexOf(approvedBanks.id);
                $scope.approvedBanksDisplay.splice(index, 1);
                $scope.editableProperty.approvedBanks.splice(index1, 1);

            };
            $scope.setAmenitiesWithinProject = function (amenitiesWithinProject) {

                $scope.amenitiesWithinProjectDisplay.push(amenitiesWithinProject);
                $scope.amenitiesWithinProject = "";
                $scope.editableProperty.privateAmenities.push(amenitiesWithinProject.id);

            };
            $scope.removeAmenitiesWithinProject = function (amenitiesWithinProjects) {

                var index = $scope.amenitiesWithinProjectDisplay.indexOf(amenitiesWithinProjects);
                var index1 = $scope.editableProperty.privateAmenities.indexOf(amenitiesWithinProjects.id);
                $scope.amenitiesWithinProjectDisplay.splice(index, 1);
                $scope.editableProperty.privateAmenities.splice(index1, 1);

            };
            $scope.setTransportation = function (transportation) {

                $scope.publicTransportDisplay.push(transportation);
                $scope.publicTransport = null;

                $scope.editableProperty.publicTransport.push(transportation.id);

            };
            $scope.removeTransportation = function (transportation) {

                var index = $scope.publicTransportDisplay.indexOf(transportation);
                var index1 = $scope.editableProperty.publicTransport.indexOf(transportation.id);
                $scope.publicTransportDisplay.splice(index, 1);
                $scope.editableProperty.publicTransport.splice(index1, 1);

            };
            $scope.setNearbyProjects = function (nearbyProjects) {

                $scope.projectsNearbyDisplay.push(nearbyProjects);
                $scope.projectsNearby = null;
                $scope.editableProperty.projectsNearby.push(nearbyProjects.id);

            };
            $scope.removeProjectsNearby = function (projectsNearby) {

                var index = $scope.projectsNearbyDisplay.indexOf(projectsNearby);
                var index1 = $scope.editableProperty.projectsNearby.indexOf(projectsNearby.id);
                $scope.projectsNearbyDisplay.splice(index, 1);
                $scope.editableProperty.projectsNearby.splice(index1, 1);

            };
            $scope.setCity = function (city) {

                $scope.editableProperty.cityId = city.id;
                $scope.editableProperty.city = city;

            };
            $scope.setLocation = function (location) {
                $scope.editableProperty.locationId = location.id;
                $scope.editableProperty.location = location;

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

                return PropertyTypeService.findByNumberOfBhkLike({
                    'numberOfBhkLike': searchTerm
                }).$promise;
            };
            $scope.searchRoad = function (searchTerm) {

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

            $scope.$watch('editableProperty.locationId', function (locationId) {

                LocationService.get({
                    'id': locationId
                }, function (locationObject) {

                    LocationTypeService.get({
                        'id': locationObject.locationTypeId
                    }, function (locationTypeObject) {
                        if (locationTypeObject.name === "WITHIN_CITY") {
                            $scope.editableProperty.bus = true;
                            $scope.editableProperty.auto = true;
                            $scope.editableProperty.taxi = true;
                            $scope.editableProperty.metro = true;
                        }
                        else {
                            $scope.editableProperty.bus = false;
                            $scope.editableProperty.auto = false;
                            $scope.editableProperty.taxi = false;
                            $scope.editableProperty.metro = false;
                        }
                    });
                });

            });

            $scope.searchLocations = function (searchTerm) {

                if ($scope.editableProperty.cityId === undefined) {

                    return LocationService.findByNameLike({
                        'name': searchTerm
                    }).$promise;
                } else {

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

                $scope.selection = locationstep;
                if (locationstep === "Basic Details") {
                    $scope.myValue = true;
                } else if (locationstep === "Connectivity") {

                    $scope.connectivity = true;
                    $scope.myValue = false;
                    $scope.costing = false;
                    $scope.neighbourhood = false;
                    $scope.livability = false;
                    $scope.marketability = false;
                    $scope.sellerCommisionAgreement = false;
                    $scope.unitDetails = false;
                }
                else if (locationstep === "Costing") {

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
        .controller('PropertyDeleteController', function (PropertyService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editableProperty = PropertyService.get({'id': $stateParams.propertyId});

            $scope.deleteProperty = function (property) {
                property.$delete(function () {
                    $state.go('admin.masters_property', null, {'reload': true});
                });
            };
        });
