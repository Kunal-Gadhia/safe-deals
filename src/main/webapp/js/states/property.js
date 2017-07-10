angular.module("safedeals.states.property", ['bootstrapLightbox'])
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
            $stateProvider.state("main.property_detail", {
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

        .controller('PropertyController', function ($scope, $state, $filter, PriceRangeService, LocationService, PropertyService, PropertyTypeService, LocationService, $stateParams, MarketPriceService, CityService, StateService) {
            console.log("State Params :%O", $stateParams);
            $scope.hideCompareButton = true;
            $scope.hideSutaibleProperty = true;
            $scope.hideDescription = true;

            if ($stateParams.cityId !== null) {
                console.log("inside This Thing");
                CityService.get({
                    'id': $stateParams.cityId
                }, function (cityObject) {
                    $scope.cityName = cityObject.name;
                    $scope.cityId = cityObject.id;
                    $scope.city = cityObject;

                    StateService.get({
                        'id': cityObject.stateId
                    }, function (stateObject) {
                        $scope.stateName = stateObject.name;
                        $scope.stateId = stateObject.id;
                        $scope.state = stateObject;
                    });

                    LocationService.get({
                        'id': $stateParams.locationId
                    }, function (locationObject) {
                        $scope.locationId = locationObject.id;
                        $scope.locationName = locationObject.name;
                        $scope.location = locationObject;
                    });
                    $scope.minBudget = $stateParams.minBudget;
                    $scope.maxBudget = $stateParams.maxBudget;
//                    $("#minBudget").val($stateParams.minBudget);
//                    $("#maxBudget").val($stateParams.maxBudget);
                    $scope.propertySize = $stateParams.propertySize;
                    console.log("Min Budget :%O", $scope.minBudget);
                    $scope.hideDescription = false;
                });

            }



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
            $scope.minBudgetList = PriceRangeService.findAllList();
            $scope.maxBudgetList = PriceRangeService.findAllList();
            console.log("Min Budghet List :%O", $scope.minBudgetList);

            $scope.$watch('minBudget', function (data) {
                console.log("Data", data);
                PriceRangeService.findByMinBudget({
                    'minBudget': data
                }, function (priceRange) {
                    console.log("Price Range :%O", priceRange);
                    $scope.maxBudgetList = priceRange;
                });
            });

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
                        $scope.hideSutaibleProperty = false;
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
                $scope.length = $scope.selectedPropertyList.length;
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

        .controller('PropertyDetailController', function ($scope, $filter, CityService, BankService, PrivateAmenitiesService, TransportationService, RoadService, PropertyService, AmenityDetailService, HospitalService, AmenityCodeService, AmenityService, LocationService, MallService, CoordinateService, BranchService, SchoolService, PropertyService, PropertyTypeService, ProjectService, $stateParams, ImageService, VideoService) {
            $scope.images = [];
            $scope.videos = [];
            $scope.myInterval = 3000;
            ImageService.findByPropertyId({
                'propertyId': $stateParams.propertyId
            }, function (images) {
                angular.forEach(images, function (image) {
                    $scope.images.push(image);
                });
            });

            VideoService.findByPropertyId({
                'propertyId': $stateParams.propertyId
            }, function (videos) {
                console.log("Videos :%O", videos);
                angular.forEach(videos, function (data) {
                    $scope.video = data;
                    console.log(" $scope.video ", $scope.video);
                });
            });

            $scope.map;
            $scope.map1;
            $scope.map2;
            $scope.map3;
            var mapContainer = document.getElementById("propertyDetailMapContainer");
            $scope.infowindow = new google.maps.InfoWindow();
            $scope.directionsService = new google.maps.DirectionsService();
            $scope.directionsDisplay = new google.maps.DirectionsRenderer({
                'preserveViewport': true,
                'suppressMarkers': true
            });
            $scope.distanceService = new google.maps.DistanceMatrixService();
            console.log("$stateparams ID::::::", $stateParams.propertyId);
            var drawMap = function (mapProperty) {
                console.log("Coming To Draw Map %O", mapContainer);
                $scope.map = new google.maps.Map(mapContainer, mapProperty);
            };
            var drawMap1 = function (mapProperty, mapContainer1) {
                console.log("Coming To Draw Map 1 :" + mapContainer1);
                $scope.map1 = new google.maps.Map(mapContainer1, mapProperty);
            };
            var drawMap2 = function (mapProperty, mapContainer2) {
                console.log("Coming To Draw Map 2 :" + mapContainer2);
                $scope.map2 = new google.maps.Map(mapContainer2, mapProperty);
            };
            var drawMap3 = function (mapProperty, mapContainer3) {
                console.log("Coming To Draw Map 3 :" + mapContainer3);
                $scope.map3 = new google.maps.Map(mapContainer3, mapProperty);
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
                var marker = new google.maps.Marker({
                    map: $scope.map,
                    position: position,
                    title: title
                });

                google.maps.event.addListener(marker, 'click', function () {
                    $scope.infowindow.setContent(title);
                    $scope.infowindow.open(map, this);
                });
                google.maps.event.addListener(marker, 'mouseover', function (event) {
                    console.log("Detecting Hover Event :%O", event);
                    console.log("Place.Geometry.Location :%O", position);
                    console.log("property :%O", $scope.property);
                    var request = {
                        origin: new google.maps.LatLng($scope.property.latitude, $scope.property.longitude),
                        destination: position,
                        travelMode: google.maps.DirectionsTravelMode.DRIVING
                    };
                    console.log("What is request :%O", request);
                    $scope.directionsService.route(request, function (response, status) {
                        console.log("Response :%O", response);
                        if (status == google.maps.DirectionsStatus.OK) {
                            console.log("Status is OK");
                            $scope.directionsDisplay.setDirections(response);
                            $scope.directionsDisplay.setMap(map);
                        }
                    });
                    var distanceRequest = {
                        origins: [new google.maps.LatLng($scope.property.latitude, $scope.property.longitude)],
                        destinations: [position],
                        travelMode: google.maps.DirectionsTravelMode.DRIVING
                    };
                    $scope.distanceService.getDistanceMatrix(distanceRequest, function (response, status) {
                        console.log("Response in distance :%O", response);
                        console.log("Distance is :%O", response.rows[0].elements[0].distance.text);
//                        $scope.distanceBox(title, response.rows[0].elements[0].distance.text);
                    });

                });
            };

            //** changed from lcation to property **//
            $scope.createAmenityMarker = function (place, property, map) {
                console.log("Place in marker :%O", place);
                console.log("Location :%O", property);
                console.log("Map :%O", map);
                var placeLoc = place.geometry.property;
                var marker = new google.maps.Marker({
                    map: map,
                    position: place.geometry.property,
                    title: place.name
                });
                google.maps.event.addListener(marker, 'click', function () {
                    $scope.infowindow.setContent(place.name);
                    $scope.infowindow.open(map, this);
                });
                google.maps.event.addListener(marker, 'mouseover', function (event) {
                    console.log("Detecting Hover Event :%O", event);
                    console.log("Place.Geometry.Location :%O", place.geometry.location);
                    var request = {
                        origin: new google.maps.LatLng(property.latitude, property.longitude),
                        destination: place.geometry.location,
                        travelMode: google.maps.DirectionsTravelMode.DRIVING
                    };
                    $scope.directionsService.route(request, function (response, status) {
                        console.log("Response :%O", response);
                        if (status == google.maps.DirectionsStatus.OK) {
                            console.log("Status is OK");
                            $scope.directionsDisplay.setDirections(response);
                            $scope.directionsDisplay.setMap(map);
                        }
                    });
                    var distanceRequest = {
                        origins: [new google.maps.LatLng(property.latitude, property.longitude)],
                        destinations: [place.geometry.location],
                        travelMode: google.maps.DirectionsTravelMode.DRIVING
                    };
                    $scope.distanceService.getDistanceMatrix(distanceRequest, function (response, status) {
                        console.log("Response :%O", response);
                        console.log("Distance is :%O", response.rows[0].elements[0].distance.text);
                    });
                });
                drawMarker({lat: property.latitude, lng: property.longitude}, property.name, map);
                new google.maps.Circle({
                    center: new google.maps.LatLng(property.latitude, property.longitude),
                    radius: 5000,
                    strokeColor: "#87C4C2",
                    strokeOpacity: 0.8,
                    strokeWeight: 2,
                    fillColor: "#C1E6E5",
                    // fillColor: "#FF69B4",
                    fillOpacity: '0.2',
                    zoom: 13
                });
            };
            var drawWorkplaceMarker = function (position, title, propertyLocation, map) {
                console.log("Position 1 :%O", position);
                var marker = new google.maps.Marker({
                    map: $scope.map1,
                    position: position,
                    title: title
                });
                google.maps.event.addListener(marker, 'click', function () {
                    $scope.infowindow.setContent(title);
                    $scope.infowindow.open($scope.map1, this);
                });
                google.maps.event.addListener(marker, 'mouseover', function (event) {
                    console.log("Detecting Hover Event :%O", event);
                    console.log("Place.Geometry.Location Main Location:%O", position);
                    console.log(" Property Object :%O", $scope.property);
                    var request = {
                        origin: new google.maps.LatLng($scope.property.latitude, $scope.property.longitude),
                        destination: position,
                        travelMode: google.maps.DirectionsTravelMode.DRIVING
                    };
                    $scope.directionsService.route(request, function (response, status) {
                        console.log("Response :%O", response);
                        if (status == google.maps.DirectionsStatus.OK) {
                            console.log("Status is OK");
                            $scope.directionsDisplay.setDirections(response);
                            $scope.directionsDisplay.setMap($scope.map1);
                        }
                    });
                    var distanceRequest = {
                        origins: [new google.maps.LatLng(propertyLocation.latitude, propertyLocation.longitude)],
                        destinations: [position],
                        travelMode: google.maps.DirectionsTravelMode.DRIVING
                    };
                    $scope.distanceService.getDistanceMatrix(distanceRequest, function (response, status) {
                        console.log("Response :%O", response);
                        console.log("Distance is :%O", response.rows[0].elements[0].distance.text);
                        $scope.amenityDistance = response.rows[0].elements[0].distance.text;
                    });
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
                console.log("into Property thing :%O", property);
                property.city = CityService.get({
                    'id': property.cityId
                });
                property.location = LocationService.get({
                    'id': property.locationId
                });
                property.project = ProjectService.get({
                    'id': property.projectId
                });
                property.road = RoadService.get({
                    'id': property.majorApproachRoad
                });
                property.propertySizeObject = PropertyTypeService.get({
                    'id': property.propertySize
                });
                property.publicTransportObjects = [];
                console.log("Public Transport :%O", property.publicTransport);
                angular.forEach(property.publicTransport, function (publicTransport) {
                    property.publicTransportObjects.push(TransportationService.get({
                        'id': publicTransport
                    }));
                    console.log("property.publicTransportObjects %O", property.publicTransportObjects);
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

                $scope.property = property;
                console.log("Property Object FInal :%O", $scope.property);

                var propertyCoordinate = new google.maps.LatLng(property.latitude, property.longitude);
                var mapProp = {
                    center: propertyCoordinate,
                    zoom: 15,
                    mapTypeId: google.maps.MapTypeId.ROADMAP
                };
                drawMap(mapProp);
                drawMarker({lat: property.latitude, lng: property.longitude}, property.name, $scope.map);
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
                myCity.setMap($scope.map);
                $scope.map.fitBounds(myCity.getBounds());
            });

            //////////////////////Distance Calculator Manual////////////////////
            $scope.getDistanceFromLatLonInKm = function (lat1, lon1, lat2, lon2) {
                console.log("Lat 1 :%O", lat1);
                console.log("Lat 2 :%O", lat2);
                console.log("Long 1 :%O", lon1);
                console.log("Long 2 :%O", lon2);
                var R = 6371; // Radius of the earth in km
                var dLat = $scope.deg2rad(lat2 - lat1);  // deg2rad below
                var dLon = $scope.deg2rad(lon2 - lon1);
                var a =
                        Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                        Math.cos($scope.deg2rad(lat1)) * Math.cos($scope.deg2rad(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2)
                        ;
                var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
                var d = R * c; // Distance in km
                return d;
            };

            $scope.deg2rad = function (deg) {
                return deg * (Math.PI / 180);
            };
            ////////////////////////////////////////////////////////////////////


            $scope.getAmenityDetailByAmenity = function (amenityDetail) {
                console.log("Property Object :%O", $scope.property);
                $scope.requiredAmenities = [];
                if (amenityDetail.name === "Daily Needs") {
                    $scope.requiredAmenities.push(amenityDetail.name);
                    console.log("Required AMenities :%O", $scope.requiredAmenities);
                    var request = {
                        location: new google.maps.LatLng($scope.property.latitude, $scope.property.longitude),
                        radius: 5000,
                        types: ['convenience_store', 'department_store']
                    };
                    var service = new google.maps.places.PlacesService($scope.map);
                    service.nearbySearch(request, callback);
                    function callback(results, status) {
                        console.log("Results For Schools :%O", results);
                        angular.forEach(results, function (result) {
                            console.log("Result in Loop :%O", result);
                            $scope.createAmenityMarker(result, $scope.property, $scope.map);
                        });
                    }
                    ;
                }

                else if (amenityDetail.name === "Public Transport") {
                    $scope.requiredAmenities.push(amenityDetail.name);
                    console.log("Required AMenities :%O", $scope.requiredAmenities);
                    var request = {
                        location: new google.maps.LatLng($scope.property.latitude, $scope.property.longitude),
                        radius: 5000,
                        types: ['taxi_stand', 'transit_station']
                    };
                    var service = new google.maps.places.PlacesService($scope.map);
                    service.nearbySearch(request, callback);
                    function callback(results, status) {
                        console.log("Results For Schools :%O", results);
                        angular.forEach(results, function (result) {
                            console.log("Result in Loop :%O", result);
                            $scope.createAmenityMarker(result, $scope.property, $scope.map);
                        });
                    }
                    ;
                }

                else if (amenityDetail.name === "Cafe") {
                    $scope.requiredAmenities.push(amenityDetail.name);
                    console.log("Required AMenities :%O", $scope.requiredAmenities);
                    var request = {
                        location: new google.maps.LatLng($scope.property.latitude, $scope.property.longitude),
                        radius: 5000,
                        types: ['cafe']
                    };
                    var service = new google.maps.places.PlacesService($scope.map);
                    service.nearbySearch(request, callback);
                    function callback(results, status) {
                        console.log("Results For Schools :%O", results);
                        angular.forEach(results, function (result) {
                            console.log("Result in Loop :%O", result);
                            $scope.createAmenityMarker(result, $scope.property, $scope.map);
                        });
                    }
                    ;
                }

                else if (amenityDetail.name === "Hospital") {
                    $scope.requiredAmenities.push(amenityDetail.name);
                    console.log("Required AMenities :%O", $scope.requiredAmenities);
                    var request = {
                        location: new google.maps.LatLng($scope.property.latitude, $scope.property.longitude),
                        radius: 5000,
                        types: ['hospital']
                    };
                    var service = new google.maps.places.PlacesService($scope.map);
                    service.nearbySearch(request, callback);
                    function callback(results, status) {
                        console.log("Results For Schools :%O", results);
                        angular.forEach(results, function (result) {
                            console.log("Result in Loop :%O", result);
                            $scope.createAmenityMarker(result, $scope.property, $scope.map);
                        });
                    }
                    ;
                }
                else if (amenityDetail.name === "Places of Workship") {
                    console.log("Inside Places of workship");
                    $scope.requiredAmenities.push(amenityDetail.name);
                    console.log("Required Amenities :%O", $scope.requiredAmenities);
                    var request = {
                        location: new google.maps.LatLng($scope.property.latitude, $scope.property.longitude),
                        radius: 5000,
                        types: ['hindu_temple', 'mosque', 'church']
                    };
                    var service = new google.maps.places.PlacesService($scope.map);
                    service.nearbySearch(request, callback);
                    function callback(results, status) {
                        angular.forEach(results, function (result) {
                            console.log("Result in Loop :%O", result);
                            $scope.createAmenityMarker(result, $scope.property, $scope.map);
                        });
                    }
                    ;
                }
                else if (amenityDetail.name === "Park") {
                    $scope.requiredAmenities.push(amenityDetail.name);
                    console.log("Required AMenities :%O", $scope.requiredAmenities);
                    var request = {
                        location: new google.maps.LatLng($scope.property.latitude, $scope.property.longitude),
                        radius: 5000,
                        types: ['park']
                    };
                    var service = new google.maps.places.PlacesService($scope.map);
                    service.nearbySearch(request, callback);
                    function callback(results, status) {
                        console.log("Results For Schools :%O", results);
                        angular.forEach(results, function (result) {
                            console.log("Result in Loop :%O", result);
                            $scope.createAmenityMarker(result, $scope.property, $scope.map);
                        });
                    }
                    ;
                }
                else if (amenityDetail.name === "Bank") {
                    $scope.requiredAmenities.push(amenityDetail.name);
                    console.log("Required AMenities :%O", $scope.requiredAmenities);
                    var request = {
                        location: new google.maps.LatLng($scope.property.latitude, $scope.property.longitude),
                        radius: 5000,
                        types: ['bank']
                    };
                    var service = new google.maps.places.PlacesService($scope.map);
                    service.nearbySearch(request, callback);
                    function callback(results, status) {
                        console.log("Results For Schools :%O", results);
                        angular.forEach(results, function (result) {
                            console.log("Result in Loop :%O", result);
                            $scope.createAmenityMarker(result, $scope.property, $scope.map);
                        });
                    }
                    ;
                }
                else if (amenityDetail.name === "ATM") {
                    $scope.requiredAmenities.push(amenityDetail.name);
                    console.log("Required AMenities :%O", $scope.requiredAmenities);
                    var request = {
                        location: new google.maps.LatLng($scope.property.latitude, $scope.property.longitude),
                        radius: 5000,
                        types: ['atm']
                    };
                    var service = new google.maps.places.PlacesService($scope.map);
                    service.nearbySearch(request, callback);
                    function callback(results, status) {
                        console.log("Results For Schools :%O", results);
                        angular.forEach(results, function (result) {
                            console.log("Result in Loop :%O", result);
                            $scope.createAmenityMarker(result, $scope.property, $scope.map);
                        });
                    }
                    ;
                }
                else if (amenityDetail.name === "School") {
                    console.log("In School If");
                    $scope.requiredAmenities.push(amenityDetail.name);
                    console.log("Required AMenities :%O", $scope.requiredAmenities);
                    var request = {
                        location: new google.maps.LatLng($scope.property.latitude, $scope.property.longitude),
                        radius: 5000,
                        types: ['school']
                    };
                    var service = new google.maps.places.PlacesService($scope.map);
                    service.nearbySearch(request, callback);
                    function callback(results, status) {
                        console.log("Results For Schools :%O", results);
                        angular.forEach(results, function (result) {
                            console.log("Result in Loop :%O", result);
                            $scope.createAmenityMarker(result, $scope.property, $scope.map);
                        });
                    }
                    ;
                }
                else if (amenityDetail.name === "College") {
                    console.log("In School If");
                    $scope.requiredAmenities.push(amenityDetail.name);
                    console.log("Required AMenities :%O", $scope.requiredAmenities);
                    var request = {
                        location: new google.maps.LatLng($scope.property.latitude, $scope.property.longitude),
                        radius: 5000,
                        types: ['school']
                    };
                    var service = new google.maps.places.PlacesService($scope.map);
                    service.nearbySearch(request, callback);
                    function callback(results, status) {
                        console.log("Results For Schools :%O", results);
                        angular.forEach(results, function (result) {
                            console.log("Result in Loop :%O", result);
                            $scope.createAmenityMarker(result, $scope.property, $scope.map);
                        });
                    }
                    ;
                }
                else if (amenityDetail.name === "Petrol Pump") {
                    console.log("In School If");
                    $scope.requiredAmenities.push(amenityDetail.name);
                    console.log("Required AMenities :%O", $scope.requiredAmenities);
                    var request = {
                        location: new google.maps.LatLng($scope.property.latitude, $scope.property.longitude),
                        radius: 5000,
                        types: ['gas_station']
                    };
                    var service = new google.maps.places.PlacesService($scope.map);
                    service.nearbySearch(request, callback);
                    function callback(results, status) {
                        console.log("Results For Schools :%O", results);
                        angular.forEach(results, function (result) {
                            console.log("Result in Loop :%O", result);
                            $scope.createAmenityMarker(result, $scope.property, $scope.map);
                        });
                    }
                    ;
                }
                else if (amenityDetail.name === "Restaurant") {
                    console.log("In School If");
                    $scope.requiredAmenities.push(amenityDetail.name);
                    console.log("Required AMenities :%O", $scope.requiredAmenities);
                    var request = {
                        location: new google.maps.LatLng($scope.property.latitude, $scope.property.longitude),
                        radius: 5000,
                        types: ['restaurant']
                    };
                    var service = new google.maps.places.PlacesService($scope.map);
                    service.nearbySearch(request, callback);
                    function callback(results, status) {
                        console.log("Results For Schools :%O", results);
                        angular.forEach(results, function (result) {
                            console.log("Result in Loop :%O", result);
                            $scope.createAmenityMarker(result, $scope.property, $scope.map);
                        });
                    }
                    ;
                }
                else if (amenityDetail.name === "Bakery") {
                    console.log("In School If");
                    $scope.requiredAmenities.push(amenityDetail.name);
                    console.log("Required AMenities :%O", $scope.requiredAmenities);
                    var request = {
                        location: new google.maps.LatLng($scope.property.latitude, $scope.property.longitude),
                        radius: 5000,
                        types: ['bakery']
                    };
                    var service = new google.maps.places.PlacesService($scope.map);
                    service.nearbySearch(request, callback);
                    function callback(results, status) {
                        console.log("Results For Schools :%O", results);
                        angular.forEach(results, function (result) {
                            console.log("Result in Loop :%O", result);
                            $scope.createAmenityMarker(result, $scope.property, $scope.map);
                        });
                    }
                    ;
                }
                else if (amenityDetail.name === "Club") {
                    console.log("In School If");
                    $scope.requiredAmenities.push(amenityDetail.name);
                    console.log("Required AMenities :%O", $scope.requiredAmenities);
                    var request = {
                        location: new google.maps.LatLng($scope.property.latitude, $scope.property.longitude),
                        radius: 5000,
                        types: ['night_club']
                    };
                    var service = new google.maps.places.PlacesService($scope.map);
                    service.nearbySearch(request, callback);
                    function callback(results, status) {
                        console.log("Results For Schools :%O", results);
                        angular.forEach(results, function (result) {
                            console.log("Result in Loop :%O", result);
                            $scope.createAmenityMarker(result, $scope.property, $scope.map);
                        });
                    }
                    ;
                }



                $scope.amenityDetailCityFilter = {
                    'amenityId': amenityDetail.id,
                    'cityId': $scope.property.cityId
                };
                $scope.amenityDetailCityFilter.cityId = $scope.property.cityId;
                AmenityDetailService.findByAmenityIdCityId({
                    'amenityId': amenityDetail.id,
                    'cityId': $scope.property.cityId
                }, function (amenityDetailObject) {
                    $scope.amenityDetailsList = [];
                    angular.forEach(amenityDetailObject, function (amenityDetail) {
                        var d = $scope.getDistanceFromLatLonInKm($scope.property.latitude, $scope.property.longitude, amenityDetail.latitude, amenityDetail.longitude);
                        if (d <= "5") {
                            console.log("Amenity Detail :%O", amenityDetail);
                            $scope.amenityDetailsList.push(amenityDetail)
                        }
                        console.log("Amenity Details List :%O", $scope.amenityDetailsList);
                        angular.forEach($scope.amenityDetailsList, function (amenityDetail) {
                            drawAmenityMarker({lat: amenityDetail.latitude, lng: amenityDetail.longitude}, amenityDetail.name, $scope.map);
                        })
//                        drawAmenityMarker({lat: amenityDetail.latitude, lng: amenityDetail.longitude}, amenityDetail.name, $scope.map);
//                        var infoWindow = new google.maps.InfoWindow({
//                            'content': amenityDetail.name
//                        });
//                        infoWindow.open(map, drawMarker());
                    });
                });
            };
            $scope.getAmenityDetailByAmenityWorkplaces = function (amenityDetail) {
                var amenityDetailsList = [];
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
                        var d = $scope.getDistanceFromLatLonInKm($scope.property.latitude, $scope.property.longitude, amenityDetail.latitude, amenityDetail.longitude);
                        console.log("Val of d = " + d);
                        if (d <= "5") {
                            console.log("Amenity Detail inside If :%O", amenityDetail);
                            amenityDetailsList.push(amenityDetail);
                        }
                        angular.forEach(amenityDetailsList, function (amenityDetail) {
                            drawWorkplaceMarker({lat: amenityDetail.latitude, lng: amenityDetail.longitude}, amenityDetail.name, $scope.location, $scope.map1);

                        });
//                          drawWorkplaceMarker({lat: amenityDetail.latitude, lng: amenityDetail.longitude}, amenityDetail.name, $scope.property, $scope.map1);
                    });
                });
            };
            $scope.toggle = function () {
                $scope.amenities = 'clicked';
            };
            $scope.propertySteps = [
                'Amenities',
                'Work Places',
                'Projects'
//                'Overview'
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

                if (amenityCode.name === "Landmark") {

                    console.log("Required AMenities :%O", $scope.requiredAmenities);
                    var request = {
                        location: new google.maps.LatLng($scope.property.latitude, $scope.property.longitude),
                        radius: 5000,
                        types: ['point_of_interest']
                    };
                    var service = new google.maps.places.PlacesService($scope.map);
                    service.nearbySearch(request, callback);
                    function callback(results, status) {
                        console.log("Results For Schools :%O", results);
                        angular.forEach(results, function (result) {
                            console.log("Result in Loop :%O", result);
                            $scope.createAmenityMarker(result, $scope.property, $scope.map);
                        });
                    }
                    ;
                }
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
                            map: $scope.map1,
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
                        myCity1.setMap($scope.map1);
                        drawMarker1.setMap($scope.map1);
                        $scope.map1.fitBounds(myCity1.getBounds());
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

            //           image Gallery
//            $scope.galleryModal = true;
//            $scope.images = ImageService.findByPropertyId({
//                'propertyId': $stateParams.propertyId
//            }, function (images) {
//                console.log("Images :%O", images);
//            });
//            $scope.openLightboxModal = function (image) {
//                $scope.galleryModal = false;
//                $scope.tempImageId = image.id;
//                $scope.arrayImage = [];
//                console.log("What is image :" + image);
//                angular.forEach($scope.images, function (image) {
//                    $scope.arrayImage.push(image.id);
//                });
//                console.log("Array :%O", $scope.arrayImage);
////                Lightbox.openModal($scope.images, index);
//            };
//
//            $scope.closeLightboxModal = function () {
//                $scope.galleryModal = true;
//            };
            //           image Gallery
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