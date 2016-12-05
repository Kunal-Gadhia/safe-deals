angular.module("safedeals.states.raw_ready_reckoner", [])
        .config(function ($stateProvider, templateRoot) {
            $stateProvider.state('admin.masters_raw_ready_reckoner', {
                'url': '/raw_ready_reckoner_master?offset',
                'templateUrl': templateRoot + '/masters/rawreadyreckoner/list.html',
                'controller': 'RawReadyReckonerListController'
            });
            $stateProvider.state('admin.masters_raw_ready_reckoner.add', {
                'url': '/add',
                'templateUrl': templateRoot + '/masters/rawreadyreckoner/form.html',
                'controller': 'RawReadyReckonerAddController'
            });
            $stateProvider.state('admin.masters_raw_ready_reckoner.edit', {
                'url': '/:rawReadyReckonerId/edit',
                'templateUrl': templateRoot + '/masters/rawreadyreckoner/form.html',
                'controller': 'RawReadyReckonerEditController'
            });
            $stateProvider.state('admin.masters_raw_ready_reckoner.delete', {
                'url': '/:rawReadyReckonerId/delete',
                'templateUrl': templateRoot + '/masters/rawreadyreckoner/delete.html',
                'controller': 'RawReadyReckonerDeleteController'
            });
            $stateProvider.state('admin.masters_raw_ready_reckoner.browse', {
                'url': '/attachment',
                'templateUrl': templateRoot + '/masters/rawreadyreckoner/browse.html',
                'controller': 'RawReadyReckonerBrowseController'
            });
        })
        .controller('RawReadyReckonerListController', function (LocationCategoryService, RawReadyReckonerService, CityService, LocationTypeService, SafedealZoneService, /*BankService, LocationService, CityService,*/ $scope, $stateParams, $state, paginationLimit) {
            if (
                    $stateParams.offset === undefined ||
                    isNaN($stateParams.offset) ||
                    new Number($stateParams.offset) < 0)
            {
                $scope.currentOffset = 0;
            } else {
                $scope.currentOffset = new Number($stateParams.offset);
            }
            $scope.rawReadyReckoners = RawReadyReckonerService.query({
                'offset': $scope.currentOffset
            }, function () {
                angular.forEach($scope.rawReadyReckoners, function (rawReadyReckoner) {
                    console.log("rawReadyReckoner ", rawReadyReckoner);
                    rawReadyReckoner.safedealZone = SafedealZoneService.get({
                        'id': rawReadyReckoner.safedealZoneId
                    });
                    rawReadyReckoner.locationType = LocationTypeService.get({
                        'id': rawReadyReckoner.locationTypeId
                    });
                    rawReadyReckoner.locationCategoryObjects = [];
                    angular.forEach(rawReadyReckoner.locationCategories, function (locationCategoryId) {
                        rawReadyReckoner.locationCategoryObjects.push(LocationCategoryService.get({
                            'id': locationCategoryId
                        }));
                    });
                    rawReadyReckoner.city = CityService.get({
                        'id': rawReadyReckoner.cityId
                    });
                });
            });


            var locations = [];
            var rrLocations = [];
            var latLongLocations = [];

            $scope.rawReadyReckonersLocations = RawReadyReckonerService.getAll();
//            var geocoder = new google.maps.Geocoder();
            $scope.findlocation = function () {
                var geocoder = new google.maps.Geocoder();
                angular.forEach($scope.rawReadyReckonersLocations, function (rrLocation) {
                    locations.push(rrLocation.location);
                });
                angular.forEach(locations, function (location) {
                    rrLocations.push({'address': location});
                });
                angular.forEach(rrLocations, function (rrLocation) {
                    geocoder.geocode(rrLocation, function (results, status) {
                        var request = {};
                        request.location = {};
                        if (status == google.maps.GeocoderStatus.OK) {
//                            alert("location : " + results[0].geometry.location.lat() + " " + results[0].geometry.location.lng());
                            request.location.lat = results[0].geometry.location.lat();
                            request.location.lng = results[0].geometry.location.lng();
                            var searchLoc = new google.maps.LatLng(request.location.lat, request.location.lng);
//                            service.nearbySearch();

                        } else {
                            request.location.lat = 0;
                            request.location.lng = 0;
                        }
                        latLongLocations.push(searchLoc);
                    });

                });
            };
//            

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
        .controller('RawReadyReckonerAddController', function (RawReadyReckonerService, LocationService, /*BankService,*/ CityService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editableRawReadyReckoner = {};

            $scope.setLocation = function (location) {
                $scope.editableBranch.locationId = location.id;
                $scope.editableBranch.location = location;
            };

            $scope.setCity = function (city) {
                $scope.editableBranch.cityId = city.id;
                $scope.editableBranch.city = city;
            };

            $scope.searchLocations = function (searchTerm) {
                console.log("Search Term :%O", searchTerm);
                return LocationService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };

            $scope.searchCities = function (searchTerm) {
                console.log("Search Term :%O", searchTerm);
                return CityService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };
            $scope.saveRawReadyReckoner = function (rawReadyReckoner) {
                RawReadyReckonerService.save(rawReadyReckoner, function () {
                    $state.go('admin.masters_raw_ready_reckoner', null, {'reload': true});
                });
            };
        })
        .controller('RawReadyReckonerEditController', function (RawReadyReckonerService, BankService, LocationService, CityService, $scope, $stateParams, $state, paginationLimit) {
            console.log("rawReadyReckonerId", $stateParams.rawReadyReckonerId);
            $scope.editableRawReadyReckoner = RawReadyReckonerService.get({'id': $stateParams.rawReadyReckonerId});

            $scope.saveRawReadyReckoner = function (rawReadyReckoner) {
                rawReadyReckoner.$save(function () {
                    $state.go('^', {'rawReadyReckonerId': $stateParams.rawReadyReckonerId}, {'reload': true});
                });
            };
        })
        .controller('RawReadyReckonerDeleteController', function (RawReadyReckonerService, LocationService, $scope, $stateParams, $state) {
            $scope.editableRawReadyReckoner = RawReadyReckonerService.get({'id': $stateParams.rawReadyReckonerId});
            $scope.deleteRawReadyReckoner = function (rawReadyReckoner) {
                rawReadyReckoner.$delete(function () {
                    $state.go('^', null, {'reload': true});
                });
            };
        })
        .controller('RawReadyReckonerBrowseController', function ($scope, $state, $stateParams, RawReadyReckonerService, $timeout, FileUploader, restRoot) {
            console.log("showDetails", $scope.showDetails);
            var uploader = $scope.fileUploader = new FileUploader({
                url: restRoot + '/rawreadyreckoner/attachment',
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
//                $scope.refreshRawReadyReckoner();
            };
            uploader.onCompleteItem = function (fileItem, response, status, headers) {
                $scope.uploadInProgress = true;
                $timeout(function () {
                    $scope.uploadInProgress = false;
                }, 2000);
//                $scope.refreshRawReadyReckoner();
                console.log("upload completion", fileItem);

            };
            $scope.saveExcelAttachment = function (fileUploader) {
                RawReadyReckonerService.saveExcelData(fileUploader, function () {
                    $state.go('admin.masters_raw_ready_reckoner', null, {'reload': true});

                });
            };
        });
			