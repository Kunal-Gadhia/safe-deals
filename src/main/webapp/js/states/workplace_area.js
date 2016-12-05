angular.module("safedeals.states.workplace_area", [])
        .config(function ($stateProvider, templateRoot) {
            $stateProvider.state('admin.masters_workplace_area', {
                'url': '/workplace_area_master?offset',
                'templateUrl': templateRoot + '/masters/workplace_area/list.html',
                'controller': 'WorkplaceAreaListController'
            });
            $stateProvider.state('admin.masters_workplace_area.add', {
                'url': '/add',
                'templateUrl': templateRoot + '/masters/workplace_area/form.html',
                'controller': 'WorkplaceAreaAddController'
            });
            $stateProvider.state('admin.masters_workplace_area.edit', {
                'url': '/:workplaceAreaId/edit',
                'templateUrl': templateRoot + '/masters/workplace_area/form.html',
                'controller': 'WorkplaceAreaEditController'
            });
            $stateProvider.state('admin.masters_workplace_area.delete', {
                'url': '/:workplaceAreaId/delete',
                'templateUrl': templateRoot + '/masters/workplace_area/delete.html',
                'controller': 'WorkplaceAreaDeleteController'
            });
        })

        .controller('WorkplaceAreaListController', function (WorkplaceAreaService, $scope, $stateParams, $state, paginationLimit) {
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

            $scope.nextWorkplaceAreas = WorkplaceAreaService.query({
                'offset': $scope.nextOffset
            });
            
            $scope.workplaceAreas = WorkplaceAreaService.query({
                'offset': $scope.currentOffset
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
        .controller('WorkplaceAreaAddController', function (WorkplaceAreaService, /*WorkplaceAreaService, LocationService, CityService, */$scope, $stateParams, $state, paginationLimit) {

            $scope.editableWorkplaceArea = {};
//            
            $scope.saveworkplaceArea = function (workplace_area) {
                console.log("workplace_area", workplace_area);
                WorkplaceAreaService.save(workplace_area, function () {
                    $state.go('admin.masters_workplace_area', null, {'reload': true});
                });
            };
             $scope.$watch('editableWorkplaceArea.name', function (name) {
                console.log("Name :" + name);
                WorkplaceAreaService.findByName({'name': name}).$promise.catch(function (response) {
                    if (response.status === 500) {
                        $scope.editableWorkplaceArea.repeatName = false;
                    }
                    else if (response.status === 404) {
                        $scope.editableWorkplaceArea.repeatName = false;
                    }
                    else if (response.status === 400) {
                        $scope.editableWorkplaceArea.repeatName = false;
                    }
                }).then(function (workplaceArea) {
                    if (workplaceArea.name !== null) {
                        $scope.editableWorkplaceArea.repeatName = true;
                    }
                    ;
                });
            });
        })
        .controller('WorkplaceAreaEditController', function (WorkplaceAreaService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editableWorkplaceArea = WorkplaceAreaService.get({'id': $stateParams.workplaceAreaId});

            $scope.saveworkplaceArea = function (workplace_area) {
                workplace_area.$save(function () {
                    $state.go('admin.masters_workplace_area', null, {'reload': true});
                });
            };
        })
        .controller('WorkplaceAreaDeleteController', function (WorkplaceAreaService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editableWorkplaceArea = WorkplaceAreaService.get({'id': $stateParams.workplaceAreaId});
            console.log("are we here?");
            $scope.deleteWorkplaceArea = function (workplace_area) {
                workplace_area.$delete(function () {
                    $state.go('admin.masters_workplace_area', null, {'reload': true});
                });
            };
        });
        