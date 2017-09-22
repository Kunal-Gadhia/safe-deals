angular.module("safedeals.states.location_master", ['angularjs-dropdown-multiselect'])
        .config(function ($stateProvider, templateRoot) {
            $stateProvider.state('admin.masters_location', {
                'url': '/location_master?offset',
                'templateUrl': templateRoot + '/masters/location/list.html',
                'controller': 'LocationListController'
            });
            $stateProvider.state('admin.masters_location.add', {
                'url': '/add',
                'templateUrl': templateRoot + '/masters/location/form.html',
                'controller': 'LocationAddController'
            });
            $stateProvider.state('admin.masters_location.edit', {
                'url': '/:locationId/edit',
                'templateUrl': templateRoot + '/masters/location/form.html',
                'controller': 'LocationEditController'
            });
            $stateProvider.state('admin.masters_location.locationAmenities', {
                'url': '/:locationId/locationAmenities',
                'templateUrl': templateRoot + '/masters/location/location_amenities.html',
                'controller': 'LocationAmenitiesController'
            });
            $stateProvider.state('admin.masters_location.deletes', {
                'url': '/:locationId/delete',
                'templateUrl': templateRoot + '/masters/location/delete.html',
                'controller': 'LocationDeleteController'
            });
            $stateProvider.state('admin.masters_location.import', {
                'url': '/import',
                'templateUrl': templateRoot + '/masters/location/import.html',
                'controller': 'LocationImportController'
            });
            $stateProvider.state('admin.masters_location.import_excel', {
                'url': '/import/excel',
                'templateUrl': templateRoot + '/masters/location/import_excel.html',
                'controller': 'LocationExcelImportController'
            });
        })
        .controller('LocationListController', function (CityService, UnitService, LocationService, LocationTypeService, SafedealZoneService, LocationCategoryService, StateService, LocationService, $scope, $stateParams, $state, paginationLimit) {
            console.log("check location");
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

            $scope.nextLocations = LocationService.query({
                'offset': $scope.nextOffset
            });

            $scope.locations = LocationService.query({
                'offset': $scope.currentOffset
            }, function () {
                console.log("$scope.locations", $scope.locations);
                angular.forEach($scope.locations, function (location) {

                    if (location.locationType !== null) {
                        location.locationType = LocationTypeService.get({
                            'id': location.locationTypeId
                        });
                    }
                    if (location.safedealZone !== null) {
                        location.safedealZone = SafedealZoneService.get({
                            'id': location.safedealZoneId
                        });
                    }
                    if (location.city !== null) {
                        location.city = CityService.get({
                            'id': location.cityId
                        });
                    }

                    if (location.unit !== null) {
                        location.unit = UnitService.get({
                            'id': location.unitId
                        });
                    }
                    location.locationCategoryObjects = [];
                    angular.forEach(location.locationCategories, function (locationCategoryId) {
                        location.locationCategoryObjects.push(
                                LocationCategoryService.get({
                                    'id': locationCategoryId
                                })
                                );
                    });


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

            $scope.locationExport = function () {
                console.log("are we in export?");
                LocationService.exportAllLocations(function (a) {
                    console.log("a", a);
                    alert("Downloaded successfully");
                });

            };
        })
        .controller('LocationAddController', function (CityService, UnitService, LocationTypeService, RoadService, SafedealZoneService, LocationService, LocationCategoryService, $scope, $state) {
            $scope.locationSteps = [
                'Basic Details',
                'Risk Factors'
            ];
            $scope.selection = $scope.locationSteps[0];
            $scope.myValue = true;
            $scope.getLocationStep = function (locationstep) {
                console.log("Location Step :%O", locationstep);
                $scope.selection = locationstep;
                if (locationstep === "Basic Details") {
                    $scope.myValue = true;
                } else if (locationstep === "Risk Factors") {
                    console.log("Inside Risk Factors");
                    $scope.myValue = false;
                    $scope.riskFactors = true;
                }
                else {
                    console.log("Nothing");
                    $scope.riskFactors = false;
                    $scope.myValue = false;
                }
            };
            $scope.editableLocation = {};
            $scope.cities = CityService.findAllCities();
            $scope.safedealZones = SafedealZoneService.query();
            $scope.locationTypes = LocationTypeService.query();

            $scope.setCity = function (city) {
                console.log("selected city ", city);
                $scope.editableLocation.cityId = city.id;
                $scope.editableLocation.city = city;
            };

            $scope.setLocationType = function (locationType) {
                console.log("editableLocation.locationType", locationType);
                $scope.editableLocation.locationTypeId = locationType.id;
                $scope.editableLocation.locationType = locationType;
            };
            $scope.setSafedealZone = function (safedealZone) {
                console.log("selected safedealZone ", safedealZone);
                $scope.editableLocation.safedealZoneId = safedealZone.id;
                $scope.editableLocation.safedealZone = safedealZone;
            };
            $scope.locationCategoriesDisplay = [];
            $scope.editableLocation.locationCategories = [];
//            $scope.setLocationCategories = function (locationCategories) {
//                $scope.locationCategoriesDisplay.push(locationCategories);
//                $scope.locationCategory = "";
//                $scope.editableLocation.locationCategories.push(locationCategories.id);
//            };
            $scope.setLocationCategories = function (locationCategories) {
                console.log("Setting the values into controller :%O", locationCategories);
                $scope.editableLocation.locationCategories = [];
                $scope.editableLocation.locationCategoryNames = [];
                angular.forEach(locationCategories, function (locationCategory) {
                    console.log("Location Category :%O", locationCategory);
                    $scope.editableLocation.locationCategories.push(locationCategory.id);
                    $scope.editableLocation.locationCategoryNames.push(locationCategory);
                });
                console.log("Location Categories :%O", $scope.editableLocation.locationCategories);
            };
            $scope.removeLocationCategory = function (locationCategory) {
                console.log("Getting the thing :%O", locationCategory);
                var index = $scope.locationCategoriesDisplay.indexOf(locationCategory);
                var index1 = $scope.editableLocation.locationCategories.indexOf(locationCategory.id);
                $scope.locationCategoriesDisplay.splice(index, 1);
                $scope.editableLocation.locationCategories.splice(index1, 1);
                console.log("Updated Type Display :%O", $scope.locationCategoriesDisplay);
                console.log("Updated %O", $scope.editableLocation.locationCategories);
            };
            $scope.setRoad = function (road) {
                $scope.editableLocation.majorApproachRoad = road.id;
                $scope.editableLocation.road = road;
            };
            $scope.searchRoad = function (searchTerm) {
                return RoadService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };

            $scope.setUnit = function (unit) {
                $scope.editableLocation.unitObject = unit;
                $scope.editableLocation.unit = unit.id;
            };
            $scope.searchUnit = function (searchTerm) {
                return UnitService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };

            $scope.searchCities = function (searchTerm) {
                console.log("Search Term :%O", searchTerm);
                return CityService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };

            $scope.searchLocationTypes = function (searchTerm) {
                console.log("Search Term :%O", searchTerm);
                return LocationTypeService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };
            LocationTypeService.query(function (locationTypes) {
                console.log("Location Types :%O", locationTypes);
                $scope.locationTypesList = locationTypes;
            });
            SafedealZoneService.query(function (sdZone) {
                $scope.safedealsZoneList = sdZone;
            });

            $scope.searchLocationCategories = function (searchTerm) {
                console.log("Search Term :%O", searchTerm);
                return LocationCategoryService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };

            $scope.searchSafedealZones = function (searchTerm) {
                console.log("Search Term :%O", searchTerm);
                return SafedealZoneService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };

//            $scope.$watch('editableLocation.name', function (name) {
//                console.log("Name :" + name);
//                LocationService.findByName({'name': name}).$promise.catch(function (response) {
//                    if (response.status === 500) {
//                        $scope.editableLocation.repeatName = false;
//                    }
//                    else if (response.status === 404) {
//                        $scope.editableLocation.repeatName = false;
//                    }
//                    else if (response.status === 400) {
//                        $scope.editableLocation.repeatName = false;
//                    }
//                }).then(function (location) {
//                    if (location.name !== null) {
//                        $scope.editableLocation.repeatName = true;
//                    }
//                    ;
//                });
//            });

            $scope.saveLocation = function (location) {
                console.log("Saved location", location);
//                $scope.locArray = [];
//                angular.forEach(location.locationCategoriesObject, function (locationCategoryData) {
//                    $scope.locArray.push(locationCategoryData.id);
////                    location.locationCategories.push(locationCategoryData.id);
//                });
//                location.locationCategories = _.uniq($scope.locArray);
                LocationService.save(location, function () {
                    $state.go('admin.masters_location', null, {'reload': true});
                });
            };

            $scope.$watch('editableLocation.locationTypeId', function (locationTypeId) {
                console.log("locationTypeId %O", locationTypeId);
                LocationTypeService.get({
                    'id': locationTypeId
                }, function (locationTypeObject) {
                    if (locationTypeObject.name === "WITHIN_CITY") {
                        $scope.editableLocation.auto = true;
                        $scope.editableLocation.bus = true;
                        $scope.editableLocation.taxi = true;
                        $scope.editableLocation.metro = true;
                        $scope.editableLocation.corporationSupply = true;
                        $scope.editableLocation.borewell = true;
                        $scope.editableLocation.openWell = true;
                    }
                    else {
                        $scope.editableLocation.auto = false;
                        $scope.editableLocation.bus = false;
                        $scope.editableLocation.taxi = false;
                        $scope.editableLocation.metro = false;
                        $scope.editableLocation.corporationSupply = false;
                        $scope.editableLocation.borewell = false;
                        $scope.editableLocation.openWell = false;
                    }
                });
            });

            ///////////////////////////////////////////Multiselect
//            $scope.editableLocation.locationCategoriesObject = [];
//            $scope.example14settings = {
//                scrollableHeight: '200px',
//                scrollable: true,
//                enableSearch: true
//            };
//            LocationCategoryService.query(function (locationCategory) {
//                $scope.example14data = locationCategory;
//            });
//            $scope.example2settings = {
//                displayProp: 'name'
//            };
            ///////////////////////////////////////////



        })
        .controller('LocationEditController', function (CityService, UnitService, RoadService, SafedealZoneService, LocationTypeService, LocationService, LocationCategoryService, $scope, $stateParams, $state, $filter, paginationLimit) {
            $scope.editableLocation = LocationService.get({
                'id': $stateParams.locationId
            }, function () {
                $scope.locationCategoriesDisplay = [];
                angular.forEach($scope.editableLocation.locationCategories, function (locationCategory) {
                    LocationCategoryService.get({
                        'id': locationCategory
                    }, function (data) {
                        $scope.locationCategoriesDisplay.push(data);
                    });
                });
                $scope.editableLocation.locationCategoriesObject = $scope.editableLocation.locationCategories;
                $scope.editableLocation.road = RoadService.get({
                    id: $scope.editableLocation.majorApproachRoad
                });
                $scope.editableLocation.city = CityService.get({
                    id: $scope.editableLocation.cityId
                });
                $scope.editableLocation.unitObject = UnitService.get({
                    id: $scope.editableLocation.unit
                });
                LocationTypeService.get({
                    id: $scope.editableLocation.locationTypeId
                }, function (locationType) {
                    $("#locationType").val(locationType.id);
                });
                SafedealZoneService.get({
                    id: $scope.editableLocation.safedealZoneId
                }, function (sdZoneData) {
                    $("#sdZone").val(sdZoneData.id);
                });
            });
            $scope.locationSteps = [
                'Basic Details',
                'Risk Factors'
            ];
            $scope.selection = $scope.locationSteps[0];
            $scope.myValue = true;
            $scope.getLocationStep = function (locationstep) {
                console.log("Location Step :%O", locationstep);
                $scope.selection = locationstep;
                if (locationstep === "Basic Details") {
                    $scope.myValue = true;
                } else if (locationstep === "Risk Factors") {
                    $scope.myValue = false;
                    $scope.riskFactors = true;
                }
                else {
                    console.log("Nothing");
                    $scope.riskFactors = false;
                    $scope.myValue = false;
                }
            };
            $scope.cities = CityService.findAllCities();
            $scope.safedealZones = SafedealZoneService.query();
            $scope.locationTypes = LocationTypeService.query();

            $scope.setCity = function (city) {
                console.log("selected city ", city);
                $scope.editableLocation.cityId = city.id;
                $scope.editableLocation.city = city;
            };

            $scope.setLocationType = function (locationType) {
                console.log("editableLocation.locationType", locationType);
                $scope.editableLocation.locationTypeId = locationType.id;
                $scope.editableLocation.locationType = locationType;
            };
            $scope.setSafedealZone = function (safedealZone) {
                console.log("selected safedealZone ", safedealZone);
                $scope.editableLocation.safedealZoneId = safedealZone.id;
                $scope.editableLocation.safedealZone = safedealZone;
            };
            $scope.locationCategoriesDisplay = [];
            $scope.editableLocation.locationCategories = [];
            $scope.setLocationCategories = function (locationCategories) {
                $scope.locationCategoriesDisplay.push(locationCategories);
                $scope.locationCategory = "";
                $scope.editableLocation.locationCategories.push(locationCategories.id);
            };
            $scope.removeLocationCategory = function (locationCategory) {
                console.log("Getting the thing :%O", locationCategory);
                var index = $scope.locationCategoriesDisplay.indexOf(locationCategory);
                var index1 = $scope.editableLocation.locationCategories.indexOf(locationCategory.id);
                $scope.locationCategoriesDisplay.splice(index, 1);
                $scope.editableLocation.locationCategories.splice(index1, 1);
                console.log("Updated Type Display :%O", $scope.locationCategoriesDisplay);
                console.log("Updated %O", $scope.editableLocation.locationCategories);
            };
            $scope.setRoad = function (road) {
                $scope.editableLocation.majorApproachRoad = road.id;
                $scope.editableLocation.road = road;
            };
            $scope.searchRoad = function (searchTerm) {
                return RoadService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };
            $scope.searchCities = function (searchTerm) {
                console.log("Search Term :%O", searchTerm);
                return CityService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };

            $scope.setUnit = function (unit) {
                $scope.editableLocation.unitObject = unit;
                $scope.editableLocation.unit = unit.id;
            };
            $scope.searchUnit = function (searchTerm) {
                return UnitService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };

            $scope.searchLocationTypes = function (searchTerm) {
                console.log("Search Term :%O", searchTerm);
                return LocationTypeService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };
            LocationTypeService.query(function (locationTypes) {
                console.log("Location Types :%O", locationTypes);
                $scope.locationTypesList = locationTypes;
            });
            SafedealZoneService.query(function (sdZone) {
                $scope.safedealsZoneList = sdZone;
            });

            $scope.searchLocationCategories = function (searchTerm) {
                console.log("Search Term :%O", searchTerm);
                return LocationCategoryService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };

            $scope.searchSafedealZones = function (searchTerm) {
                console.log("Search Term :%O", searchTerm);
                return SafedealZoneService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };

//            $scope.$watch('editableLocation.locationTypeId', function (locationTypeId) {
//                console.log("locationTypeId %O", locationTypeId);
//                LocationTypeService.get({
//                    'id': locationTypeId
//                }, function (locationTypeObject) {
//                    if (locationTypeObject.name === "WITHIN_CITY") {
//                        $scope.editableLocation.auto = true;
//                        $scope.editableLocation.bus = true;
//                        $scope.editableLocation.taxi = true;
//                        $scope.editableLocation.metro = true;
//                        $scope.editableLocation.corporationSupply = true;
//                        $scope.editableLocation.borewell = true;
//                        $scope.editableLocation.openWell = true;
//                    }
//                    else {
//                        $scope.editableLocation.auto = false;
//                        $scope.editableLocation.bus = false;
//                        $scope.editableLocation.taxi = false;
//                        $scope.editableLocation.metro = false;
//                        $scope.editableLocation.corporationSupply = false;
//                        $scope.editableLocation.borewell = false;
//                        $scope.editableLocation.openWell = false;
//                    }
//                });
//
//
//            });

//            $scope.cities = CityService.findAllCities();
////            $scope.safedealZones = SafedealZoneService.query();
////            $scope.locationTypes = LocationTypeService.query();
//            console.log("$scope.locationTypes", $scope.locationTypes);
//            $scope.editableLocation = LocationService.get({'id': $stateParams.locationId}, function (location) {
//
//            });
//            LocationTypeService.query(function (locationTypes) {
//                console.log("Location Types :%O", locationTypes);
//                $scope.locationTypesList = locationTypes;
//            });
//            SafedealZoneService.query(function (sdZone) {
//                $scope.safedealsZoneList = sdZone;
//            });
//
////            $scope.preSelected2 = [];
////            $scope.editableLocation.$promise.then(function (data) {
////
////                $scope.editableLocation.locationCategoriesObjects = [];
////                console.log("data", data);
////                console.log("$scope.editableLocation.locationCategoriesObjects", $scope.editableLocation.locationCategoriesObjects);
//////                $scope.editableLocation.optionalAttendiesObjects = [];
////                angular.forEach(data.locationCategories, function (locationId) {
////                    console.log("data.locationCategories", data.locationCategories);
////                    console.log("data : locationId", locationId);
////                    $scope.editableLocation.locationCategoriesObjects.push(LocationCategoryService.get({
////                        'id': locationId
////                    }));
////                });
////                $scope.preSelected = $scope.editableLocation.locationCategoriesObjects;
////                console.log("$scope.preSelected", $scope.preSelected);
////                console.log("$scope.editableLocationlocationCategoriesObjects", $scope.editableLocation.locationCategoriesObjects);
////            });
//
//            $scope.searchLocationCategories = function (searchTerm) {
//                console.log("Search Term :%O", searchTerm);
//                return LocationCategoryService.findByNameLike({
//                    'name': searchTerm
//                }).$promise;
//            };
//            $scope.locationCategoriesDisplay = [];
//            $scope.editableLocation.locationCategories = [];
//            
//            $scope.setLocationCategories = function (locationCategories) {
//                $scope.locationCategoriesDisplay.push(locationCategories);
//                console.log("Location Categories Display :%O", $scope.locationCategoriesDisplay);
//                $scope.locationCategory = "";
//                $scope.editableLocation.locationCategories.push(locationCategories.id);
//            };
//            $scope.removeLocationCategory = function (locationCategory) {
//                console.log("Getting the thing :%O", locationCategory);
//                var index = $scope.locationCategoriesDisplay.indexOf(locationCategory);
//                var index1 = $scope.editableLocation.locationCategories.indexOf(locationCategory.id);
//                $scope.locationCategoriesDisplay.splice(index, 1);
//                $scope.editableLocation.locationCategories.splice(index1, 1);
//                console.log("Updated Type Display :%O", $scope.locationCategoriesDisplay);
//                console.log("Updated %O", $scope.editableLocation.locationCategories);
//            };
//            $scope.setCity = function (city) {
//                $scope.editableLocation.cityId = city.id;
//                $scope.editableLocation.city = city;
//            };
//            $scope.setRoad = function (road) {
//                $scope.editableLocation.majorApproachRoad = road.id;
//                $scope.editableLocation.road = road;
//            };
//            $scope.searchRoad = function (searchTerm) {
//                return RoadService.findByNameLike({
//                    'name': searchTerm
//                }).$promise;
//            };
//            $scope.setLocationCategories = function (locationCategories) {
//                //console.log("Array me locationCategory mila kya?", locationCategories);
////                $scope.editableLocation.locationCategoryId = locationCategory.id;
////                $scope.editableLocation.locationCategory = locationCategory;
//
//                $scope.editableLocation.locationCategoriesObjects = [];
//                $scope.editableLocation.locationCategories = [];
//                angular.forEach(locationCategories, function (locationCategory) {
//                    $scope.editableLocation.locationCategories.push(locationCategory.id);
//                });
//
//            };
//            $scope.setLocationType = function (locationType) {
//                console.log("editableLocation.locationType", locationType);
//                $scope.editableLocation.locationTypeId = locationType.id;
//                $scope.editableLocation.locationType = locationType;
//            };
            $scope.saveLocation = function (location) {
                console.log("edit location :%O", location);
                location.$save(function () {
                    $state.go('admin.masters_location', null, {'reload': true});
                });
            };
            ///////////////////////////////////////////Multiselect
//            $scope.editableLocation.locationCategoriesObject = [];
            $scope.example14settings = {
                scrollableHeight: '200px',
                scrollable: true,
                enableSearch: true
            };
            LocationCategoryService.query(function (locationCategory) {
                $scope.example14data = locationCategory;
            });
            $scope.example2settings = {
                displayProp: 'name'
            };
            ///////////////////////////////////////////
        })
        .controller('LocationDeleteController', function (LocationService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editableLocation = LocationService.get({'id': $stateParams.locationId});

            $scope.deleteLocation = function (location) {
                console.log("Delete Object :%O", location);
                location.$delete(function () {
                    $state.go('admin.masters_location', null, {'reload': true});
                });
            };
        })
        .controller('LocationAmenitiesController', function (AmenityDetailService, LocationService, AmenityService, $scope, $stateParams, $state, paginationLimit) {
            LocationService.get({
                'id': $stateParams.locationId
            }, function (location) {
                $scope.editableLocation = location;
                $scope.selectedLocation = new google.maps.LatLng(location.latitude, location.longitude);
            });

            AmenityService.findAllAmenities(function (locationAmenities) {
                console.log("locationAmenities ", locationAmenities);

                $scope.locationAmenitiesArray = locationAmenities;
                var half_length = Math.ceil($scope.locationAmenitiesArray.length / 2);
                $scope.locationAmenitiesArrays = $scope.locationAmenitiesArray.splice(0, half_length);

            });

            $scope.LocationAmenities = [];
            $scope.AmenityDetails = [];
            $scope.toggleLocationAmenitySelection = function (locationAmenity) {
                console.log("locationAmenity", locationAmenity);
                var idx = $scope.LocationAmenities.indexOf(locationAmenity);

                // is currently selected
                if (idx > -1) {
                    $scope.LocationAmenities.splice(idx, 1);
                }

                // is newly selected
                else {
                    $scope.LocationAmenities.push(locationAmenity);
                }

            };


            $scope.saveLocationAmenitiesFromMap = function () {
                $scope.requiredAmenities = [];

                angular.forEach($scope.LocationAmenities, function (LocationAmenity) {
                    console.log("showLocationAmenity ::::", LocationAmenity);
                    switch (LocationAmenity.name) {
                        case 'Hotel':
                            $scope.requiredAmenities.push("restaurant");
                            break;
                        case 'Industries':
                            LocationAmenity = "";
                            $scope.requiredAmenities.push(LocationAmenity);
                            break;
                        case 'Government Offices':
                            $scope.requiredAmenities.push("local_government_office");
                            break;
                        case 'College':
                            $scope.requiredAmenities.push("university");
                            break;
                        case 'Bank':
                            $scope.requiredAmenities.push("bank");
                            break;
                        case 'School':
                            $scope.requiredAmenities.push("school");
                            break;
                        case 'Hospital':
                            $scope.requiredAmenities.push("hospital");
                            break;
                        case 'Mall':
                            $scope.requiredAmenities.push("shopping_mall");
                            break;
                        case 'Software Company':
                            LocationAmenity = "";
                            $scope.requiredAmenities.push(LocationAmenity);
                            break;
                        case 'Airport':
                            $scope.requiredAmenities.push("airport");
                            break;
                        case 'Railway Station':
                            $scope.requiredAmenities.push("train_station");
                            break;
                        case 'Bus Stand':
                            $scope.requiredAmenities.push("bus_station");
                            break;
                        case 'Petrol Pump':
                            LocationAmenity = "";
                            $scope.requiredAmenities.push("gas_station");
                            break;
                        case 'Park':
                            $scope.requiredAmenities.push("park");
                            break;
                        case 'Club':
                            $scope.requiredAmenities.push("night_club");
                            break;
                        case 'Market':
                            $scope.requiredAmenities.push("grocery_or_supermarket");
                            break;
                        case 'Coaching Center':
                            $scope.requiredAmenities.push(LocationAmenity);
                            break;
                    }
                });

                map = new google.maps.Map(document.getElementById('locationAmenitiesMapContainer'), {
                    center: $scope.selectedLocation,
                    zoom: 15
                });
                var request = {
                    location: $scope.selectedLocation,
                    radius: 500,
                    types: $scope.requiredAmenities
                };

                var service = new google.maps.places.PlacesService(map);
                service.nearbySearch(request, callback);
                function callback(results, status) {
                    angular.forEach(results, function (result) {
                        console.log(result);

                        $scope.AmenityDetails.push({
                            name: result.name,
                            amenity_id: null,
                            locationId: $scope.editableLocation.id,
                            address: result.vicinity,
                            phone_number: null,
                            latitude: result.geometry.location.lat(),
                            longitude: result.geometry.location.lng(),
                            workplace_category_id: null
                        });
                        console.log("$scope.AmenityDetails", $scope.AmenityDetails);
                    });
//                    ---------------------------------------------------
                    angular.forEach($scope.AmenityDetails, function (val) {
                        AmenityDetailService.save(val, function () {
                            $state.go('admin.masters_location', null, {'reload': true});
                        });
                    });
//--------------------------------------------------------------------
//                    $scope.AmenityDetails.push({
//                        locationId : $scope.editableLocation.id,
//                        address : result.vicinity;
//                    });
//                    if (status == google.maps.places.PlacesServiceStatus.OK) {
//                        for (var i = 0; i < results.length; i++) {
//                            createMarker(results[i]);
//                        }
//                    }
                }

//                angular.forEach(results, function (result) {
//                    console.log("RESULTS", result);
//                    AmenityDetailService.save(locationAmenity, function () {
//                        $state.go('admin.masters_location', null, {'reload': true});
//                    });
//                });
            };
//            console.log("$scope.locationAmenities", $scope.locationAmenities);
//            var mapCenter = new google.maps.LatLng(19.7515, 75.7139);
//            var showMap = function () {
//                var mapContainer = document.getElementById("locationAmenitiesMapContainer");
//                var mapProp = {
//                    center: mapCenter,
//                    zoom: 11,
//                    mapTypeId: google.maps.MapTypeId.ROADMAP
//                };
//                var map = new google.maps.Map(mapContainer, mapProp);
//            };
//            showMap();
//            console.log("are we here?");
        })
        .controller('LocationImportController', function (CityService, LocationService, RawMarketPriceService, RawReadyReckonerService, $scope, $stateParams, $state, paginationLimit) {
            console.log("chck loc imp");

            var map;
            var mapContainer = document.getElementById("locationImportMapContainer");
            var mapCenter = new google.maps.LatLng(20.5937, 78.9629);
            var mapProp = {
                center: mapCenter,
                zoom: 4,
                mapTypeId: google.maps.MapTypeId.ROADMAP
            };
            var geocoder = new google.maps.Geocoder();
            console.log("What is geocoder?? :%O", geocoder);
            $scope.editableLocation = {};
            $scope.cities = CityService.findAllCities();
            console.log("$scope.cities", $scope.cities);
            $scope.locations = [];
            $scope.setCity = function (city) {
                $scope.cityId = city.id;
                $scope.city = city;
                angular.forEach($scope.locations, function (location) {
                    location.cityId = city.id;
                });
                mapProp.center = new google.maps.LatLng(city.latitude, city.longitude);
                mapProp.zoom = 12;
                map = new google.maps.Map(mapContainer, mapProp);
                console.log("Geocoder :%O", geocoder);
                console.log("Map :%O", map);
                geocodeAddress(geocoder, map, $scope.cityId);

            };
            var showMap = function () {

                map = new google.maps.Map(mapContainer, mapProp);
                console.log("map", map);
            };
            function geocodeAddress(geocoder, resultsMap, cityId) {
                console.log("City : " + cityId);
                console.log("Inside Geocode Address");
//                RawMarketPriceService.findByUniqueLocation(function (rawMarketPrices) {
                RawMarketPriceService.findByUniqueLocationWithCityId({
                    'cityId': cityId
                }, function (rawMarketPrices) {
                    console.log("Raw Market Prices", rawMarketPrices);
                    angular.forEach(rawMarketPrices, function (rawMarketPrice) {
                        console.log("inside for each?", rawMarketPrice);
                        var address = rawMarketPrice.locationName + ", " + $scope.city.name;
                        console.log("address ", address);
                        geocoder.geocode({'address': address}, function (results, status) {
                            if (status === google.maps.GeocoderStatus.OK) {
                                console.log("results", results);
                                rawMarketPrice.lat = results[0].geometry.location.lat();
                                rawMarketPrice.long = results[0].geometry.location.lng();
                                resultsMap.setCenter(results[0].geometry.location);
                                var marker = new google.maps.Marker({
                                    map: resultsMap,
                                    position: results[0].geometry.location,
                                    title: results[0].formatted_address
                                });
                            } else {
                                alert('Geocode was not successful for the following reason: ' + status);
                            }
                            $scope.locations.push({
                                name: rawMarketPrice.locationName,
                                locationTypeId: rawMarketPrice.locationTypeId,
                                cityId: $scope.cityId,
                                safedealZoneId: rawMarketPrice.safedealZoneId,
                                locationCategories: rawMarketPrice.locationCategories,
                                latitude: rawMarketPrice.lat,
                                longitude: rawMarketPrice.long,
                                sourceOfWater: rawMarketPrice.sourceOfWater,
                                publicTransport: rawMarketPrice.publicTransport,
                                advantage: rawMarketPrice.advantage,
                                disadvantage: rawMarketPrice.disadvantage,
                                population: rawMarketPrice.population,
                                description: rawMarketPrice.description,
                                majorApproachRoad: rawMarketPrice.majorApproachRoad,
                                isCommercialCenter: rawMarketPrice.isCommercialCenter
                            });
                        });
                    });
                });
            }
            ;
            console.log("$$$", $scope.locations);
            $scope.saveLocationFromMap = function () {
                console.log("Inside Save Location :%O", $scope.locations);
                angular.forEach($scope.locations, function (location) {
                    console.log("location to save %O", location);
                    LocationService.save(location, function () {
                        $state.go('admin.masters_location', {'reload': true});
                    });
                });
            };
            showMap();
        })
        .controller('LocationExcelImportController', function (FileUploader, $timeout, restRoot, LocationService, $scope, $stateParams, $state, paginationLimit) {
//            console.log("showDetails", $scope.showDetails);
            var uploader = $scope.fileUploader = new FileUploader({
                url: restRoot + '/location/attachment',
                autoUpload: true,
                alias: 'attachment'
            });

            uploader.onBeforeUploadItem = function (item) {
                $scope.uploadInProgress = true;
                console.log("before upload item:", item);
                console.log("uploader", uploader);
            };
            uploader.onErrorItem = function (fileItem, response, status, headers) {
                $scope.uploadFailed = true;
                $timeout(function () {
                    $scope.uploadFailed = false;
                }, 2000);
                console.log("upload error");
//                $scope.refreshRawMarketPrice();
            };
            uploader.onCompleteItem = function (fileItem, response, status, headers) {
                $scope.uploadInProgress = true;
                $timeout(function () {
                    $scope.uploadInProgress = false;
                }, 2000);
//                $scope.refreshRawMarketPrice();
                console.log("upload completion", fileItem);

            };
            $scope.saveExcelAttachment = function (fileUploader) {
                LocationService.saveExcelData(fileUploader, function () {
                    $state.go('admin.masters_location', null, {'reload': true});
                });
            };
        });
