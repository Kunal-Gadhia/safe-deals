angular.module("safedeals.states.market_price", [])
        .config(function ($stateProvider, templateRoot) {
            $stateProvider.state('admin.masters_market_price', {
                'url': '/market_price_master?offset',
                'templateUrl': templateRoot + '/masters/marketprice/list.html',
                'controller': 'MarketPriceListController'
            });
            $stateProvider.state('admin.masters_market_price.add', {
                'url': '/add',
                'templateUrl': templateRoot + '/masters/marketprice/form.html',
                'controller': 'MarketPriceAddController'
            });
            $stateProvider.state('admin.masters_market_price.edit', {
                'url': '/:marketPriceId/edit',
                'templateUrl': templateRoot + '/masters/marketprice/form.html',
                'controller': 'MarketPriceEditController'
            });
            $stateProvider.state('admin.masters_market_price.delete', {
                'url': '/:marketPriceId/delete',
                'templateUrl': templateRoot + '/masters/marketprice/delete.html',
                'controller': 'MarketPriceDeleteController'
            });
            $stateProvider.state('admin.masters_market_price.import', {
                'url': '/import',
                'templateUrl': templateRoot + '/masters/marketprice/import.html',
                'controller': 'MarketPriceImportController'
            });
        })
        .controller('MarketPriceListController', function (MarketPriceService, LocationService, CityService, $scope, $stateParams, $state, paginationLimit) {
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

            $scope.nextMarketPrices = MarketPriceService.query({
                'offset': $scope.nextOffset
            });

            MarketPriceService.query({
                'offset': $scope.currentOffset
            }, function (prices) {
                $scope.marketPrices = prices;
                angular.forEach($scope.marketPrices, function (price) {
                    price.location = LocationService.get({
                        'id': price.locationId
                    });
                    price.city = CityService.get({
                        'id': price.cityId
                    });
                });
                console.log("$scope.marketprices", $scope.marketPrices);
            });
            //console.log("$scope.marketPrices.month",$scope.marketPrices.month);

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
        .controller('MarketPriceAddController', function (MarketPriceService, LocationService, $scope, $stateParams, $state, paginationLimit, CityService) {
            $scope.editableMarketPrice = {};
            console.log("hiiiiii", $scope.locations);
            $scope.setLocation = function (location) {
                $scope.editableMarketPrice.locationId = location.id;
                $scope.editableMarketPrice.location = location;
                console.log("$scope.editableMarketPrice.location ", $scope.editableMarketPrice.location);
            };
            $scope.setCity = function (city) {
                $scope.editableMarketPrice.cityId = city.id;
                $scope.editableMarketPrice.city = city;
                console.log("$scope.editableMarketPrice.city ", $scope.editableMarketPrice.city);
            };

            $scope.searchCities = function (searchTerm) {
                console.log("Search Term :%O", searchTerm);
                return CityService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };

            $scope.searchLocations = function (searchTerm) {
                console.log("City Id :%O", $scope.editableMarketPrice.cityId);
                if ($scope.editableMarketPrice.cityId === undefined) {
                    return LocationService.findByNameLike({
                        'name': searchTerm
                    }).$promise;
                } else {
                    return LocationService.findByNameAndCityId({
                        'name': searchTerm,
                        'cityId': $scope.editableMarketPrice.cityId
                    }).$promise;
                }
            };

            $scope.saveMarketPrice = function (marketPrice) {
                console.log("marketprice :" + marketPrice);
                MarketPriceService.save(marketPrice, function () {
                    $state.go('admin.masters_market_price', null, {'reload': true});
                });
            };
          
        })

        .controller('MarketPriceEditController', function (MarketPriceService, LocationService, CityService, $scope, $stateParams, $state, paginationLimit) {
          
            $scope.editableMarketPrice = MarketPriceService.get({'id': $stateParams.marketPriceId}, function () {
                $scope.editableMarketPrice.location = LocationService.get({
                    id: $scope.editableMarketPrice.locationId
                });
                $scope.editableMarketPrice.city = CityService.get({
                    id: $scope.editableMarketPrice.cityId
                });
            });

            $scope.setLocation = function (location) {
                $scope.editableMarketPrice.locationId = location.id;
                $scope.editableMarketPrice.location = location;
            };
            $scope.setCity = function (city) {
                $scope.editableMarketPrice.cityId = city.id;
                $scope.editableMarketPrice.city = city;
            };

            $scope.searchCities = function (searchTerm) {
                console.log("Search Term :%O", searchTerm);
                return CityService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };

            $scope.searchLocations = function (searchTerm) {
                console.log("City Id :%O", $scope.editableMarketPrice.cityId);
                if ($scope.editableMarketPrice.cityId === undefined) {
                    return LocationService.findByNameLike({
                        'name': searchTerm
                    }).$promise;
                } else {
                    return LocationService.findByNameAndCityId({
                        'name': searchTerm,
                        'cityId': $scope.editableMarketPrice.cityId
                    }).$promise;
                }
            };

            $scope.saveMarketPrice = function (marketPrice) {
                marketPrice.$save(function () {
                    $state.go('admin.masters_market_price', null, {'reload': true});
                });
            };
        })
        .controller('MarketPriceDeleteController', function (MarketPriceService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editableMarketPrice = MarketPriceService.get({'id': $stateParams.marketPriceId});
            $scope.deleteMarketPrice = function (marketprice) {
                marketprice.$delete(function () {
                    $state.go('admin.masters_market_price', null, {'reload': true});
                });
            };
        })
        .controller('MarketPriceImportController', function ($scope, MarketPriceService, $state, $stateParams, RawMarketPriceService, $timeout, FileUploader, restRoot) {
            var uploader = $scope.fileUploader = new FileUploader({
                url: restRoot + '/market_price/attachment',
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
                MarketPriceService.checkExcelData(fileUploader, function (a) {


                }).$promise.then(function (value) {
                    console.log("value BOOLEAN", value.data);

                    var Val = confirm("Do you want to continue ?");
                    if (Val === true) {
                        MarketPriceService.saveExcelData(function () {
                            $state.go('admin.masters_market_price', null, {'reload': true});
                        });
                        console.log("INSIDE IFFF");

                    } else {
                        console.log("INISDE ELSE");
                        alert("Import cancelled");
                    }
                });
            };
        });


