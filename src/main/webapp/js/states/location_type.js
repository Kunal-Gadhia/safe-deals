angular.module("safedeals.states.location_type", [])
        .config(function ($stateProvider, templateRoot) {
            $stateProvider.state('admin.masters_location_type', {
                'url': '/location_type_master?offset',
                'templateUrl': templateRoot + '/masters/location_type/list.html',
                'controller': 'LocationTypeListController'
            });
            $stateProvider.state('admin.masters_location_type.add', {
                'url': '/add',
                'templateUrl': templateRoot + '/masters/location_type/form.html',
                'controller': 'LocationTypeAddController'
            });
            $stateProvider.state('admin.masters_location_type.edit', {
                'url': '/:locationTypeId/edit',
                'templateUrl': templateRoot + '/masters/location_type/form.html',
                'controller': 'LocationTypeEditController'
            });
            $stateProvider.state('admin.masters_location_type.delete', {
                'url': '/:locationTypeId/delete',
                'templateUrl': templateRoot + '/masters/location_type/delete.html',
                'controller': 'LocationTypeDeleteController'
            });
        })
        .controller('LocationTypeListController', function (LocationTypeService, $scope, $stateParams, $state, paginationLimit) {
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

            $scope.nextLocationTypes = LocationTypeService.query({
                'offset': $scope.nextOffset
            });

            $scope.locationTypes = LocationTypeService.query({
                'offset': $scope.currentOffset
            }, function (s) {
                console.log("LocationTypeService ", s);
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
        .controller('LocationTypeAddController', function (LocationTypeService, $scope, $stateParams, $state, paginationLimit) {

            $scope.editableLocationType = {};


            $scope.saveLocationType = function (location_type) {
                console.log("location_type", location_type);
                LocationTypeService.save(location_type, function () {
                    $state.go('admin.masters_location_type', null, {'reload': true});
                });
            };

            $scope.$watch('editableLocationType.name', function (name) {
                console.log("Name :" + name);
                LocationTypeService.findByName({'name': name}).$promise.catch(function (response) {
                    console.log("Response :%O", response);
                    if (response.status === 500) {
                        $scope.editableLocationType.repeatName = false;
                    } else if (response.status === 404) {
                        $scope.editableLocationType.repeatName = false;
                    } else if (response.status === 400) {
                        $scope.editableLocationType.repeatName = false;
                    }
                }).then(function (locationType) {
                    if (locationType.name !== null) {
                        $scope.editableLocationType.repeatName = true;
                    }
                    ;
                });
            });
        })
        .controller('LocationTypeEditController', function (LocationTypeService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editableLocationType = LocationTypeService.get({'id': $stateParams.locationTypeId});

            $scope.saveLocationType = function (location_type) {
                location_type.$save(function () {
                    $state.go('admin.masters_location_type', null, {'reload': true});
                });
            };
        })
        .controller('LocationTypeDeleteController', function (LocationTypeService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editableLocationType = LocationTypeService.get({'id': $stateParams.locationTypeId});
            console.log("are we here?");
            $scope.deleteLocationType = function (location_type) {
                location_type.$delete(function () {
                    $state.go('admin.masters_location_type', null, {'reload': true});
                });
            };
        });
        