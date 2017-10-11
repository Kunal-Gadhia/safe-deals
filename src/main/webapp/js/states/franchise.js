angular.module("safedeals.states.franchise", [])
        .config(function ($stateProvider, templateRoot) {
            $stateProvider.state('admin.masters_franchise', {
                'url': '/franchise_master?offset',
                'templateUrl': templateRoot + '/masters/franchise/list.html',
                'controller': 'FranchiseListController'
            });
            $stateProvider.state('admin.masters_franchise.add', {
                'url': '/add',
                'templateUrl': templateRoot + '/masters/franchise/form.html',
                'controller': 'FranchiseAddController'
            });
            $stateProvider.state('admin.masters_franchise.edit', {
                'url': '/:franchiseId/edit',
                'templateUrl': templateRoot + '/masters/franchise/form.html',
                'controller': 'FranchiseEditController'
            });
            $stateProvider.state('admin.masters_franchise.delete', {
                'url': '/:franchiseId/delete',
                'templateUrl': templateRoot + '/masters/franchise/delete.html',
                'controller': 'FranchiseDeleteController'
            });
        })
        .controller('FranchiseListController', function (FranchiseService, CityService, LocationService, $scope, $stateParams, $state, paginationLimit) {            
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

            $scope.nextFranchises = FranchiseService.query({
                'offset': $scope.nextOffset
            });

            $scope.franchises = FranchiseService.query({
                'offset': $scope.currentOffset
            }
            , function (franchises) {
                angular.forEach(franchises, function (franchise) {

                    franchise.city = CityService.get({
                        'id': franchise.cityId
                    });
                    franchise.location = LocationService.get({
                        'id': franchise.locationId
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
        })
        .controller('FranchiseAddController', function (FranchiseService, CityService, LocationService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editableFranchise = {};
            $scope.cities = CityService.findAllCities();
            $scope.locations = LocationService.findAllLocations();
            console.log("hiiiiii", $scope.locationAreas);
            $scope.setCity = function (city) {
                console.log("xyz", city);
                $scope.editableFranchise.cityId = city.id;
                $scope.editableFranchise.city = city;
                console.log("$scope.editableFranchise.city ", $scope.editableFranchise.city);
            };
            $scope.setLocation = function (location) {
                $scope.editableFranchise.locationId = location.id;
                $scope.editableFranchise.location = location;
                console.log("$scope.editableFranchise.location ", $scope.editableFranchise.location);
            };

            $scope.searchLocations = function (searchTerm) {
                console.log("Search Term :%O", searchTerm);
                console.log("City Id :%O", $scope.editableFranchise.cityId);
                if ($scope.editableFranchise.cityId === undefined) {
                    console.log("Coming to if ??");
                    return LocationService.findByNameLike({
                        'name': searchTerm
                    }).$promise;
                } else {
                    console.log("Coming to Else ??");
                    return LocationService.findByNameAndCityId({
                        'name': searchTerm,
                        'cityId': $scope.editableFranchise.cityId
                    }).$promise;
                }
            };

            $scope.searchCities = function (searchTerm) {
                console.log("Search Term :%O", searchTerm);
                return CityService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };

            $scope.saveFranchise = function (franchise) {
                console.log("franchise :" + franchise);
                FranchiseService.save(franchise, function () {
                    $state.go('admin.masters_franchise', null, {'reload': true});
                });
            };
            $scope.$watch('editableFranchise.name', function (name) {
                console.log("Franchise Name :" + name);
                FranchiseService.findByName({'name': name}).$promise.catch(function (response) {
                    if (response.status === 500) {
                        $scope.editableFranchise.repeatFranchise = false;
                    } else if (response.status === 404) {
                        $scope.editableFranchise.repeatFranchise = false;
                    } else if (response.status === 400) {
                        $scope.editableFranchise.repeatFranchise = false;
                    }
                }).then(function (franchise) {
                    if (franchise.name !== null) {
                        $scope.editableFranchise.repeatFranchise = true;
                    }
                    ;
                });
            });
        })


        .controller('FranchiseEditController', function (FranchiseService, CityService, LocationService, $scope, $stateParams, $state, paginationLimit) {
            $scope.cities = CityService.findAllCities();
            $scope.locations = LocationService.findAllLocations();

            $scope.editableFranchise = FranchiseService.get({'id': $stateParams.franchiseId}, function () {

                $scope.editableFranchise.city = CityService.get({
                    id: $scope.editableFranchise.cityId
                });
                $scope.editableFranchise.location = LocationService.get({
                    id: $scope.editableFranchise.locationId
                });
            });
            console.log("Franchise :%O", $scope.editableFranchise);

            $scope.setCity = function (city) {
                $scope.editableFranchise.cityId = city.id;
                $scope.editableFranchise.city = city;
            };

            $scope.setLocation = function (location) {
                $scope.editableFranchise.locationId = location.id;
                $scope.editableFranchise.location = location;
            };

            $scope.searchLocations = function (searchTerm) {
                console.log("Search Term :%O", searchTerm);
                console.log("City Id :%O", $scope.editableFranchise.cityId);
                if ($scope.editableFranchise.cityId === undefined) {
                    console.log("Coming to if ??");
                    return LocationService.findByNameLike({
                        'name': searchTerm
                    }).$promise;
                } else {
                    console.log("Coming to Else ??");
                    return LocationService.findByNameAndCityId({
                        'name': searchTerm,
                        'cityId': $scope.editableFranchise.cityId
                    }).$promise;
                }
            };

            $scope.searchCities = function (searchTerm) {
                console.log("Search Term :%O", searchTerm);
                return CityService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };

            $scope.saveFranchise = function (franchise) {
                franchise.$save(function () {
                    $state.go('admin.masters_franchise', null, {'reload': true});
                });
            };
        })
        .controller('FranchiseDeleteController', function (FranchiseService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editableFranchise = FranchiseService.get({'id': $stateParams.franchiseId});
            $scope.deleteFranchise = function (franchise) {
                franchise.$delete(function () {
                    $state.go('admin.masters_franchise', null, {'reload': true});
                });
            };
        }); 





