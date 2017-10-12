angular.module("safedeals.states.safedeal_zone", [])
        .config(function ($stateProvider, templateRoot) {
            $stateProvider.state('admin.masters_safedeal_zone', {
                'url': '/safedeal_zone_master?offset',
                'templateUrl': templateRoot + '/masters/safedeal_zone/list.html',
                'controller': 'SafedealZoneListController'
            });
            $stateProvider.state('admin.masters_safedeal_zone.add', {
                'url': '/add',
                'templateUrl': templateRoot + '/masters/safedeal_zone/form.html',
                'controller': 'SafedealZoneAddController'
            });
            $stateProvider.state('admin.masters_safedeal_zone.edit', {
                'url': '/:safedealZoneId/edit',
                'templateUrl': templateRoot + '/masters/safedeal_zone/form.html',
                'controller': 'SafedealZoneEditController'
            });
            $stateProvider.state('admin.masters_safedeal_zone.delete', {
                'url': '/:safedealZoneId/delete',
                'templateUrl': templateRoot + '/masters/safedeal_zone/delete.html',
                'controller': 'SafedealZoneDeleteController'
            });
        })
        .controller('SafedealZoneListController', function (SafedealZoneService, $scope, $stateParams, $state, paginationLimit) {
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

            $scope.nextSafedealZones = SafedealZoneService.query({
                'offset': $scope.nextOffset
            });

            $scope.safedealZones = SafedealZoneService.query({
                'offset': $scope.currentOffset
            }, function (s) {

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
        .controller('SafedealZoneAddController', function (SafedealZoneService, /*SalaryRangeService, LocationService, CityService, */$scope, $stateParams, $state, paginationLimit) {

            $scope.editableSafedealZone = {};
            $scope.saveSafedealZone = function (safedeal_zone) {

                SafedealZoneService.save(safedeal_zone, function () {
                    $state.go('admin.masters_safedeal_zone', null, {'reload': true});
                });
            };

            $scope.$watch('editableSafedealZone.name', function (name) {

                SafedealZoneService.findByName({'name': name}).$promise.catch(function (response) {
                    if (response.status === 500) {
                        $scope.editableSafedealZone.repeatName = false;
                    } else if (response.status === 404) {
                        $scope.editableSafedealZone.repeatName = false;
                    } else if (response.status === 400) {
                        $scope.editableSafedealZone.repeatName = false;
                    }
                }).then(function (safedealZone) {
                    if (safedealZone.name !== null) {
                        $scope.editableSafedealZone.repeatName = true;
                    }
                    ;
                });
            });
        })
        .controller('SafedealZoneEditController', function (SafedealZoneService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editableSafedealZone = SafedealZoneService.get({'id': $stateParams.safedealZoneId});
            $scope.saveSafedealZone = function (safedeal_zone) {
                safedeal_zone.$save(function () {
                    $state.go('admin.masters_safedeal_zone', null, {'reload': true});
                });
            };
        })
        .controller('SafedealZoneDeleteController', function (SafedealZoneService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editableSafedealZone = SafedealZoneService.get({'id': $stateParams.safedealZoneId});
            $scope.deleteSafedealZone = function (safedeal_zone) {
                safedeal_zone.$delete(function () {
                    $state.go('admin.masters_safedeal_zone', null, {'reload': true});
                });
            };
        });