angular.module("safedeals.states.business_associate", [])
        .config(function ($stateProvider, templateRoot) {
            $stateProvider.state('admin.masters_business_associate', {
                'url': '/business_associate_master?offset',
                'templateUrl': templateRoot + '/masters/businessAssociate/list.html',
                'controller': 'BusinessAssociateListController'
            });
            $stateProvider.state('admin.masters_business_associate.add', {
                'url': '/add',
                'templateUrl': templateRoot + '/masters/businessAssociate/form.html',
                'controller': 'BusinessAssociateAddController'
            });
            $stateProvider.state('admin.masters_business_associate.edit', {
                'url': '/:businessAssociateId/edit',
                'templateUrl': templateRoot + '/masters/businessAssociate/form.html',
                'controller': 'BusinessAssociateEditController'
            });
            $stateProvider.state('admin.masters_business_associate.delete', {
                'url': '/:businessAssociateId/delete',
                'templateUrl': templateRoot + '/masters/businessAssociate/delete.html',
                'controller': 'BusinessAssociateDeleteController'
            });
        })

        .controller('BusinessAssociateListController', function (BusinessAssociateService, CityService, LocationService, $scope, $stateParams, $state, paginationLimit) {
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

            $scope.nextBusinessAssociates = BusinessAssociateService.query({
                'offset': $scope.nextOffset
            });
            
            $scope.businessAssociates = BusinessAssociateService.query({
                'offset': $scope.currentOffset
            }
            , function (businessAssociates) {
                console.log("hi", businessAssociates);
                angular.forEach(businessAssociates, function (businessAssociate) {
                    console.log("abc", businessAssociates);
                    businessAssociate.city = CityService.get({
                        'id': businessAssociate.cityId
                    });
                    businessAssociate.location = LocationService.get({
                        'id': businessAssociate.locationId
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

        .controller('BusinessAssociateAddController', function (BusinessAssociateService, CityService, LocationService, $scope, $stateParams, $state, paginationLimit) {

            $scope.editableBusinessAssociate = {};
            $scope.cities = CityService.findAllCities();
            $scope.locations = LocationService.findAllLocations();
            console.log("city ", $scope.cities);

            $scope.searchCities = function (searchTerm) {
                console.log("Search Term ABC:%O", searchTerm);
                return CityService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };

            $scope.setCity = function (city) {
                console.log("xyz", city);
                $scope.editableBusinessAssociate.cityId = city.id;
                $scope.editableBusinessAssociate.city = city;
                console.log("$scope.editableBusinessAssociate.city ", $scope.editableBusinessAssociate.city);
            };

            $scope.searchLocations = function (searchTerm) {
                console.log("Search Term :%O", searchTerm);
                console.log("City Id :%O", $scope.editableBusinessAssociate.cityId);
                if ($scope.editableBusinessAssociate.cityId === undefined) {
                    console.log("Coming to if ??");
                    return LocationService.findByNameLike({
                        'name': searchTerm
                    }).$promise;
                } else {
                    console.log("Coming to Else ??");
                    return LocationService.findByNameAndCityId({
                        'name': searchTerm,
                        'cityId': $scope.editableBusinessAssociate.cityId
                    }).$promise;
                }
            };

            $scope.setLocation = function (location) {
                console.log("xyz", location);
                $scope.editableBusinessAssociate.locationId = location.id;
                $scope.editableBusinessAssociate.location = location;
                console.log("$scope.editableBusinessAssociate.location ", $scope.editableBusinessAssociate.location);
            };

            $scope.saveBusinessAssociate = function (business_associate) {
                console.log("business_associate", business_associate);
                BusinessAssociateService.save(business_associate, function () {
                    $state.go('admin.masters_business_associate', null, {'reload': true});
                });
            };
            $scope.$watch('editableBusinessAssociate.name', function (name) {
                console.log("Name :" + name);
                BusinessAssociateService.findByName({'name': name}).$promise.catch(function (response) {
                    if (response.status === 500) {
                        $scope.editableBusinessAssociate.repeatName = false;
                    }
                    else if (response.status === 404) {
                        $scope.editableBusinessAssociate.repeatName = false;
                    }
                    else if (response.status === 400) {
                        $scope.editableBusinessAssociate.repeatName = false;
                    }
                }).then(function (businessAssociate) {
                    if (businessAssociate.name !== null) {
                        $scope.editableBusinessAssociate.repeatName = true;
                    }
                    ;
                });
            });
        })
        .controller('BusinessAssociateEditController', function (BusinessAssociateService, CityService, LocationService, $scope, $stateParams, $state, paginationLimit) {
            $scope.cities = CityService.findAllCities();
            $scope.locations = LocationService.findAllLocations();

            $scope.editableBusinessAssociate = BusinessAssociateService.get({'id': $stateParams.businessAssociateId},
            function () {
                $scope.editableBusinessAssociate.city = CityService.get({
                    id: $scope.editableBusinessAssociate.cityId
                });
                console.log("$scope.editableBusinessAssociate.city", $scope.editableBusinessAssociate.city);

                $scope.editableBusinessAssociate.location = LocationService.get({
                    id: $scope.editableBusinessAssociate.locationId
                });
                console.log("$scope.editableBusinessAssociate.location", $scope.editableBusinessAssociate.location);
            });

            $scope.setCity = function (city) {
                $scope.editableBusinessAssociate.cityId = city.id;
                $scope.editableBusinessAssociate.city = city;
            };

            $scope.setLocation = function (location) {
                $scope.editableBusinessAssociate.locationId = location.id;
                $scope.editableBusinessAssociate.location = location;
            };
            
            $scope.searchLocations = function (searchTerm) {
                console.log("Search Term :%O", searchTerm);
                console.log("City Id :%O", $scope.editableBusinessAssociate.cityId);
                if ($scope.editableBusinessAssociate.cityId === undefined) {
                    console.log("Coming to if ??");
                    return LocationService.findByNameLike({
                        'name': searchTerm
                    }).$promise;
                } else {
                    console.log("Coming to Else ??");
                    return LocationService.findByNameAndCityId({
                        'name': searchTerm,
                        'cityId': $scope.editableBusinessAssociate.cityId
                    }).$promise;
                }
            };
            
            $scope.searchCities = function (searchTerm) {
                console.log("Search Term :%O", searchTerm);
                return CityService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };
            $scope.saveBusinessAssociate = function (businessAssociate) {
                businessAssociate.$save(function () {
                    $state.go('admin.masters_business_associate', null, {'reload': true});
                });
            };
        })

        .controller('BusinessAssociateDeleteController', function (BusinessAssociateService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editableBusinessAssociate = BusinessAssociateService.get({'id': $stateParams.businessAssociateId});
            console.log("are we here?");
            $scope.deleteBusinessAssociate = function (business_associate) {
                business_associate.$delete(function () {
                    $state.go('admin.masters_business_associate', null, {'reload': true});
                });
            };
        });



