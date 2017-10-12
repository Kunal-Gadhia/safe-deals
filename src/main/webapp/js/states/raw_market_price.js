/* global CityService, SafedealZoneService, LocationTypeService */

angular.module("safedeals.states.raw_market_price", [])
        .config(function ($stateProvider, templateRoot) {
            $stateProvider.state('admin.masters_raw_market_price', {
                'url': '/raw_market_price_master?offset',
                'templateUrl': templateRoot + '/masters/rawmarketprice/list.html',
                'controller': 'RawMarketPriceListController'
            });
            $stateProvider.state('admin.masters_raw_market_price.add', {
                'url': '/add',
                'templateUrl': templateRoot + '/masters/rawmarketprice/form.html',
                'controller': 'RawMarketPriceAddController'
            });
            $stateProvider.state('admin.masters_raw_market_price.edit', {
                'url': '/:rawMarketPriceId/edit',
                'templateUrl': templateRoot + '/masters/rawmarketprice/form.html',
                'controller': 'RawMarketPriceEditController'
            });
            $stateProvider.state('admin.masters_raw_market_price.delete', {
                'url': '/:rawMarketPriceId/delete',
                'templateUrl': templateRoot + '/masters/rawmarketprice/delete.html',
                'controller': 'RawMarketPriceDeleteController'
            });
            $stateProvider.state('admin.masters_raw_market_price.import', {
                'url': '/attachment',
                'templateUrl': templateRoot + '/masters/rawmarketprice/import.html',
                'controller': 'RawMarketPriceImportController'
            });
        })
        .controller('RawMarketPriceListController', function (LocationCategoryService, RawMarketPriceService, CityService, LocationTypeService, SafedealZoneService, /*BankService, LocationService, CityService,*/ $scope, $stateParams, $state, paginationLimit) {
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

            $scope.nextRawMarketPrices = RawMarketPriceService.query({
                'offset': $scope.nextOffset
            });

            $scope.rawMarketPrices = RawMarketPriceService.query({
                'offset': $scope.currentOffset
            }, function () {
                angular.forEach($scope.rawMarketPrices, function (rawMarketPrice) {

                    rawMarketPrice.safedealZone = SafedealZoneService.get({
                        'id': rawMarketPrice.safedealZoneId
                    });
                    rawMarketPrice.locationType = LocationTypeService.get({
                        'id': rawMarketPrice.locationTypeId
                    });
                    rawMarketPrice.locationCategoryObjects = [];
                    angular.forEach(rawMarketPrice.locationCategories, function (locationCategoryId) {
                        rawMarketPrice.locationCategoryObjects.push(LocationCategoryService.get({
                            'id': locationCategoryId
                        }));
                    });
                    rawMarketPrice.city = CityService.get({
                        'id': rawMarketPrice.cityId
                    });
                });
            });


            var locations = [];
            var rrLocations = [];
            var latLongLocations = [];

            $scope.rawMarketPricesLocations = RawMarketPriceService.getAll();

            $scope.findlocation = function () {
                var geocoder = new google.maps.Geocoder();
                angular.forEach($scope.rawMarketPricesLocations, function (rrLocation) {
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

                            request.location.lat = results[0].geometry.location.lat();
                            request.location.lng = results[0].geometry.location.lng();
                            var searchLoc = new google.maps.LatLng(request.location.lat, request.location.lng);


                        } else {
                            request.location.lat = 0;
                            request.location.lng = 0;
                        }
                        latLongLocations.push(searchLoc);
                    })

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
        .controller('RawMarketPriceAddController', function (SafedealZoneService, LocationTypeService, RawMarketPriceService, LocationService, LocationCategoryService, CityService, $scope, $stateParams, $state, paginationLimit) {
            $scope.cities = CityService.findAllCities();
            $scope.safedealZones = SafedealZoneService.query();
            $scope.locationTypes = LocationTypeService.query();
            $scope.editableRawMarketPrice = {};
            $scope.setCity = function (city) {

                $scope.editableRawMarketPrice.cityId = city.id;
                $scope.editableRawMarketPrice.city = city;

            };
            $scope.setSafedealZone = function (safedealZone) {

                $scope.editableRawMarketPrice.safedealZoneId = safedealZone.id;
                $scope.editableRawMarketPrice.safedealZone = safedealZone;

            };
            $scope.setLocationType = function (locationType) {

                $scope.editableRawMarketPrice.locationTypeId = locationType.id;
                $scope.editableRawMarketPrice.locationType = locationType;

            };
            $scope.setLocationCategories = function (locationCategories) {
                $scope.editableRawMarketPrice.locationCategoriesObjects = [];
                $scope.editableRawMarketPrice.locationCategories = [];
                angular.forEach(locationCategories, function (locationCategory) {
                    $scope.editableRawMarketPrice.locationCategories.push(locationCategory.id);
                    $scope.editableRawMarketPrice.locationCategoriesObjects.push(locationCategory);
                });
            };
            $scope.setLocation = function (location) {
                $scope.editableRawMarketPrice.locationId = location.id;
                $scope.editableRawMarketPrice.location = location;
            };
            $scope.searchLocations = function (searchTerm) {

                return LocationService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };
            $scope.saveRawMarketPrice = function (rawMarketPrice) {

                RawMarketPriceService.save(rawMarketPrice, function () {
                    $state.go('admin.masters_raw_market_price', null, {'reload': true});
                });
            };
        })
        .controller('RawMarketPriceEditController', function (RawMarketPriceService, SafedealZoneService, LocationCategoryService, LocationTypeService, LocationService, CityService, $scope, $stateParams, $state, paginationLimit) {

            $scope.cities = CityService.findAllCities();
            $scope.safedealZones = SafedealZoneService.query();
            $scope.locationTypes = LocationTypeService.query();

            $scope.editableRawMarketPrice = RawMarketPriceService.get({'id': $stateParams.rawMarketPriceId}, function () {
                $scope.locationCategories = LocationCategoryService.query();
                $scope.editableRawMarketPrice.city = CityService.get({
                    id: $scope.editableRawMarketPrice.cityId
                });
                $scope.editableRawMarketPrice.safedealZone = SafedealZoneService.get({
                    id: $scope.editableRawMarketPrice.safedealZoneId
                });
                $scope.editableRawMarketPrice.locationType = LocationTypeService.get({
                    id: $scope.editableRawMarketPrice.locationTypeId
                });
            });

            $scope.preSelected = [];

            $scope.editableRawMarketPrice.$promise.then(function (data) {

                $scope.editableRawMarketPrice.locationCategoriesObjects = [];

                angular.forEach(data.locationCategories, function (locationId) {

                    $scope.editableRawMarketPrice.locationCategoriesObjects.push(LocationCategoryService.get({
                        'id': locationId
                    }));
                });
                $scope.preSelected = $scope.editableRawMarketPrice.locationCategoriesObjects;

            });

            $scope.setLocationCategories = function (locationCategories) {
                $scope.editableRawMarketPrice.locationCategoriesObjects = [];
                $scope.editableRawMarketPrice.locationCategories = [];
                angular.forEach(locationCategories, function (locationCategory) {
                    $scope.editableRawMarketPrice.locationCategories.push(locationCategory.id);
                });

            };
            $scope.setCity = function (city) {

                $scope.editableRawMarketPrice.cityId = city.id;
                $scope.editableRawMarketPrice.city = city;

            };
            $scope.setSafedealZone = function (safedealZone) {

                $scope.editableRawMarketPrice.safedealZoneId = safedealZone.id;
                $scope.editableRawMarketPrice.safedealZone = safedealZone;

            };
            $scope.setLocationType = function (locationType) {

                $scope.editableRawMarketPrice.locationTypeId = locationType.id;
                $scope.editableRawMarketPrice.locationType = locationType;

            };
            $scope.setLocation = function (location) {
                $scope.editableBranch.locationId = location.id;
                $scope.editableBranch.location = location;
            };
            $scope.searchLocations = function (searchTerm) {

                return LocationService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };

            $scope.saveRawMarketPrice = function (rawMarketPrice) {

                rawMarketPrice.$save(function () {
                    $state.go('admin.masters_raw_market_price', {'rawMarketPriceId': $stateParams.rawMarketPriceId}, {'reload': true});
                });
            };
        })
        .controller('RawMarketPriceDeleteController', function (RawMarketPriceService, CityService, $scope, $stateParams, $state) {
            $scope.editableRawMarketPrice = RawMarketPriceService.get({'id': $stateParams.rawMarketPriceId}, function () {
                $scope.editableRawMarketPrice.city = CityService.get({
                    id: $scope.editableRawMarketPrice.cityId
                });
            });

            $scope.deleteRawMarketPrice = function (rawMarketPrice) {
                rawMarketPrice.$delete(function () {
                    $state.go('admin.masters_raw_market_price', null, {'reload': true});
                });
            };
        })
        .controller('RawMarketPriceImportController', function ($scope, $state, $stateParams, RawMarketPriceService, $timeout, FileUploader, restRoot) {

            var uploader = $scope.fileUploader = new FileUploader({
                url: restRoot + '/rawmarketprice/attachment',
                autoUpload: true,
                alias: 'attachment'
            });

            uploader.onBeforeUploadItem = function (item) {
                $scope.uploadInProgress = true;

            };
            uploader.onErrorItem = function (fileItem, response, status, headers) {
                $scope.uploadFailed = true;
                $timeout(function () {
                    $scope.uploadFailed = false;
                }, 2000);

            };
            uploader.onCompleteItem = function (fileItem, response, status, headers) {
                $scope.uploadInProgress = true;
                $timeout(function () {
                    $scope.uploadInProgress = false;
                }, 2000);
            };
            $scope.saveExcelAttachment = function (fileUploader) {
                RawMarketPriceService.checkExcelData(fileUploader, function (a) {


                }).$promise.then(function (value) {

                    var Val = confirm("Do you want to continue ?");
                    if (Val === true) {
                        RawMarketPriceService.saveExcelData(function () {
                            $state.go('admin.masters_raw_market_price', null, {'reload': true});
                        });

                    } else {

                        alert("Import cancelled");
                    }
                });
            };
            $scope.rawMarketPriceExport = function () {

                RawMarketPriceService.exportAllRawMarketPrice(function (rm) {

                    alert("Downloaded successfully");
                });

            };
        });
			