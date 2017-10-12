angular.module("safedeals.states.society_maintenance", [])
        .config(function ($stateProvider, templateRoot) {
            $stateProvider.state('admin.masters_society_maintenance', {
                'url': '/society_maintenance_master?offset',
                'templateUrl': templateRoot + '/masters/societymaintenance/list.html',
                'controller': 'SocietyMaintenanceListController'
            });
            $stateProvider.state('admin.masters_society_maintenance.add', {
                'url': '/add',
                'templateUrl': templateRoot + '/masters/societymaintenance/form.html',
                'controller': 'SocietyMaintenanceAddController'
            });
            $stateProvider.state('admin.masters_society_maintenance.edit', {
                'url': '/:societyMaintenanceId/edit',
                'templateUrl': templateRoot + '/masters/societymaintenance/form.html',
                'controller': 'SocietyMaintenanceEditController'
            });
            $stateProvider.state('admin.masters_society_maintenance.delete', {
                'url': '/:societyMaintenanceId/delete',
                'templateUrl': templateRoot + '/masters/societymaintenance/delete.html',
                'controller': 'SocietyMaintenanceDeleteController'
            });
        })
        .controller('SocietyMaintenanceListController', function (SocietyMaintenanceService, $scope, $stateParams, $state, paginationLimit) {
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

            $scope.nextSocietyMaintenances = SocietyMaintenanceService.query({
                'offset': $scope.nextOffset
            });

            $scope.societyMaintenances = SocietyMaintenanceService.query({
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
        .controller('SocietyMaintenanceAddController', function (SocietyMaintenanceService, $scope, $stateParams, $state, paginationLimit) {

            $scope.editableSocietyMaintenance = {};
            $scope.saveSocietyMaintenance = function (society_maintenance) {

                SocietyMaintenanceService.save(society_maintenance, function () {
                    $state.go('admin.masters_society_maintenance', null, {'reload': true});
                });
            };

        })
        .controller('SocietyMaintenanceEditController', function (SocietyMaintenanceService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editableSocietyMaintenance = SocietyMaintenanceService.get({'id': $stateParams.societyMaintenanceId});

            $scope.saveSocietyMaintenance = function (society_maintenance) {
                society_maintenance.$save(function () {
                    $state.go('admin.masters_society_maintenance', null, {'reload': true});
                });
            };
        })
        .controller('SocietyMaintenanceDeleteController', function (SocietyMaintenanceService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editableSocietyMaintenance = SocietyMaintenanceService.get({'id': $stateParams.societyMaintenanceId});

            $scope.deleteSocietyMaintenance = function (society_maintenance) {
                society_maintenance.$delete(function () {
                    $state.go('admin.masters_society_maintenance', null, {'reload': true});
                });
            };
        });
        