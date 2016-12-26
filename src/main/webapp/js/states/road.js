angular.module("safedeals.states.road", [])
        .config(function ($stateProvider, templateRoot) {
            $stateProvider.state('admin.masters_road', {
                'url': '/road_master?offset',
                'templateUrl': templateRoot + '/masters/road/list.html',
                'controller': 'RoadListController'
            });
            $stateProvider.state('admin.masters_road.add', {
                'url': '/road',
                'templateUrl': templateRoot + '/masters/road/form.html',
                'controller': 'RoadAddController'
            });
            $stateProvider.state('admin.masters_road.edit', {
                'url': '/:roadId/edit',
                'templateUrl': templateRoot + '/masters/road/form.html',
                'controller': 'RoadEditController'
            });
            $stateProvider.state('admin.masters_road.delete', {
                'url': '/:roadId/delete',
                'templateUrl': templateRoot + '/masters/road/delete.html',
                'controller': 'RoadDeleteController'
            });
        })
        .controller('RoadListController', function (RoadService, $scope, $stateParams, $state, paginationLimit) {
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

            $scope.nextRoads = RoadService.query({
                'offset': $scope.nextOffset
            });

            $scope.roads = RoadService.query({
                'offset': $scope.currentOffset
            });
            console.log("Roads :%O", $scope.roads);

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
        .controller('RoadAddController', function (RoadService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editableRoad = {};

            $scope.saveRoad = function (road) {
                console.log("Road :%O", road);
                RoadService.save(road, function () {
                    $state.go('admin.masters_road', null, {'reload': true});
                });
            };
            $scope.$watch('editableRoad.name', function (name) {
                console.log("Name :" + name);
                RoadService.findByName({'name': name}).$promise.catch(function (response) {
                    if (response.status === 500) {
                        $scope.editableRoad.repeatName = false;
                    }
                    else if (response.status === 404) {
                        $scope.editableRoad.repeatName = false;
                    }
                    else if (response.status === 400) {
                        $scope.editableRoad.repeatName = false;
                    }
                }).then(function (road) {
                    if (road.name !== null) {
                        $scope.editableRoad.repeatName = true;
                    }
                    ;
                });
            });
        })
        .controller('RoadEditController', function (RoadService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editableRoad = RoadService.get({'id': $stateParams.roadId});

            $scope.saveRoad = function (road) {
                road.$save(function () {
                    $state.go('admin.masters_road', null, {'reload': true});
                });
            };
        })
        .controller('RoadDeleteController', function (RoadService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editableRoad = RoadService.get({'id': $stateParams.roadId});
//            console.log("are we here?");
            $scope.deleteRoad = function (road) {
                road.$delete(function () {
                    $state.go('admin.masters_road', null, {'reload': true});
                });
            };
        });