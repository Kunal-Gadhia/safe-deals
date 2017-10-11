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
            $stateProvider.state('admin.masters_amenity_detail.import_excel', {
                'url': '/import/excel',
                'templateUrl': templateRoot + '/masters/amenitydetail/import_excel.html',
                'controller': 'AmenityDetailExcelImportController'
            });
        })
        .controller('AmenityDetailListController', function (AmenityDetailService, WorkplaceCategoryService, LocationService, AmenityService, CityService, $scope, $stateParams, $state, paginationLimit) {

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
        .controller('AmenityDetailEditController', function (AmenityDetailService, AmenityService, LocationService, WorkplaceCategoryService, CityService, $scope, $stateParams, $state, paginationLimit) {
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
        })
        .controller('AmenityDetailDeleteController', function (AmenityDetailService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editableAmenityDetail = AmenityDetailService.get({'id': $stateParams.amenity_detailId});
            console.log("are we here?", $scope.editableAmenityDetail.name);
            $scope.deleteAmenityDetail = function (amenitydetail) {
                amenitydetail.$delete(function () {
                    $state.go('admin.masters_amenity_detail', null, {'reload': true});
                });
            };
        })
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

                if (status === google.maps.places.PlacesServiceStatus.OK) {
                    for (var i = 0; i < results.length; i++) {
                        createMarker(results[i]);
                    }
                }
                angular.forEach($scope.results, function (result) {

                    $scope.amenityDetailResult = {};
                    $scope.amenityDetailResult.name = result.name;
                    $scope.amenityDetailResult.amenityId = $scope.amenityId;
                    $scope.amenityDetailResult.address = result.formatted_address;

                    $scope.amenityDetailResult.latitude = result.geometry.location.lat();
                    $scope.amenityDetailResult.longitude = result.geometry.location.lng();

                    AmenityDetailService.save($scope.amenityDetailResult, function () {
                        $state.go('admin.masters_amenity_detail', null, {'reload': true});
                    });
                });
            }
            function createMarker(place) {

                var placeLoc = place.geometry.location;
                var marker = new google.maps.Marker({
                    map: map,
                    position: placeLoc,
                    icon: place.icon
                });
                google.maps.event.addListener(marker, 'click', function () {
                    infowindow.setContent(place.name);
                    infowindow.open(map, this);
                });
            }
            ;

            showMap();
        })
        .controller('AmenityDetailExcelImportController', function (FileUploader, $timeout, restRoot, AmenityDetailService, $scope, $stateParams, $state, paginationLimit) {
            var uploader = $scope.fileUploader = new FileUploader({
                url: restRoot + '/location/attachment',
                autoUpload: true,
                alias: 'attachment'
            });

            $scope.amenityDetailExport = function () {
                console.log("amenityDetailExport");
                AmenityDetailService.exportAllLocations(function (a) {
                    alert("Downloaded successfully");
                });
            };

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
            };
            uploader.onCompleteItem = function (fileItem, response, status, headers) {
                $scope.uploadInProgress = true;
                $timeout(function () {
                    $scope.uploadInProgress = false;
                }, 2000);

                console.log("upload completion", fileItem);

            };
            $scope.saveExcelAttachment = function (fileUploader) {
                AmenityDetailService.saveExcelData(fileUploader, function () {
                    $state.go('admin.masters_amenity_detail', null, {'reload': true});
                });
            };
        });
        