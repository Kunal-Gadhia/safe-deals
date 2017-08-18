/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

angular.module("safedeals.states.unit", [])
        .config(function ($stateProvider, templateRoot) {
            $stateProvider.state('admin.masters_unit', {
                'url': '/unit_master?offset',
                'templateUrl': templateRoot + '/masters/unit/list.html',
                'controller': 'UnitListController'
            });
            $stateProvider.state('admin.masters_unit.add', {
                'url': '/add',
                'templateUrl': templateRoot + '/masters/unit/form.html',
                'controller': 'UnitAddController'
            });
            $stateProvider.state('admin.masters_unit.edit', {
                'url': '/:unitId/edit',
                'templateUrl': templateRoot + '/masters/unit/form.html',
                'controller': 'UnitEditController'
            });
            $stateProvider.state('admin.masters_unit.delete', {
                'url': '/:unitId/delete',
                'templateUrl': templateRoot + '/masters/unit/delete.html',
                'controller': 'UnitDeleteController'
            });
        })
        .controller('UnitListController', function (UnitService, $scope, $stateParams, $state, paginationLimit) {
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

            $scope.nextUnits = UnitService.query({
                'offset': $scope.nextOffset
            });

            $scope.units = UnitService.query({
                'offset': $scope.currentOffset
            });
            console.log("$scope.units", $scope.units);

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
        .controller('UnitAddController', function (UnitService, $scope, $state) {
            $scope.editableUnit = {};
            $scope.saveUnit = function (unit) {
                console.log("unit :" + unit);
                UnitService.save(unit, function () {
                    $state.go('admin.masters_unit', null, {'reload': true});
                });
            };
            $scope.$watch('editableUnit.name', function (name) {
                console.log("Name :" + name);
                UnitService.findByName({'name': name}).$promise.catch(function (response) {
                    if (response.status === 500) {
                        $scope.editableUnit.repeatName = false;
                    } else if (response.status === 404) {
                        $scope.editableUnit.repeatName = false;
                    } else if (response.status === 400) {
                        $scope.editableUnit.repeatName = false;
                    }
                }).then(function (unit) {
                    if (unit.name !== null) {
                        $scope.editableUnit.repeatName = true;
                    }
                    ;
                });
            });
        })
        .controller('UnitEditController', function (UnitService, $scope, $stateParams, $state) {
            $scope.editableUnit = UnitService.get({'id': $stateParams.unitId});
            $scope.saveUnit = function (unit) {
                console.log("aaa", unit);
                unit.$save(function () {
                    $state.go('admin.masters_unit', null, {'reload': true});
                });
            };
        })
        .controller('UnitDeleteController', function (UnitService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editableUnit = UnitService.get({'id': $stateParams.unitId});
            $scope.deleteUnit = function (unit) {
                unit.$delete(function () {
                    $state.go('admin.masters_unit', null, {'reload': true});
                });
            };
        });







