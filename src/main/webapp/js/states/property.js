angular.module("safedeals.states.property", [])
        .config(function ($stateProvider, templateRoot) {
            $stateProvider.state('main.property', {
                'url': '/property/?:cityId?:locationId?:propertySize?:minBudget?:maxBudget?',
                'params': {cityId: null,
                    locationId: null,
                    propertySize: null,
                    minBudget: null,
                    maxBudget: null},
                'templateUrl': templateRoot + '/property/property.html',
                'controller': 'PropertyController'
            });
            $stateProvider.state("main.property.property_detail", {
                'url': '/:propertyId/property_detail',
                'templateUrl': templateRoot + '/property/property_detail.html',
                'controller': 'PropertyDetailController'
            });
            $stateProvider.state("main.property.property_comparison", {
                'url': '/property_comparison',
                'templateUrl': templateRoot + '/property/property_comparision.html',
                'controller': 'PropertyComparisionController'
            });
        })

        .controller('PropertyController', function ($scope, $state, $filter, PropertyService, PropertyTypeService, LocationService, $stateParams, MarketPriceService, CityService, StateService) {
            console.log("State Params :%O", $stateParams);
            $scope.hideCompareButton = true;
            $scope.propertyTypesList = PropertyTypeService.query();
            console.log("Property type List :%O", $scope.propertyTypesList);
            $scope.validateForm = function (cityId, locationId, propertySize, minBudget, maxBudget) {
                console.log("City Id :%O", cityId);
                console.log("Location Id :%O", locationId);
                console.log("Property Size :%O", propertySize);
                console.log("Min Budget :" + minBudget);
                console.log("Max Budget :" + maxBudget);
                if (cityId !== undefined & locationId !== undefined & propertySize !== undefined & minBudget === undefined & maxBudget === undefined) {
                    console.log("Filter By City & Location");
                    $state.go('main.property', {
                        cityId: cityId,
                        locationId: locationId,
                        propertySize: propertySize,
                        minBudget: null,
                        maxBudget: null
                    });
                }
                else if (cityId === undefined & locationId === undefined & propertySize !== undefined & minBudget !== undefined & maxBudget !== undefined) {
                    console.log("Filter By Budget");
                    var difference = maxBudget - minBudget;
                    console.log("Difference :" + difference);
                    if (difference < 0) {
                        alert("Minimum budget is more than maximum budget, Select correct value.");
                    } else {
                        console.log("Budget Parameters are proper");
                        $state.go('main.property', {
                            cityId: null,
                            locationId: null,
                            propertySize: propertySize,
                            minBudget: minBudget,
                            maxBudget: maxBudget
                        });
                    }
                }
                else {
                    console.log("All Parameters Present");
                    var difference = maxBudget - minBudget;
                    console.log("Difference :" + difference);
                    if (difference < 0) {
                        alert("Minimum budget is more than maximum budget, Select correct value.");
                    } else {
                        console.log("Budget Parameters are proper");
                        $state.go('main.property', {
                            cityId: cityId,
                            locationId: locationId,
                            propertySize: propertySize,
                            minBudget: minBudget,
                            maxBudget: maxBudget
                        });
                    }
                }

            };

//            $scope.searchLocationByLocationAndBudget = function (cityName, minBudget, maxBudget, propertySize) {
//                console.log("property:" + propertySize);
//                console.log("City Id :" + cityName);
//                console.log("Min Budget :" + minBudget);
//                console.log("Max Budget :" + maxBudget);
//                $scope.cityObject = CityService.findByCityName({
//                    'name': cityName
//                });
//                console.log("City Object :%O", $scope.cityObject);
//                $state.go('main.location', {
//                    locationMinBudget: minBudget / propertySize,
//                    locationMaxBudget: maxBudget / propertySize,
//                    propertyDetails: propertySize,
//                    cityId: $scope.cityId
//                });
//            };
            var map;
            var mapContainer = document.getElementById("locationMapContainer");
            var nagpurCoordinate = new google.maps.LatLng(21.1458, 79.0882);
            var mapProp = {
                center: nagpurCoordinate,
                zoom: 4,
                mapTypeId: google.maps.MapTypeId.ROADMAP
            };
            var drawMap = function () {
                console.log("Priop :%O", mapProp);
                map = new google.maps.Map(mapContainer, mapProp);
            };

            $scope.properties = [];
            $scope.searchStates = function (searchTerm) {
                return StateService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };
            $scope.selectState = function (state) {
                //only change if not same as previously selected state
                $scope.stateName = state.name;
                $scope.stateId = state.id;
                $scope.state = state;
            };


            $scope.searchCities = function (searchTerm) {
                console.log("State Id :%O", $scope.stateId);
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
            $scope.selectCity = function (city) {
                //only change if not same as previously selected state
                console.log("City :%O", city);
                $scope.cityName = city.name;
                $scope.cityId = city.id;
                $scope.city = city;
            };

            $scope.searchLocations = function (searchTerm) {
                console.log("Search Term :%O", searchTerm);
                console.log("City Id :%O", $scope.cityId);
                if ($scope.cityId === undefined) {
                    console.log("Coming to if ??");
                    return LocationService.findByNameLike({
                        'name': searchTerm
                    }).$promise;
                } else {
                    console.log("Coming to Else ??");
                    return LocationService.findByNameAndCityId({
                        'name': searchTerm,
                        'cityId': $scope.cityId
                    }).$promise;
                }
            };
            $scope.setLocation = function (location) {
                $scope.locationId = location.id;
                $scope.locationName = location.name;
                $scope.location = location;
                console.log("$scope.location ", $scope.location);
            };
            drawMap();
            console.log("Location Min Budget :%O", $stateParams.locationMinBudget);
            if ($stateParams.cityId !== null & $stateParams.locationId !== null & $stateParams.propertySize !== null & $stateParams.minBudget === null & $stateParams.maxBudget === null) {
                console.log("Location Filter Main Thing");
                console.log("StateParams :%O", $stateParams);

                PropertyService.findByLocationAndCity({
                    'locationId': $stateParams.locationId,
                    'cityId': $stateParams.cityId,
                    'propertySize': $stateParams.propertySize
                }, function (propertyObjects) {
                    console.log("Property objects :%O", propertyObjects);
                    $scope.propertyList = propertyObjects;
                    angular.forEach(propertyObjects, function (propertyObject) {
                        $scope.properties.push(propertyObject);
                        LocationService.get({
                            'id': propertyObject.locationId
                        }, function (location) {
//                            $scope.locations.push(location);
                            console.log("Locations :%O", location);
                            drawMarker({lat: location.latitude, lng: location.longitude}, location.name, map);
                            var myCity = new google.maps.Circle({
                                center: new google.maps.LatLng(location.latitude, location.longitude),
                                radius: 750,
                                strokeColor: "#87C4C2",
                                strokeOpacity: 0.8,
                                strokeWeight: 2,
                                fillColor: "#C1E6E5",
                                fillOpacity: 0.2,
                                zoom: 13
                            });
                            myCity.setMap(map);
                            map.fitBounds(myCity.getBounds());
//                            myCity.setZoom(13);
                        });
                    });
                });
//                console.log("City Id :%O", $stateParams.cityId);
//                console.log("Min Budget :%O", $stateParams.locationMinBudget);
//                console.log("Max Budget :%O", $stateParams.locationMaxBudget);
//                MarketPriceService.findByRequirement({
//                    'cityId': $stateParams.cityId, // cityId is hard coded for 1.0 version
//                    'locationMinBudget': Math.round($stateParams.locationMinBudget),
//                    'locationMaxBudget': Math.round($stateParams.locationMaxBudget)
//                }, function (mpObjects) {
//                    console.log("$scope.mpObjects =  %O", mpObjects);
//                    angular.forEach(mpObjects, function (mpObject) {
//                        LocationService.get({
//                            'id': mpObject.locationId
//                        }, function (location) {
//                            $scope.locations.push(location);
//                            console.log("Locations :%O", location);
//                            drawMarker({lat: location.latitude, lng: location.longitude}, location.name, map);
//                            var myCity = new google.maps.Circle({
//                                center: new google.maps.LatLng(location.latitude, location.longitude),
//                                radius: 750,
//                                strokeColor: "#87C4C2",
//                                strokeOpacity: 0.8,
//                                strokeWeight: 2,
//                                fillColor: "#C1E6E5",
//                                fillOpacity: 0.2,
//                                zoom: 13
//                            });
//                            myCity.setMap(map);
//                            myCity.setMap(map);
//                            myCity.setMap(map);
//                            map.fitBounds(myCity.getBounds());
////                            myCity.setZoom(13);
//                        });
//                    });
//                    $scope.$watch("locations", function (n, o) {
//                        $scope.selectedLocationList = $filter("filter")(n, {flag: true});
//                        console.log("What is trues :%O", $scope.selectedLocationList);
//                        console.log("Trues Length :%O", $scope.selectedLocationList.length);
//                        if ($scope.selectedLocationList.length === 0) {
//                            $scope.hideCompareButton = true;
//                        } else if ($scope.selectedLocationList.length > 0) {
//                            $scope.hideCompareButton = false;
//                        }
//                        if ($scope.selectedLocationList.length === 3) {
//                            console.log("Coming to if?");
//                            $scope.hideCheckbox = true;
//                        }
//                    }, true);
//                    $scope.removeLocation = function (location) {
//                        console.log("You are called with :%O", location);
//                        location.flag = false;
//                        var index = $scope.selectedLocationList.indexOf(location);
//                        console.log("Index :%O", index);
//                        $scope.selectedLocationList.splice(index, 1);
////                        $scope.s
////                        $scope.selectedLocationList.splice(location);
//                        console.log("After Removing List:%O", $scope.selectedLocationList);
//                        if ($scope.selectedLocationList.length < 3) {
//                            $scope.hideCheckbox = false;
//                        }
//                    };
//                    $scope.compareLocations = function () {
//                        console.log("Coming to compare??");
//                    };
//                });
            }
            else if ($stateParams.cityId === null & $stateParams.locationId === null & $stateParams.propertySize !== null & $stateParams.minBudget !== null & $stateParams.maxBudget !== null) {
                console.log("Coming to budget main thing");
                PropertyService.findByMinAndMaxBudget({
                    'propertySize': $stateParams.propertySize,
                    'minBudget': $stateParams.minBudget,
                    'maxBudget': $stateParams.maxBudget
                }, function (propertyObjects) {
                    console.log("Property objects :%O", propertyObjects);
                    $scope.propertyList = propertyObjects;
                    angular.forEach(propertyObjects, function (propertyObject) {
                        $scope.properties.push(propertyObject);
                        LocationService.get({
                            'id': propertyObject.locationId
                        }, function (location) {
                            console.log("Locations :%O", location);
                            drawMarker({lat: location.latitude, lng: location.longitude}, location.name, map);
                            var myCity = new google.maps.Circle({
                                center: new google.maps.LatLng(location.latitude, location.longitude),
                                radius: 750,
                                strokeColor: "#87C4C2",
                                strokeOpacity: 0.8,
                                strokeWeight: 2,
                                fillColor: "#C1E6E5",
                                fillOpacity: 0.2,
                                zoom: 13
                            });
                            myCity.setMap(map);
                            map.fitBounds(myCity.getBounds());
                        });
                    });
                });
            } else if ($stateParams.cityId !== null & $stateParams.locationId !== null & $stateParams.propertySize !== null & $stateParams.minBudget !== null & $stateParams.maxBudget !== null) {
                console.log("All Params ");
                PropertyService.findByFilters({
                    'cityId': $stateParams.cityId,
                    'locationId': $stateParams.locationId,
                    'propertySize': $stateParams.propertySize,
                    'minBudget': $stateParams.minBudget,
                    'maxBudget': $stateParams.maxBudget
                }, function (propertyObjects) {
                    console.log("Property objects :%O", propertyObjects);
                    $scope.propertyList = propertyObjects;
                    angular.forEach(propertyObjects, function (propertyObject) {
                        $scope.properties.push(propertyObject);
                        LocationService.get({
                            'id': propertyObject.locationId
                        }, function (location) {
                            console.log("Locations :%O", location);
                            drawMarker({lat: location.latitude, lng: location.longitude}, location.name, map);
                            var myCity = new google.maps.Circle({
                                center: new google.maps.LatLng(location.latitude, location.longitude),
                                radius: 750,
                                strokeColor: "#87C4C2",
                                strokeOpacity: 0.8,
                                strokeWeight: 2,
                                fillColor: "#C1E6E5",
                                fillOpacity: 0.2,
                                zoom: 13
                            });
                            myCity.setMap(map);
                            map.fitBounds(myCity.getBounds());
                        });
                    });
                });
            }
            else {
                console.log("Coming to else??");
                $scope.$watch('state', function (state) {
                    map.setCenter({
                        lat: state.latitude,
                        lng: state.longitude
                    });
                    map.setZoom(6);
                });
                $scope.$watch('city', function (city) {
                    map.setCenter({
                        lat: city.latitude,
                        lng: city.longitude
                    });
                    map.setZoom(13);
                });
                $scope.$watch('location', function (location) {
                    map.setCenter({
                        lat: location.latitude,
                        lng: location.longitude
                    });
                    map.setZoom(14);
                });
            }
            ;

            $scope.$watch("properties", function (n, o) {
                console.log("Detecting Change");
                $scope.selectedPropertyList = $filter("filter")(n, {flag: true});
                console.log("What is trues :%O", $scope.selectedPropertyList);
                console.log("Trues Length :%O", $scope.selectedPropertyList.length);
                if ($scope.selectedPropertyList.length === 0) {
                    $scope.hideCompareButton = true;
                } else if ($scope.selectedPropertyList.length > 0) {
                    $scope.hideCompareButton = false;
                }
                if ($scope.selectedPropertyList.length === 3) {
                    console.log("Coming to if?");
                    $scope.hideCheckbox = true;
                }
            }, true);
            $scope.removeProperty = function (property) {
                console.log("You are called with :%O", property);
                property.flag = false;
                var index = $scope.selectedPropertyList.indexOf(property);
                console.log("Index :%O", index);
                $scope.selectedPropertyList.splice(index, 1);
//                        $scope.s
//                        $scope.selectedLocationList.splice(location);
                console.log("After Removing List:%O", $scope.selectedPropertyList);
                if ($scope.selectedPropertyList.length < 3) {
                    $scope.hideCheckbox = false;
                }
            };
            $scope.compareLocations = function () {
                console.log("Coming to compare??");
            };

            var drawMarker = function (position, title, map) {
                console.log("Position :%O", position);
                new google.maps.Marker({
                    map: map,
                    position: position,
                    title: title,
                    icon: 'images/icons_svg/dot.png'
                });
            };
        })

        .controller('PropertyComparisionController', function ($scope, LocationService, BankService, AmenityDetailService, TransportationService, RoadService, ProjectService) {
            console.log("Property Comaparision Controller");
            console.log("Selected List :%O", $scope.$parent.selectedPropertyList);
            $scope.compareList = $scope.$parent.selectedPropertyList;
            $scope.finalCompareArray = [];
            angular.forEach($scope.compareList, function (compareList) {
                console.log("locationID??", compareList.locationId);
                LocationService.get({
                    'id': compareList.locationId
                }, function (locationObject) {
                    compareList.location = locationObject;
                    console.log("compareList.location111 :%O", compareList.location);
                });

                ProjectService.get({
                    'id': compareList.projectId
                }, function (projectObject) {
                    compareList.project = projectObject;
                    console.log("compareList.project111 :%O", compareList.project);
                });

                RoadService.get({
                    'id': compareList.majorApproachRoad
                }, function (majorApproachRoadObject) {
                    compareList.majorApproachRoadObject = majorApproachRoadObject;
                    //  console.log("compareList.majorApproachRoad111 :%O", compareList.majorApproachRoadObject);
                });

//                TransportationService.get({
//                    'id': compareList.publicTransport
//                }, function (publicTransportObject) {
//                    compareList.publicTransportObject = publicTransportObject;
//                  //  console.log("compareList.publicTransport111 :%O", compareList.publicTransportObject);
//                });

                compareList.publicTransportDisplay = [];
                angular.forEach(compareList.publicTransport, function (publicTransport) {
                    TransportationService.get({
                        'id': publicTransport
                    }, function (data) {
                        compareList.publicTransportDisplay.push(data);
                    });
                });



                compareList.workplacesDisplay = [];
                angular.forEach(compareList.workplaces, function (workplaces) {
                    AmenityDetailService.get({
                        'id': workplaces
                    }, function (data) {
                        compareList.workplacesDisplay.push(data);
                    });
                });

                compareList.basicAmenitiesDisplay = [];
                angular.forEach(compareList.basicAmenities, function (basicAmenities) {
                    AmenityDetailService.get({
                        'id': basicAmenities
                    }, function (data) {
                        compareList.basicAmenitiesDisplay.push(data);
                    });
                });

                compareList.luxuryAmenitiesDisplay = [];
                angular.forEach(compareList.luxuryAmenities, function (luxuryAmenities) {
                    AmenityDetailService.get({
                        'id': luxuryAmenities
                    }, function (data) {
                        compareList.luxuryAmenitiesDisplay.push(data);
                    });
                });

                compareList.approvedBanksDisplay = [];
                angular.forEach(compareList.approvedBanks, function (approvedbanks) {
                    BankService.get({
                        'id': approvedbanks
                    }, function (data) {
                        compareList.approvedBanksDisplay.push(data);
                    });
                });

                compareList.projectsNearbyDisplay = [];
                angular.forEach(compareList.projectsNearby, function (projectsNearby) {
                    console.log("Nearby Projects :%O", projectsNearby);
                    ProjectService.get({
                        'id': projectsNearby
                    }, function (data) {
                        compareList.projectsNearbyDisplay.push(data);
                    });
                });

                console.log("compareList.location :%O", compareList.location);
                $scope.finalCompareArray.push(compareList);
            });



        })
        .controller('PropertyDetailController', function ($scope, $filter, PropertyService, AmenityDetailService, HospitalService, AmenityCodeService, AmenityService, LocationService, MallService, CoordinateService, BranchService, SchoolService, PropertyService, ProjectService, $stateParams) {
            var map;
            var map1;
            var map2;
            var map3;
            var mapContainer = document.getElementById("propertyDetailMapContainer");
            console.log("$stateparams ID::::::", $stateParams.propertyId);
            var drawMap = function (mapProperty) {
                console.log("Coming To Draw Map %O", mapContainer);
                map = new google.maps.Map(mapContainer, mapProperty);
            };
            var drawMap1 = function (mapProperty, mapContainer1) {
                console.log("Coming To Draw Map 1 :" + mapContainer1);
                map1 = new google.maps.Map(mapContainer1, mapProperty);
            };
            var drawMap2 = function (mapProperty, mapContainer2) {
                console.log("Coming To Draw Map 2 :" + mapContainer2);
                map2 = new google.maps.Map(mapContainer2, mapProperty);
            };
            var drawMap3 = function (mapProperty, mapContainer3) {
                console.log("Coming To Draw Map 3 :" + mapContainer3);
                map3 = new google.maps.Map(mapContainer3, mapProperty);
            };
            var drawMarker = function (position, title, map) {
                console.log("Position :%O", position);
                new google.maps.Marker({
                    map: map,
                    position: position,
                    title: title,
                    icon: 'images/icons_svg/dot.png'
                });
            };
            var drawAmenityMarker = function (position, title, map) {
                console.log("Position :%O", position);
                new google.maps.Marker({
                    map: map,
                    position: position,
                    title: title
                });
            };
            var drawWorkplaceMarker = function (position, title, map) {
                console.log("Position 1 :%O", position);
                new google.maps.Marker({
                    map: map1,
                    position: position,
                    title: title
                });
            };
//            LocationService.get({
//                'id': $stateParams.locationId
//            }, function (location) {
//                $scope.location = location;
//                var nagpurCoordinate = new google.maps.LatLng(location.latitude, location.longitude);
//                var mapProp = {
//                    center: nagpurCoordinate,
//                    zoom: 15,
//                    mapTypeId: google.maps.MapTypeId.ROADMAP
//                };
//                drawMap(mapProp);
//                drawMarker({lat: location.latitude, lng: location.longitude}, location.name, map);
//                var myCity = new google.maps.Circle({
//                    center: new google.maps.LatLng(location.latitude, location.longitude),
//                    radius: 5000,
//                    strokeColor: "#87C4C2",
//                    strokeOpacity: 0.8,
//                    strokeWeight: 2,
//                    fillColor: "#C1E6E5",
//                    fillOpacity: 0.2,
//                    zoom: 13
//                });
//                myCity.setMap(map);
//                map.fitBounds(myCity.getBounds());
//            });
            PropertyService.get({
                'id': $stateParams.propertyId
            }, function (property) {
                $scope.property = property;
                var propertyCoordinate = new google.maps.LatLng(property.latitude, property.longitude);
                var mapProp = {
                    center: propertyCoordinate,
                    zoom: 15,
                    mapTypeId: google.maps.MapTypeId.ROADMAP
                };
                drawMap(mapProp);
                drawMarker({lat: property.latitude, lng: property.longitude}, property.name, map);
                var myCity = new google.maps.Circle({
                    center: new google.maps.LatLng(property.latitude, property.longitude),
                    radius: 5000,
                    strokeColor: "#87C4C2",
                    strokeOpacity: 0.8,
                    strokeWeight: 2,
                    fillColor: "#C1E6E5",
                    fillOpacity: 0.2,
                    zoom: 13
                });
                myCity.setMap(map);
                map.fitBounds(myCity.getBounds());
            });
            $scope.getAmenityDetailByAmenity = function (amenityDetail) {
                $scope.amenityDetailCityFilter = {
                    'amenityId': amenityDetail.id,
                    'cityId': $scope.property.cityId
                };
                $scope.amenityDetailCityFilter.cityId = $scope.property.cityId;
                AmenityDetailService.findByAmenityIdCityId({
                    'amenityId': amenityDetail.id,
                    'cityId': $scope.property.cityId
                }, function (amenityDetailObject) {
                    $scope.amenityDetailsList = amenityDetailObject;
                    angular.forEach(amenityDetailObject, function (amenityDetail) {
                        drawAmenityMarker({lat: amenityDetail.latitude, lng: amenityDetail.longitude}, amenityDetail.name, map);
//                        var infoWindow = new google.maps.InfoWindow({
//                            'content': amenityDetail.name
//                        });
//                        infoWindow.open(map, drawMarker());
                    });
                });
            };
            $scope.getAmenityDetailByAmenityWorkplaces = function (amenityDetail) {
                $scope.amenityDetailCityFilter = {
                    'amenityId': amenityDetail.id,
                    'cityId': $scope.property.cityId
                };
                $scope.amenityDetailCityFilter.cityId = $scope.property.cityId;
                AmenityDetailService.findByAmenityIdCityId({
                    'amenityId': amenityDetail.id,
                    'cityId': $scope.property.cityId
                }, function (amenityDetailObject) {
                    $scope.amenityDetailsList = amenityDetailObject;
                    angular.forEach(amenityDetailObject, function (amenityDetail) {
                        drawWorkplaceMarker({lat: amenityDetail.latitude, lng: amenityDetail.longitude}, amenityDetail.name, map);
                    });
                });
            };
            $scope.toggle = function () {
                $scope.amenities = !$scope.amenities;
            };
            $scope.propertySteps = [
                'Amenities',
                'Work Places',
                'Projects',                
                'Overview'
            ];
            $scope.selection = $scope.propertySteps[0];
            console.log("What is Selection :%O", $scope.selection);

            $scope.$watch('selection', function (newSelection) {
                console.log("Getting the changed selection :%O", newSelection);
                if (newSelection === "Work Places") {
                    $scope.workplaces = AmenityCodeService.findWorkplaces();
                    console.log("Workplaces List :%O", $scope.workplaces);
                } else if (newSelection === "Projects") {

                } else if (newSelection === "Properties") {

                }
            });

            $scope.amenityCodes = AmenityCodeService.findByTabName({
                'name': $scope.selection
            });
//            $scope.locations = [];
            $scope.getCurrentStepIndex = function () {
                // Get the index of the current step given selection
                return _.indexOf($scope.propertySteps, $scope.selection);
            };
//            // Go to a defined step index
            $scope.goToStep = function (index) {
                if (!_.isUndefined($scope.propertySteps[index]))
                {
                    $scope.selection = $scope.propertySteps[index];
                }
            };
//            $scope.amenityCodes = AmenityCodeService.query();
            console.log("Amenity Codes :%O", $scope.amenityCodes);
            $scope.getAmenityByAmenityCode = function (amenityCode) {
                console.log("Amenity COde :%O", amenityCode);
                $scope.amenitiesList = AmenityService.findByAmenityCode({
                    'amenityCodeId': amenityCode.id
                });
                console.log("Amenities List :%O", $scope.amenitiesList);
            };
            $scope.getWorkplaceByAmenityCode = function (amenityCode) {
                console.log("Amenity COde :%O", amenityCode);
                $scope.amenitiesWorkplaceList = AmenityService.findByAmenityCode({
                    'amenityCodeId': amenityCode.id
                });
                console.log("Amenities List :%O", $scope.amenitiesWorkplaceList);
            };
            $scope.myValue = true;
            $scope.getPropertyStep = function (propertyStep) {
                console.log("Property Step :%O", propertyStep);
                $scope.selection = propertyStep;
                if (propertyStep === "Amenities") {
//                    $scope.amenityCodes = AmenityCodeService.findByTabName({
//                       'name' : AMENITIES
//                    });
                    $scope.myValue = true;
                } else if (propertyStep === "Work Places") {
                    $scope.myWorkplaces = true;
                    $scope.myValue = false;
                    $scope.myProjects = false;
                    $scope.myProperties = false;
                    PropertyService.get({
                        'id': $stateParams.propertyId
                    }, function (property) {
                        var mapContainer1 = document.getElementById("propertyDetailMapContainerWorkplaces");
                        var propertyCoordinate = new google.maps.LatLng(property.latitude, property.longitude);
                        var mapProp = {
                            center: propertyCoordinate,
                            zoom: 15,
                            mapTypeId: google.maps.MapTypeId.ROADMAP
                        };
                        drawMap1(mapProp, mapContainer1);
                        var drawMarker1 = new google.maps.Marker({
                            map: map1,
                            position: propertyCoordinate,
                            title: property.name,
                            icon: 'images/icons_svg/dot.png'
                        });
                        var myCity1 = new google.maps.Circle({
                            center: new google.maps.LatLng(property.latitude, property.longitude),
                            radius: 5000,
                            strokeColor: "#87C4C2",
                            strokeOpacity: 0.8,
                            strokeWeight: 2,
                            fillColor: "#C1E6E5",
                            fillOpacity: 0.2,
                            zoom: 13
                        });
                        myCity1.setMap(map1);
                        drawMarker1.setMap(map1);
                        map1.fitBounds(myCity1.getBounds());
                    });
                }
                else if (propertyStep === "Projects") {
                    $scope.myProjects = true;
                    $scope.myWorkplaces = false;
                    $scope.myProperties = false;
                    $scope.myValue = false;
                    PropertyService.get({
                        'id': $stateParams.propertyId
                    }, function (property) {
                        var mapContainer2 = document.getElementById("propertyDetailMapContainerProjects");
                        var propertyCoordinate = new google.maps.LatLng(property.latitude, property.longitude);
                        var mapProp = {
                            center: propertyCoordinate,
                            zoom: 15,
                            mapTypeId: google.maps.MapTypeId.ROADMAP
                        };
                        drawMap2(mapProp, mapContainer2);
                        var drawMarker2 = new google.maps.Marker({
                            map: map2,
                            position: propertyCoordinate,
                            title: property.name,
                            icon: 'images/icons_svg/dot.png'
                        });
                        var myCity2 = new google.maps.Circle({
                            center: propertyCoordinate,
                            radius: 5000,
                            strokeColor: "#87C4C2",
                            strokeOpacity: 0.8,
                            strokeWeight: 2,
                            fillColor: "#C1E6E5",
                            fillOpacity: 0.2,
                            zoom: 13
                        });
                        myCity2.setMap(map2);
                        drawMarker2.setMap(map2);
                        map2.fitBounds(myCity2.getBounds());
                    });
                }
//                else if (propertyStep === "Properties") {
//                    $scope.myProperties = true;
//                    $scope.myWorkplaces = false;
//                    $scope.myProjects = false;
//                    $scope.myValue = false;
//                    LocationService.get({
//                        'id': $stateParams.locationId
//                    }, function (location) {
//                        var mapContainer3 = document.getElementById("locationDetailMapContainerProperties");
//                        var locationCoordinate = new google.maps.LatLng(location.latitude, location.longitude);
//                        var mapProp = {
//                            center: locationCoordinate,
//                            zoom: 15,
//                            mapTypeId: google.maps.MapTypeId.ROADMAP
//                        };
//                        drawMap3(mapProp, mapContainer3);
//                        var drawMarker3 = new google.maps.Marker({
//                            map: map3,
//                            position: locationCoordinate,
//                            title: location.name,
//                            icon: 'images/icons_svg/dot.png'
//                        });
//                        var myCity3 = new google.maps.Circle({
//                            center: new google.maps.LatLng(location.latitude, location.longitude),
//                            radius: 5000,
//                            strokeColor: "#87C4C2",
//                            strokeOpacity: 0.8,
//                            strokeWeight: 2,
//                            fillColor: "#C1E6E5",
//                            fillOpacity: 0.2,
//                            zoom: 13
//                        });
//                        myCity3.setMap(map3);
//                        drawMarker3.setMap(map3);
//                        map3.fitBounds(myCity3.getBounds());
//                    });
//                } 
                else {
                    $scope.myValue = false;
                    $scope.myWorkplaces = false;
                    $scope.myProjects = false;
                    $scope.myProperties = false;
                }
            };
        });
//
////angular.module("safedeals.states.property", [])
//        .config(function ($stateProvider, templateRoot) {
//            $stateProvider.state("main.property", {
//                'url': '/property',
//                'templateUrl': templateRoot + '/property/property_content.html',
//                'controller': 'PropertyController'
//            });
//            $stateProvider.state("main.property.property_map_container", {
//                'url': '/property_map',
//                'templateUrl': templateRoot + '/property/property_right_sidebar/properties.html',
//                'controller': 'PropertyMapController'
//            });
//            $stateProvider.state("main.property.project_map_container", {
//                'url': '/project_map',
//                'templateUrl': templateRoot + '/property/property_right_sidebar/projects.html',
//                'controller': 'ProjectMapController'
//            });
//            $stateProvider.state("main.property.school_map_container", {
//                'url': '/school_map',
//                'templateUrl': templateRoot + '/property/property_right_sidebar/schools.html',
//                'controller': 'SchoolMapController'
//            });
//        })
//
//
//        .controller('PropertyController', function ($scope, CoordinateService, PropertyService, MapService, templateRoot) {
//            $scope.slabs = [
//                'INR 5Lac',
//                'INR 25Lac',
//                'INR 50Lac',
//                'INR 75Lac'
//            ];
//            $scope.getMinSlabValue = function(slab){
//                console.log(slab);
//            };
//        })
//        .controller('PropertyMapController', function ($scope, CoordinateService, PropertyService, MapService, templateRoot) {
//            $scope.mapData = {
//                'mapContainer': document.getElementById('mapContainer'),
//                'mapCenter': {
//                    'lat': 21.1500,
//                    'lng': 79.0900
//                },
//                'markers': [
//                ]
//            };
//            $scope.properties = PropertyService.findByLocationId({
//                'locationId': 1
//            }, function (properties) {
//                angular.forEach(properties, function (property) {
//                    $scope.mapData.markers.push({
//                        'lat': property.latitude,
//                        'lng': property.longitude,
//                        'title': property.name
//                    });
//                });
//                MapService.drawMap($scope.mapData);
//            });
////            console.log("mapData", $scope.mapData.markers);
//
//
////            var nagpur = new google.maps.LatLng(21.1500, 79.0900);
////            var dhantoliNagpur = new google.maps.LatLng(21.1418, 79.0843);
////            var initialize = function () {
////                var mapContainer = document.getElementById('mapContainer');
////                var mapProp = {
////                    center: nagpur,
////                    zoom: 13,
////                    mapTypeId: google.maps.MapTypeId.ROADMAP
////                };
////
////                var map = new google.maps.Map(mapContainer, mapProp);
////                var myCity = new google.maps.Circle({
////                    center: dhantoliNagpur,
////                    radius: 500,
////                    editable: true,
////                    draggable: true,
////                    strokeColor: "#0000FF",
////                    strokeOpacity: 0.5,
////                    strokeWeight: 2,
////                    fillColor: "#0000FF",
////                    fillOpacity: 0.2
////                });
////                var marker = new google.maps.Marker({
////                    position: dhantoliNagpur,
////                    map: map,
//////                    icon: 'images/map_markers/office-building.png',
////                    title: 'Dhantoli'
////                });
////                var contentString = '<div id="content">' +
////                        '<div id="siteNotice">' +
////                        '</div>' +
////                        '<div id="bodyContent">' +
////                        '<p><b>Himalaya Mansion</b></br>P. S. Road, Dhantoli<hr>Rs.65 lakhs</br>2 bhk Appartment</p>' +
////                        '</div>' +
////                        '</div>';
////
////                var infowindow = new google.maps.InfoWindow({
////                    content: contentString
////                });
////                marker.addListener('mouseover', function () {
////                    infowindow.open(map, marker);
////                });
////                marker.addListener('mouseout', function () {
////                    infowindow.close(map, marker);
////                });
////                myCity.setMap(map);
////            };
//////            google.maps.event.addDomListener(window, 'load', initialize);
////            initialize();
//        })
//        .controller('ProjectMapController', function ($scope, ProjectService, MapService, templateRoot) {
//            $scope.mapData = {
//                'mapContainer': document.getElementById('mapContainer'),
//                'mapCenter': {
//                    'lat': 21.104836,
//                    'lng': 79.003682
//                },
//                'markers': [
//                ]
//            };
//            $scope.projects = ProjectService.findByLocationId({
//                'locationId': 1
//            }, function (projects) {
//                angular.forEach(projects, function (project) {
//                    console.log("project", project);
//                    $scope.mapData.markers.push({
//                        'lat': project.latitude,
//                        'lng': project.longitude,
//                        'title': project.name
//                    });
//                });
//                MapService.drawMap($scope.mapData);
//            });
//        })
//        .controller('SchoolMapController', function ($scope, SchoolService, MapService, templateRoot) {
//            $scope.mapData = {
//                'mapContainer': document.getElementById('mapContainer'),
//                'mapCenter': {
//                    'lat': 21.104836,
//                    'lng': 79.003682
//                },
//                'markers': [
//                ]
//            };
//            $scope.schools = SchoolService.findByLocationId({
//                'locationId': 1
//            }, function (schools) {
//                angular.forEach(schools, function (school) {
//                    console.log("school", school);
//                    $scope.mapData.markers.push({
//                        'lat': school.latitude,
//                        'lng': school.longitude,
//                        'title': school.name
//                    });
//                });
//                MapService.drawMap($scope.mapData);
//            });
//        });