angular.module("safedeals.states.amenity_detail", [])
        .config(function ($stateProvider, templateRoot) {
            $stateProvider.state('admin.masters_amenity_detail', {
                'url': '/amenity_detail_master?offset',
                'templateUrl': templateRoot + '/masters/amenitydetail/list.html',
                'controller': 'AmenityDetailListController'
            });
            $stateProvider.state('admin.masters_amenity_detail.add', {
                'url': '/add',
                'templateUrl': templateRoot + '/masters/amenitydetail/form.html',
                'controller': 'AmenityDetailAddController'
            });
            $stateProvider.state('admin.masters_amenity_detail.edit', {
                'url': '/:amenity_detailId/edit',
                'templateUrl': templateRoot + '/masters/amenitydetail/form.html',
                'controller': 'AmenityDetailEditController'
            });
            $stateProvider.state('admin.masters_amenity_detail.delete', {
                'url': '/:amenity_detailId/delete',
                'templateUrl': templateRoot + '/masters/amenitydetail/delete.html',
                'controller': 'AmenityDetailDeleteController'
            });
            $stateProvider.state('admin.masters_amenity_detail.import', {
                'url': '/import',
                'templateUrl': templateRoot + '/masters/amenitydetail/import.html',
                'controller': 'AmenityDetailImportController'
            });
        })
        .controller('AmenityDetailListController', function (AmenityDetailService, WorkplaceCategoryService, LocationService, AmenityService,CityService, $scope, $stateParams, $state, paginationLimit) {
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

            $scope.nextAmenitydetails = AmenityDetailService.query({
                'offset': $scope.nextOffset
            });
            
            $scope.amenitydetails = AmenityDetailService.query({
                'offset': $scope.currentOffset
            }
            , function (amenitydetails) {
                angular.forEach(amenitydetails, function (amenitydetail) {
                    console.log("amenitydetail", amenitydetail);
                    amenitydetail.amenity = AmenityService.get({
                        'id': amenitydetail.amenityId
                    });
                    amenitydetail.workplaceCategory = WorkplaceCategoryService.get({
                        'id': amenitydetail.workplaceCategoryId
                    });
                    amenitydetail.location = LocationService.get({
                        'id': amenitydetail.locationId
                    });
                    amenitydetail.city = CityService.get({
                        'id': amenitydetail.cityId
                    });
                    console.log('amenitydetail.amenity', amenitydetail.amenity);
                });
            });
//            console.log("$scope.amenitydetails", $scope.amenitydetails);

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
            $scope.amenityDetailExport = function () {
                console.log("amenityDetailExport");
                AmenityDetailService.exportAllLocations(function (a) {
                    alert("Downloaded successfully");
                });
            };
        })
        .controller('AmenityDetailAddController', function (AmenityDetailService, AmenityService, LocationService, WorkplaceCategoryService, CityService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editableAmenityDetail = {};
            $scope.setAmenity = function (amenity) {
                $scope.editableAmenityDetail.amenityId = amenity.id;
                $scope.editableAmenityDetail.amenity = amenity;
            };
            $scope.searchAmenity = function (searchTerm) {
                console.log("searchTerm", searchTerm);
                return AmenityService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };
            $scope.setWorkplaceCategory = function (workplaceCategory) {
                $scope.editableAmenityDetail.workplaceCategoryId = workplaceCategory.id;
                $scope.editableAmenityDetail.workplaceCategory = workplaceCategory;
            };
            $scope.searchWorkplaceCategory = function (searchTerm) {
                console.log("searchTerm", searchTerm);
                return WorkplaceCategoryService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };
            $scope.setLocation = function (location) {
                $scope.editableAmenityDetail.locationId = location.id;
                $scope.editableAmenityDetail.location = location;
            };
            $scope.searchLocations = function (searchTerm) {
                 if ($scope.editableAmenityDetail.cityId === undefined) {
                    return LocationService.findByNameLike({
                        'name': searchTerm
                    }).$promise;
                } else {
                    return LocationService.findByNameAndCityId({
                        'name': searchTerm,
                        'cityId': $scope.editableAmenityDetail.cityId
                    }).$promise;
                }
            };
            $scope.setCity = function (city) {
                $scope.editableAmenityDetail.cityId = city.id;
                $scope.editableAmenityDetail.city = city;
            };
            $scope.searchCities = function (searchTerm) {
                console.log("Search Term :%O", searchTerm);
                return CityService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };

            $scope.$watch('editableAmenityDetail.name', function (name) {
                console.log("Branch Name :" + name);
                AmenityDetailService.findByAmenityDetailName({'name': name}).$promise.catch(function (response) {
                    if (response.status === 500) {
                        $scope.editableAmenityDetail.repeatAmenityDetail = false;
                    }
                    else if (response.status === 404) {
                        $scope.editableAmenityDetail.repeatAmenityDetail = false;
                    }
                    else if (response.status === 400) {
                        $scope.editableAmenityDetail.repeatAmenityDetail = false;
                    }
                }).then(function (amenityDetail) {
                    if (amenityDetail.name !== null) {
                        $scope.editableAmenityDetail.repeatAmenityDetail = true;
                    }
                    ;
                });
            });

            $scope.saveAmenityDetail = function (amenitydetail) {
                console.log("amenitydetail name:" + amenitydetail.name);
                AmenityDetailService.save(amenitydetail, function () {
                    $state.go('admin.masters_amenity_detail', null, {'reload': true});
                });
            };
        })
        .controller('AmenityDetailEditController', function (AmenityDetailService, AmenityService, LocationService, WorkplaceCategoryService,CityService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editableAmenityDetail = AmenityDetailService.get({
                'id': $stateParams.amenity_detailId
            }, function () {
                $scope.editableAmenityDetail.amenity = AmenityService.get({
                    'id': $scope.editableAmenityDetail.amenityId
                });
                $scope.editableAmenityDetail.workplaceCategory = WorkplaceCategoryService.get({
                    'id': $scope.editableAmenityDetail.workplaceCategoryId
                });
                $scope.editableAmenityDetail.location = LocationService.get({
                    'id': $scope.editableAmenityDetail.locationId
                });
                $scope.editableAmenityDetail.city = CityService.get({
                    'id': $scope.editableAmenityDetail.cityId
                });
            });
            console.log("$scope.editableAmenityDetail", $scope.editableAmenityDetail);
            $scope.setAmenity = function (amenity) {
                $scope.editableAmenityDetail.amenityId = amenity.id;
                $scope.editableAmenityDetail.amenity = amenity;
            };
            $scope.searchAmenity = function (searchTerm) {
                console.log("searchTerm", searchTerm);
                return AmenityService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };
            $scope.setWorkplaceCategory = function (workplaceCategory) {
                $scope.editableAmenityDetail.workplaceCategoryId = workplaceCategory.id;
                $scope.editableAmenityDetail.workplaceCategory = workplaceCategory;
            };
            $scope.searchWorkplaceCategory = function (searchTerm) {
                console.log("searchTerm", searchTerm);
                return WorkplaceCategoryService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };
            $scope.setLocation = function (location) {
                $scope.editableAmenityDetail.locationId = location.id;
                $scope.editableAmenityDetail.location = location;
            };
           $scope.searchLocations = function (searchTerm) {
                 if ($scope.editableAmenityDetail.cityId === undefined) {
                    return LocationService.findByNameLike({
                        'name': searchTerm
                    }).$promise;
                } else {
                    return LocationService.findByNameAndCityId({
                        'name': searchTerm,
                        'cityId': $scope.editableAmenityDetail.cityId
                    }).$promise;
                }
            };
            $scope.setCity = function (city) {
                $scope.editableAmenityDetail.cityId = city.id;
                $scope.editableAmenityDetail.city = city;
            };
            $scope.searchCities = function (searchTerm) {
                console.log("Search Term :%O", searchTerm);
                return CityService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };
            $scope.saveAmenityDetail = function (amenitydetail) {
                console.log("amenitydetail", amenitydetail);
                amenitydetail.$save(function () {
                    $state.go('admin.masters_amenity_detail', null, {'reload': true});
                });
            };
//            console.log("$scope.editableAmenityDetail" , $scope.editableAmenityDetail);
        })
        .controller('AmenityDetailDeleteController', function (AmenityDetailService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editableAmenityDetail = AmenityDetailService.get({'id': $stateParams.amenity_detailId});
            console.log("are we here?", $scope.editableAmenityDetail.name);
            $scope.deleteAmenityDetail = function (amenitydetail) {
                amenitydetail.$delete(function () {
                    $state.go('admin.masters_amenity_detail', null, {'reload': true});
                });
            };
        }) /*AmenityDetailService, LocationService, CityService, */
        .controller('AmenityDetailImportController', function (AmenityDetailService, AmenityService, CityService, LocationService, RawReadyReckonerService, $scope, $stateParams, $state, paginationLimit) {
            var map;
            var mapContainer = document.getElementById("amenityDetailImportMapContainer");
            var mapCenter = new google.maps.LatLng(20.5937, 78.9629);
            var mapProp = {
                center: mapCenter,
                zoom: 4,
                mapTypeId: google.maps.MapTypeId.ROADMAP
            };
            $scope.cities = CityService.findAllCities();
            console.log("$scope.cities", $scope.cities);
            $scope.requiredAmenities = [];
            AmenityService.findAllAmenities(function (amenities) {
                angular.forEach(amenities, function (amenity) {
                    $scope.requiredAmenities.push(amenity.amenityCode);
                });
            });
            console.log("$scope.amenities", $scope.amenities);
            $scope.locations = [];
            $scope.amenity = [];
            $scope.setCity = function (city) {
                $scope.cityId = city.id;
                $scope.city = city;
                console.log("$scope.city", $scope.city);
                map.setCenter(new google.maps.LatLng(city.latitude, city.longitude));
                map.setZoom(12);
                var request = {
                    location: map.getCenter(),
                    radius: city.radius,
                    types: $scope.requiredAmenities
                };

//                var service = new google.maps.places.PlacesService(map);
//                service.radarSearch(request, callback);
//                function callback(results, status) {
//                    console.log("results ", results);
//                    angular.forEach(results, function (result) {
//                        var request = {
//                            placeId: result.place_id
//                        };
//                        service.getDetails(request, callback2);
//                    });
//                    function callback2(place, status) {
//                        console.log("place in callback2", place);
//                        console.log("status in callback2", status);
//                        if (status === google.maps.places.PlacesServiceStatus.OK) {
//                            createMarker(place);
//                        }
//                    }
//                    function createMarker(place) {
//                        var marker = new google.maps.Marker({
//                            map: map,
//                            position: place.geometry.location,
//                            title: place.name
//                        });
//                    }
//                }
            };
            var showMap = function () {
                map = new google.maps.Map(mapContainer, mapProp);
                console.log("map", map);
            };
            $scope.getAmenityDetail = function (a) {
                $scope.amenityId = a.id;
                console.log("a", a);
                console.log("city", $scope.city);
                var infowindow = new google.maps.InfoWindow();
                service = new google.maps.places.PlacesService(map);
                service.textSearch({
                    location: map.getCenter(),
                    radius: $scope.city.radius,
                    query: a.name
                }, callback);
            };
            function callback(results, status) {
                $scope.results = results;
//                createMarker(null);
//                console.log("results", results);
                if (status === google.maps.places.PlacesServiceStatus.OK) {
                    for (var i = 0; i < results.length; i++) {
                        createMarker(results[i]);
                    }
                }
                angular.forEach($scope.results, function (result) {
//                    console.log("result", result);
//                    angular.forEach($scope.results, function (result) {
//                        var request = {
//                            placeId: result.place_id
//                        };
//                        service.getDetails(request, callback2);
//                    });
//                    function callback2(place, status) {
//                        console.log("place in callback2", place);
//                        $scope.phoneNo = place.formatted_phone_number;
////                        console.log("status in callback2", status);
////                        if (status === google.maps.places.PlacesServiceStatus.OK) {
////                            createMarker(place);
////                        }
//                    }
                    $scope.amenityDetailResult = {};
                    $scope.amenityDetailResult.name = result.name;
                    $scope.amenityDetailResult.amenityId = $scope.amenityId;
                    $scope.amenityDetailResult.address = result.formatted_address;
//                    $scope.amenityDetailResult.phoneNumber =  $scope.phoneNo;
                    $scope.amenityDetailResult.latitude = result.geometry.location.lat();
                    $scope.amenityDetailResult.longitude = result.geometry.location.lng();
//                    console.log("amenityDetailResult", $scope.amenityDetailResult);
                    AmenityDetailService.save($scope.amenityDetailResult, function () {
                        $state.go('admin.masters_amenity_detail', null, {'reload': true});
                    });
                });
            }
            function createMarker(place) {
//                console.log("place", place);
                var placeLoc = place.geometry.location;
                var marker = new google.maps.Marker({
                    map: map,
                    position: placeLoc,
                    icon: place.icon
                });
                google.maps.event.addListener(marker, 'click', function () {
                    infowindow.setContent(place.name);
                    infowindow.open(map, this);
                })
//            angular.forEach($scope.results, function (result) {
//                    console.log("result1", result);
//                    AmenityDetailService.save(result, function () {
//                        $state.go('admin.masters_amenity_detail', null, {'reload': true});
//                    })
//                })
            }
            ;
//            $scope.saveAmenityDetailFromMap = function () {
//                angular.forEach($scope.results, function (result) {
//                    console.log("result", result);
//                    AmenityDetailService.save(result, function () {
//                        $state.go('admin.masters_amenity_detail', null, {'reload': true});
//                    });
//                });
//            };
            showMap();
        });
        