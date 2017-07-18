angular.module("safedeals.states.project_master", ['ngComboDatePicker'])
        .config(function ($stateProvider, templateRoot) {
            $stateProvider.state('admin.masters_project', {
                'url': '/project_master?offset',
                'templateUrl': templateRoot + '/masters/project/list.html',
                'controller': 'ProjectListController'
            });
            $stateProvider.state('admin.masters_project.add', {
                'url': '/add',
                'templateUrl': templateRoot + '/masters/project/form.html',
                'controller': 'ProjectAddController'
            });
            $stateProvider.state('admin.masters_project.edit', {
                'url': '/:projectId/edit',
                'templateUrl': templateRoot + '/masters/project/form.html',
                'controller': 'ProjectEditController'
            });
            $stateProvider.state('admin.masters_project.delete', {
                'url': '/:projectId/delete',
                'templateUrl': templateRoot + '/masters/project/delete.html',
                'controller': 'ProjectDeleteController'
            });
            $stateProvider.state('admin.masters_project.photo', {
                'url': '/:projectId/photo',
                'templateUrl': templateRoot + '/masters/project/photo.html',
                'controller': 'ProjectPhotoController'
            });
            $stateProvider.state('admin.masters_project.view', {
                'url': '/:projectId/view',
                'templateUrl': templateRoot + '/masters/project/view.html',
                'controller': 'ProjectViewController'
            });
            $stateProvider.state('admin.masters_project.info', {
                'url': '/:projectId/info',
                'templateUrl': templateRoot + '/masters/project/info.html',
                'controller': 'ProjectInfoController'
            });
        })
        .controller('ProjectListController', function (CityService, LocationService, CountryService, StateService, ProjectService, $scope, $stateParams, $state, paginationLimit) {
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

            $scope.nextProjects = ProjectService.query({
                'offset': $scope.nextOffset
            });

            $scope.projects = ProjectService.query({
                'offset': $scope.currentOffset
            }, function (project) {
                angular.forEach($scope.projects, function (projects) {

                    if (projects.cityId !== null) {
                        projects.city = CityService.get({
                            'id': projects.cityId
                        });
                    }
                    if (projects.locationId !== null) {
                        projects.location = LocationService.get({
                            'id': projects.locationId
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
        .controller('ProjectAddController', function (ProjectService, PrivateAmenitiesService, BankService, AmenityDetailService, TransportationService, RoadService, PropertyTypeService, LocationService, CityService, StateService, CountryService, PropertyService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editableProject = {};
            $scope.propertiesTypeDisplay = [];
            $scope.editableProject.propertiesType = [];
            $scope.publicTransportDisplay = [];
            $scope.editableProject.publicTransport = [];
            $scope.workplacesDisplay = [];
            $scope.editableProject.workplaces = [];
            $scope.basicAmenitiesDisplay = [];
            $scope.editableProject.basicAmenities = [];
            $scope.luxuryAmenitiesDisplay = [];
            $scope.editableProject.luxuryAmenities = [];
            $scope.approvedBanksDisplay = [];
            $scope.editableProject.approvedBanks = [];
            $scope.amenitiesWithinProjectDisplay = [];
            $scope.editableProject.privateAmenities = [];
            $scope.locationSteps = [
                'Basic Details',
                'Connectivity',
                'Costing',
                'Demand For Rental/Resale',
                'Marketability',
                'Project Highlights',
                'Unit Details',
                'Sellers Credability'
                
            ];
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

            $scope.saveProject = function (project) {
                console.log("Project :%O", project);
                ProjectService.save(project, function () {
                    $state.go('admin.masters_project', null, {'reload': true});
                });
            };
            $scope.setState = function (state) {
                console.log("xyz", state);
                $scope.editableProject.stateId = state.id;
                $scope.editableProject.state = state;
                console.log("$scope.editableProject.state ", $scope.editableProject.state);
            };
            $scope.setPropertyType = function (property) {
                console.log("xyz", property);
                $scope.propertiesTypeDisplay.push(property);
                $scope.propertyType = "";
                $scope.editableProject.propertiesType.push(property.id);
                console.log("Main Array :%O", $scope.editableProject.propertiesType);
                console.log("Properties Type Array :%O", $scope.propertiesTypeDisplay);
            };
            $scope.removePropertyType = function (propertyType) {
                console.log("Getting the thing :%O", propertyType);
                var index = $scope.propertiesTypeDisplay.indexOf(propertyType);
                var index1 = $scope.editableProject.propertiesType.indexOf(propertyType.id);
                $scope.propertiesTypeDisplay.splice(index, 1);
                $scope.editableProject.propertiesType.splice(index1, 1);
                console.log("Updated Type Display :%O", $scope.propertiesTypeDisplay);
                console.log("Updated %O", $scope.editableProject.propertiesType);
            };
            $scope.setWorkplaces = function (workplaces) {
                console.log("xyz", workplaces);
                $scope.workplacesDisplay.push(workplaces);
                $scope.workplaces = "";
                $scope.editableProject.workplaces.push(workplaces.id);
            };
            $scope.removeWorkplaces = function (workplaces) {
                console.log("Getting the thing :%O", workplaces);
                var index = $scope.workplacesDisplay.indexOf(workplaces);
                var index1 = $scope.editableProject.workplaces.indexOf(workplaces.id);
                $scope.workplacesDisplay.splice(index, 1);
                $scope.editableProject.workplaces.splice(index1, 1);
                console.log("Updated Type Display :%O", $scope.workplacesDisplay);
                console.log("Updated %O", $scope.editableProject.workplaces);
            };
            $scope.setBasicAmenities = function (basicAmenities) {
                console.log("xyz", basicAmenities);
                $scope.basicAmenitiesDisplay.push(basicAmenities);
                $scope.$parent.basicAmenities = "";
                $scope.editableProject.basicAmenities.push(basicAmenities.id);
            };
            $scope.removeBasicAmenities = function (basicAmenities) {
                console.log("Getting the thing :%O", basicAmenities);
                var index = $scope.basicAmenitiesDisplay.indexOf(basicAmenities);
                var index1 = $scope.editableProject.basicAmenities.indexOf(basicAmenities.id);
                $scope.basicAmenitiesDisplay.splice(index, 1);
                $scope.editableProject.basicAmenities.splice(index1, 1);
                console.log("Updated Type Display :%O", $scope.basicAmenitiesDisplay);
                console.log("Updated %O", $scope.editableProject.basicAmenities);
            };
            $scope.setLuxuryAmenities = function (luxuryAmenities) {
                console.log("xyz", luxuryAmenities);
                $scope.luxuryAmenitiesDisplay.push(luxuryAmenities);
                $scope.luxuryAmenities = "";
                $scope.editableProject.luxuryAmenities.push(luxuryAmenities.id);
            };
            $scope.removeLuxuryAmenities = function (luxuryAmenities) {
                console.log("Getting the thing :%O", luxuryAmenities);
                var index = $scope.luxuryAmenitiesDisplay.indexOf(luxuryAmenities);
                var index1 = $scope.editableProject.luxuryAmenities.indexOf(luxuryAmenities.id);
                $scope.luxuryAmenitiesDisplay.splice(index, 1);
                $scope.editableProject.luxuryAmenities.splice(index1, 1);
                console.log("Updated Type Display :%O", $scope.luxuryAmenitiesDisplay);
                console.log("Updated %O", $scope.editableProject.luxuryAmenities);
            };
            $scope.setApprovedBanks = function (approvedBanks) {
                console.log("xyz", approvedBanks);
                $scope.approvedBanksDisplay.push(approvedBanks);
                $scope.approvedBanks = "";
                $scope.editableProject.approvedBanks.push(approvedBanks.id);
                console.log("List :%O", $scope.approvedBanksDisplay);
            };
            $scope.removeApprovedBanks = function (approvedBanks) {
                console.log("Getting the thing :%O", approvedBanks);
                var index = $scope.approvedBanksDisplay.indexOf(approvedBanks);
                var index1 = $scope.editableProject.approvedBanks.indexOf(approvedBanks.id);
                $scope.approvedBanksDisplay.splice(index, 1);
                $scope.editableProject.approvedBanks.splice(index1, 1);
                console.log("Updated Type Display :%O", $scope.approvedBanksDisplay);
                console.log("Updated %O", $scope.editableProject.approvedBanks);
            };
            $scope.setAmenitiesWithinProject = function (amenitiesWithinProject) {
                console.log("xyz", amenitiesWithinProject);
                $scope.amenitiesWithinProjectDisplay.push(amenitiesWithinProject);
                $scope.amenitiesWithinProject = "";
                $scope.editableProject.privateAmenities.push(amenitiesWithinProject.id);
                console.log("List :%O", $scope.amenitiesWithinProjectDisplay);
            };
            $scope.removeAmenitiesWithinProject = function (amenitiesWithinProjects) {
                console.log("Getting the thing :%O", amenitiesWithinProjects);
                var index = $scope.amenitiesWithinProjectDisplay.indexOf(amenitiesWithinProjects);
                var index1 = $scope.editableProject.privateAmenities.indexOf(amenitiesWithinProjects.id);
                $scope.amenitiesWithinProjectDisplay.splice(index, 1);
                $scope.editableProject.privateAmenities.splice(index1, 1);
                console.log("Updated Type Display :%O", $scope.amenitiesWithinProjectDisplay);
                console.log("Updated %O", $scope.editableProject.privateAmenities);
            };
            $scope.setTransportation = function (transportation) {
                console.log("xyz t", transportation);
                $scope.publicTransportDisplay.push(transportation);
                $scope.publicTransport = null;
//                $scope.clear();
                $scope.editableProject.publicTransport.push(transportation.id);
                console.log("Main Array :%O", $scope.editableProject.publicTransport);
                console.log("Public Transport Array :%O", $scope.publicTransportDisplay);
            };
            $scope.removeTransportation = function (transportation) {
                console.log("Getting the thing :%O", transportation);
                var index = $scope.publicTransportDisplay.indexOf(transportation);
                var index1 = $scope.editableProject.publicTransport.indexOf(transportation.id);
                $scope.publicTransportDisplay.splice(index, 1);
                $scope.editableProject.publicTransport.splice(index1, 1);
                console.log("Updated Type Display :%O", $scope.publicTransportDisplay);
                console.log("Updated %O", $scope.editableProject.publicTransport);
            };
            $scope.setCity = function (city) {
                console.log("xyz", city);
                $scope.editableProject.cityId = city.id;
                $scope.editableProject.city = city;
                console.log("$scope.editableFranchise.city ", $scope.editableProject.city);
            };
            $scope.setLocation = function (location) {
                $scope.editableProject.locationId = location.id;
                $scope.editableProject.location = location;
                console.log("$scope.editableFranchise.location ", $scope.editableProject.location);
            };
            $scope.setRoad = function (road) {
                $scope.editableProject.majorApproachRoad = road.id;
                $scope.editableProject.road = road;
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
                console.log("State Id :%O", $scope.editableProject.stateId);
                if ($scope.editableProject.stateId === undefined) {
                    return CityService.findByNameLike({
                        'name': searchTerm
                    }).$promise;
                } else {
                    return CityService.findByNameAndStateId({
                        'name': searchTerm,
                        'stateId': $scope.editableProject.stateId
                    }).$promise;
                }
            };
            $scope.searchLocations = function (searchTerm) {
                console.log("Search Term :%O", searchTerm);
                console.log("City Id :%O", $scope.editableProject.cityId);
                if ($scope.editableProject.cityId === undefined) {
                    console.log("Coming to if ??");
                    return LocationService.findByNameLike({
                        'name': searchTerm
                    }).$promise;
                } else {
                    console.log("Coming to Else ??");
                    return LocationService.findByNameAndCityId({
                        'name': searchTerm,
                        'cityId': $scope.editableProject.cityId
                    }).$promise;
                }
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
                    $scope.demandForRentalResale = false;
                    $scope.marketability = false;
                    $scope.projectHighlights = false;
                    $scope.sellersCredibality = false;
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
                    $scope.demandForRentalResale = false;
                    $scope.marketability = false;
                    $scope.projectHighlights = false;
                    $scope.sellersCredibality = false;
                    $scope.unitDetails = false;
                }
                else if (locationstep === "Demand For Rental/Resale") {
                    console.log("Hello demand for rental");
                    $scope.demandForRentalResale = true;
                    $scope.myValue = false;
                    $scope.costing = false;
                    $scope.connectivity = false;
                    $scope.marketability = false;
                    $scope.projectHighlights = false;
                    $scope.sellersCredibality = false;
                    $scope.unitDetails = false;
                }
                else if (locationstep === "Marketability") {
                    console.log("Marketability");
                    $scope.marketability = true;
                    $scope.myValue = false;
                    $scope.costing = false;
                    $scope.connectivity = false;
                    $scope.demandForRentalResale = false;
                    $scope.projectHighlights = false;
                    $scope.sellersCredibality = false;
                    $scope.unitDetails = false;
                }
                else if (locationstep === "Project Highlights") {
                    console.log("Hello Project Highlights");
                    $scope.projectHighlights = true;
                    $scope.myValue = false;
                    $scope.costing = false;
                    $scope.connectivity = false;
                    $scope.demandForRentalResale = false;
                    $scope.marketability = false;
                    $scope.sellersCredibality = false;
                    $scope.unitDetails = false;
                }
                else if (locationstep === "Sellers Credability") {
                    console.log("Hello Sellers Credability");
                    $scope.sellersCredability = true;
                    $scope.myValue = false;
                    $scope.costing = false;
                    $scope.connectivity = false;
                    $scope.demandForRentalResale = false;
                    $scope.marketability = false;
                    $scope.projectHighlights = false;
                    $scope.unitDetails = false;
                }
                else if (locationstep === "Unit Details") {
                    console.log("Hello Unit Details");
                    $scope.unitDetails = true;
                    $scope.myValue = false;
                    $scope.costing = false;
                    $scope.connectivity = false;
                    $scope.demandForRentalResale = false;
                    $scope.marketability = false;
                    $scope.sellersCredibality = false;
                    $scope.projectHighlights = false;
                }
                else {
                    console.log("Nothing");
                    $scope.projectHighlights = false;
                    $scope.myValue = false;
                    $scope.costing = false;
                    $scope.connectivity = false;
                    $scope.demandForRentalResale = false;
                    $scope.marketability = false;
                    $scope.sellersCredibality = false;
                    $scope.unitDetails = false;
                }
            };
        })
        .controller('ProjectEditController', function (LocationService, PrivateAmenitiesService, BankService, AmenityDetailService, PropertyTypeService, TransportationService, RoadService, CityService, StateService, CountryService, ProjectService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editableProject = ProjectService.get({
                'id': $stateParams.projectId
            }, function () {
                $scope.editableProject.state = StateService.get({
                    id: $scope.editableProject.stateId
                });
                $scope.editableProject.city = CityService.get({
                    id: $scope.editableProject.cityId
                });
                $scope.editableProject.location = LocationService.get({
                    id: $scope.editableProject.locationId
                });
                $scope.editableProject.road = RoadService.get({
                    id: $scope.editableProject.majorApproachRoad
                });
                var bookingStartDate = new Date($scope.editableProject.bookingStartDate);
                var bookingStartDatelong = bookingStartDate * 1;
                $scope.editableProject.bookingStartDate = bookingStartDatelong;
                var completionDate = new Date($scope.editableProject.completionDate);
                var completionDateLong = completionDate * 1;
                $scope.editableProject.completionDate = completionDateLong;
                var offerValidTill = new Date($scope.editableProject.offerValidTill);
                var offerValidTillLong = offerValidTill * 1;
                $scope.editableProject.offerValidTill = offerValidTillLong;
                $scope.propertiesTypeDisplay = [];
                angular.forEach($scope.editableProject.propertiesType, function (propertyType) {
                    PropertyTypeService.get({
                        'id': propertyType
                    }, function (data) {
                        $scope.propertiesTypeDisplay.push(data);
                    });
                });
                $scope.publicTransportDisplay = [];
                angular.forEach($scope.editableProject.publicTransport, function (publicTransport) {
                    TransportationService.get({
                        'id': publicTransport
                    }, function (data) {
                        $scope.publicTransportDisplay.push(data);
                    });
                });
                $scope.workplacesDisplay = [];
                angular.forEach($scope.editableProject.workplaces, function (workplace) {
                    AmenityDetailService.get({
                        'id': workplace
                    }, function (data) {
                        $scope.workplacesDisplay.push(data);
                    });
                });
                $scope.basicAmenitiesDisplay = [];
                angular.forEach($scope.editableProject.basicAmenities, function (basicAmenities) {
                    AmenityDetailService.get({
                        'id': basicAmenities
                    }, function (data) {
                        $scope.basicAmenitiesDisplay.push(data);
                    });
                });
                $scope.luxuryAmenitiesDisplay = [];
                angular.forEach($scope.editableProject.luxuryAmenities, function (luxuryAmenities) {
                    AmenityDetailService.get({
                        'id': luxuryAmenities
                    }, function (data) {
                        $scope.luxuryAmenitiesDisplay.push(data);
                    });
                });
                $scope.approvedBanksDisplay = [];
                angular.forEach($scope.editableProject.approvedBanks, function (approvedbanks) {
                    BankService.get({
                        'id': approvedbanks
                    }, function (data) {
                        $scope.approvedBanksDisplay.push(data);
                    });
                });
                $scope.amenitiesWithinProjectDisplay = [];
                angular.forEach($scope.editableProject.privateAmenities, function (privateAmenities) {
                    console.log("Amenities Within porject :%O", privateAmenities);
                    PrivateAmenitiesService.get({
                        'id': privateAmenities
                    }, function (data) {
                        $scope.amenitiesWithinProjectDisplay.push(data);
                    });
                });
            });
            console.log("We Are inside Edit");
//            $scope.propertiesTypeDisplay = [];
            $scope.editableProject.propertiesType = [];
//            $scope.publicTransportDisplay = [];
            $scope.editableProject.publicTransport = [];
//            $scope.workplacesDisplay = [];
            $scope.editableProject.workplaces = [];
//            $scope.basicAmenitiesDisplay = [];
            $scope.editableProject.basicAmenities = [];
//            $scope.luxuryAmenitiesDisplay = [];
            $scope.editableProject.luxuryAmenities = [];
//            $scope.approvedBanksDisplay = [];
            $scope.editableProject.approvedBanks = [];
//            $scope.amenitiesWithinProjectDisplay = [];
            $scope.editableProject.amenitiesWithinProject = [];
            $scope.locationSteps = [
                'Basic Details',
                'Connectivity',
                'Costing',
                'Demand For Rental/Resale',
                'Marketability',
                'Project Highlights',
                'Unit Details',
                'Sellers Credability'
            ];
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

            $scope.saveProject = function (project) {
                console.log("Project :%O", project);
                project.$save(project, function () {
                    $state.go('admin.masters_project', null, {'reload': true});
                });
            };
            $scope.setState = function (state) {
                console.log("xyz", state);
                $scope.editableProject.stateId = state.id;
                $scope.editableProject.state = state;
                console.log("$scope.editableProject.state ", $scope.editableProject.state);
            };
            $scope.setPropertyType = function (property) {
                console.log("xyz", property);
                $scope.propertiesTypeDisplay.push(property);
                $scope.propertyType = "";
                $scope.editableProject.propertiesType.push(property.id);
                console.log("Main Array :%O", $scope.editableProject.propertiesType);
                console.log("Properties Type Array :%O", $scope.propertiesTypeDisplay);
            };
            $scope.removePropertyType = function (propertyType) {
                console.log("Getting the thing :%O", propertyType);
                var index = $scope.propertiesTypeDisplay.indexOf(propertyType);
                var index1 = $scope.editableProject.propertiesType.indexOf(propertyType.id);
                $scope.propertiesTypeDisplay.splice(index, 1);
                $scope.editableProject.propertiesType.splice(index1, 1);
                console.log("Updated Type Display :%O", $scope.propertiesTypeDisplay);
                console.log("Updated %O", $scope.editableProject.propertiesType);
            };
            $scope.setWorkplaces = function (workplaces) {
                console.log("xyz", workplaces);
                $scope.workplacesDisplay.push(workplaces);
                $scope.workplaces = "";
                $scope.editableProject.workplaces.push(workplaces.id);
            };
            $scope.removeWorkplaces = function (workplaces) {
                console.log("Getting the thing :%O", workplaces);
                var index = $scope.workplacesDisplay.indexOf(workplaces);
                var index1 = $scope.editableProject.workplaces.indexOf(workplaces.id);
                $scope.workplacesDisplay.splice(index, 1);
                $scope.editableProject.workplaces.splice(index1, 1);
                console.log("Updated Type Display :%O", $scope.workplacesDisplay);
                console.log("Updated %O", $scope.editableProject.workplaces);
            };
            $scope.setBasicAmenities = function (basicAmenities) {
                console.log("xyz", basicAmenities);
                $scope.basicAmenitiesDisplay.push(basicAmenities);
                $scope.$parent.basicAmenities = "";
                $scope.editableProject.basicAmenities.push(basicAmenities.id);
            };
            $scope.removeBasicAmenities = function (basicAmenities) {
                console.log("Getting the thing :%O", basicAmenities);
                var index = $scope.basicAmenitiesDisplay.indexOf(basicAmenities);
                var index1 = $scope.editableProject.basicAmenities.indexOf(basicAmenities.id);
                $scope.basicAmenitiesDisplay.splice(index, 1);
                $scope.editableProject.basicAmenities.splice(index1, 1);
                console.log("Updated Type Display :%O", $scope.basicAmenitiesDisplay);
                console.log("Updated %O", $scope.editableProject.basicAmenities);
            };
            $scope.setLuxuryAmenities = function (luxuryAmenities) {
                console.log("xyz", luxuryAmenities);
                $scope.luxuryAmenitiesDisplay.push(luxuryAmenities);
                $scope.luxuryAmenities = "";
                $scope.editableProject.luxuryAmenities.push(luxuryAmenities.id);
            };
            $scope.removeLuxuryAmenities = function (luxuryAmenities) {
                console.log("Getting the thing :%O", luxuryAmenities);
                var index = $scope.luxuryAmenitiesDisplay.indexOf(luxuryAmenities);
                var index1 = $scope.editableProject.luxuryAmenities.indexOf(luxuryAmenities.id);
                $scope.luxuryAmenitiesDisplay.splice(index, 1);
                $scope.editableProject.luxuryAmenities.splice(index1, 1);
                console.log("Updated Type Display :%O", $scope.luxuryAmenitiesDisplay);
                console.log("Updated %O", $scope.editableProject.luxuryAmenities);
            };
            $scope.setApprovedBanks = function (approvedBanks) {
                console.log("xyz", approvedBanks);
                $scope.approvedBanksDisplay.push(approvedBanks);
                $scope.approvedBanks = "";
                $scope.editableProject.approvedBanks.push(approvedBanks.id);
                console.log("List :%O", $scope.approvedBanksDisplay);
            };
            $scope.removeApprovedBanks = function (approvedBanks) {
                console.log("Getting the thing :%O", approvedBanks);
                var index = $scope.approvedBanksDisplay.indexOf(approvedBanks);
                var index1 = $scope.editableProject.approvedBanks.indexOf(approvedBanks.id);
                $scope.approvedBanksDisplay.splice(index, 1);
                $scope.editableProject.approvedBanks.splice(index1, 1);
                console.log("Updated Type Display :%O", $scope.approvedBanksDisplay);
                console.log("Updated %O", $scope.editableProject.approvedBanks);
            };
            $scope.setAmenitiesWithinProject = function (amenitiesWithinProject) {
                console.log("xyz", amenitiesWithinProject);
                $scope.amenitiesWithinProjectDisplay.push(amenitiesWithinProject);
                $scope.amenitiesWithinProject = "";
                $scope.editableProject.privateAmenities.push(amenitiesWithinProject.id);
                console.log("List :%O", $scope.amenitiesWithinProjectDisplay);
            };
            $scope.removeAmenitiesWithinProject = function (amenitiesWithinProjects) {
                console.log("Getting the thing :%O", amenitiesWithinProjects);
                var index = $scope.amenitiesWithinProjectDisplay.indexOf(amenitiesWithinProjects);
                var index1 = $scope.editableProject.privateAmenities.indexOf(amenitiesWithinProjects.id);
                $scope.amenitiesWithinProjectDisplay.splice(index, 1);
                $scope.editableProject.privateAmenities.splice(index1, 1);
                console.log("Updated Type Display :%O", $scope.amenitiesWithinProjectDisplay);
                console.log("Updated %O", $scope.editableProject.privateAmenities);
            };
            $scope.setTransportation = function (transportation) {
                console.log("xyz t", transportation);
                $scope.publicTransportDisplay.push(transportation);
                $scope.publicTransport = null;
//                $scope.clear();
                $scope.editableProject.publicTransport.push(transportation.id);
                console.log("Main Array :%O", $scope.editableProject.publicTransport);
                console.log("Public Transport Array :%O", $scope.publicTransportDisplay);
            };
            $scope.removeTransportation = function (transportation) {
                console.log("Getting the thing :%O", transportation);
                var index = $scope.publicTransportDisplay.indexOf(transportation);
                var index1 = $scope.editableProject.publicTransport.indexOf(transportation.id);
                $scope.publicTransportDisplay.splice(index, 1);
                $scope.editableProject.publicTransport.splice(index1, 1);
                console.log("Updated Type Display :%O", $scope.publicTransportDisplay);
                console.log("Updated %O", $scope.editableProject.publicTransport);
            };
            $scope.setCity = function (city) {
                console.log("xyz", city);
                $scope.editableProject.cityId = city.id;
                $scope.editableProject.city = city;
                console.log("$scope.editableFranchise.city ", $scope.editableProject.city);
            };
            $scope.setLocation = function (location) {
                $scope.editableProject.locationId = location.id;
                $scope.editableProject.location = location;
                console.log("$scope.editableFranchise.location ", $scope.editableProject.location);
            };
            $scope.setRoad = function (road) {
                $scope.editableProject.majorApproachRoad = road.id;
                $scope.editableProject.road = road;
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
                console.log("State Id :%O", $scope.editableProject.stateId);
                if ($scope.editableProject.stateId === undefined) {
                    return CityService.findByNameLike({
                        'name': searchTerm
                    }).$promise;
                } else {
                    return CityService.findByNameAndStateId({
                        'name': searchTerm,
                        'stateId': $scope.editableProject.stateId
                    }).$promise;
                }
            };
            $scope.searchLocations = function (searchTerm) {
                console.log("Search Term :%O", searchTerm);
                console.log("City Id :%O", $scope.editableProject.cityId);
                if ($scope.editableProject.cityId === undefined) {
                    console.log("Coming to if ??");
                    return LocationService.findByNameLike({
                        'name': searchTerm
                    }).$promise;
                } else {
                    console.log("Coming to Else ??");
                    return LocationService.findByNameAndCityId({
                        'name': searchTerm,
                        'cityId': $scope.editableProject.cityId
                    }).$promise;
                }
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
                    $scope.demandForRentalResale = false;
                    $scope.marketability = false;
                    $scope.projectHighlights = false;
                    $scope.sellersCredibality = false;
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
                    $scope.demandForRentalResale = false;
                    $scope.marketability = false;
                    $scope.projectHighlights = false;
                    $scope.sellersCredibality = false;
                    $scope.unitDetails = false;
                }
                else if (locationstep === "Demand For Rental/Resale") {
                    console.log("Hello demand for rental");
                    $scope.demandForRentalResale = true;
                    $scope.myValue = false;
                    $scope.costing = false;
                    $scope.connectivity = false;
                    $scope.marketability = false;
                    $scope.projectHighlights = false;
                    $scope.sellersCredibality = false;
                    $scope.unitDetails = false;
                }
                else if (locationstep === "Marketability") {
                    console.log("Marketability");
                    $scope.marketability = true;
                    $scope.myValue = false;
                    $scope.costing = false;
                    $scope.connectivity = false;
                    $scope.demandForRentalResale = false;
                    $scope.projectHighlights = false;
                    $scope.sellersCredibality = false;
                    $scope.unitDetails = false;
                }
                else if (locationstep === "Project Highlights") {
                    console.log("Hello Project Highlights");
                    $scope.projectHighlights = true;
                    $scope.myValue = false;
                    $scope.costing = false;
                    $scope.connectivity = false;
                    $scope.demandForRentalResale = false;
                    $scope.marketability = false;
                    $scope.sellersCredibality = false;
                    $scope.unitDetails = false;
                }
                else if (locationstep === "Sellers Credability") {
                    console.log("Hello Sellers Credability");
                    $scope.sellersCredability = true;
                    $scope.myValue = false;
                    $scope.costing = false;
                    $scope.connectivity = false;
                    $scope.demandForRentalResale = false;
                    $scope.marketability = false;
                    $scope.projectHighlights = false;
                    $scope.unitDetails = false;
                }
                else if (locationstep === "Unit Details") {
                    console.log("Hello Unit Details");
                    $scope.unitDetails = true;
                    $scope.myValue = false;
                    $scope.costing = false;
                    $scope.connectivity = false;
                    $scope.demandForRentalResale = false;
                    $scope.marketability = false;
                    $scope.sellersCredibality = false;
                    $scope.projectHighlights = false;
                }
                else {
                    console.log("Nothing");
                    $scope.projectHighlights = false;
                    $scope.myValue = false;
                    $scope.costing = false;
                    $scope.connectivity = false;
                    $scope.demandForRentalResale = false;
                    $scope.marketability = false;
                    $scope.sellersCredibality = false;
                    $scope.unitDetails = false;
                }
            };
        })
        .controller('ProjectViewController', function ($scope, $stateParams, $state) {
            $scope.project = {};
            $scope.project.id = $stateParams.projectId;
            $scope.goBack = function () {
                $state.go('admin.masters_project', {}, {'reload': true});
            };
        })
        .controller('ProjectPhotoController', function (restRoot, FileUploader, $scope, $stateParams, $state) {
            $scope.enableSaveButton = false;
            $scope.goBack = function () {
                $state.go('admin.masters_project', {}, {'reload': true});
            };
            var uploader = $scope.fileUploader = new FileUploader({
                url: restRoot + '/project/' + $stateParams.projectId + '/attachment',
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
        .controller('ProjectInfoController', function (LocationService, PrivateAmenitiesService, BankService, AmenityDetailService, TransportationService, RoadService, PropertyTypeService, ProjectService, CityService, StateService, $scope, $stateParams, $state) {
            $scope.editableProject = ProjectService.get({'id': $stateParams.projectId}, function (project) {
                $scope.editableProject.state = StateService.get({
                    'id': $scope.editableProject.stateId
                });
                $scope.editableProject.city = CityService.get({
                    'id': $scope.editableProject.cityId
                });
                $scope.editableProject.location = LocationService.get({
                    'id': $scope.editableProject.locationId
                });
                $scope.editableProject.road = RoadService.get({
                    'id': $scope.editableProject.majorApproachRoad
                });
                project.propertiesTypeObjects = [];
                angular.forEach(project.propertiesType, function (propertyType) {
                    project.propertiesTypeObjects.push(PropertyTypeService.get({
                        'id': propertyType
                    }));
                });
                project.publicTransportObjects = [];
                angular.forEach(project.publicTransport, function (publicTransport) {
                    project.publicTransportObjects.push(TransportationService.get({
                        'id': publicTransport
                    }));
            });
                project.workplacesObjects = [];
                angular.forEach(project.workplaces, function (workplace) {
                    project.workplacesObjects.push(AmenityDetailService.get({
                        'id': workplace
                    }));
                });
                project.basicAmenitiesObjects = [];
                angular.forEach(project.basicAmenities, function (basicAmenity) {
                    project.basicAmenitiesObjects.push(AmenityDetailService.get({
                        'id': basicAmenity
                    }));
                });
                project.luxuryAmenitiesObjects = [];
                angular.forEach(project.luxuryAmenities, function (luxuryAmenity) {
                    project.luxuryAmenitiesObjects.push(AmenityDetailService.get({
                        'id': luxuryAmenity
                    }));
                });
                project.approvedBanksObjects = [];
                angular.forEach(project.approvedBanks, function (approvedBank) {
                    project.approvedBanksObjects.push(BankService.get({
                        'id': approvedBank
                    }));
                });
                project.privateAmenitiesObjects = [];
                angular.forEach(project.privateAmenities, function (privateAmenity) {
                    project.privateAmenitiesObjects.push(PrivateAmenitiesService.get({
                        'id': privateAmenity
                    }));
                });
            });
            console.log("Editable Project in Info :%O", $scope.editableProject);
        })
        .controller('ProjectDeleteController', function (ProjectService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editableProject = ProjectService.get({'id': $stateParams.projectId});

            $scope.deleteProject = function (project) {
                project.$delete(function () {
                    $state.go('admin.masters_project', null, {'reload': true});
                });
            };
        });
