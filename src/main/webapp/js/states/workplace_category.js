angular.module("safedeals.states.workplace_category", [])
        .config(function ($stateProvider, templateRoot) {
            $stateProvider.state('admin.masters_workplace_category', {
                'url': '/workplace_category_master?offset',
                'templateUrl': templateRoot + '/masters/workplace_category/list.html',
                'controller': 'WorkplaceCategoryListController'
            });
            $stateProvider.state('admin.masters_workplace_category.add', {
                'url': '/add',
                'templateUrl': templateRoot + '/masters/workplace_category/form.html',
                'controller': 'WorkplaceCategoryAddController'
            });
            $stateProvider.state('admin.masters_workplace_category.edit', {
                'url': '/:workplaceCategoryId/edit',
                'templateUrl': templateRoot + '/masters/workplace_category/form.html',
                'controller': 'WorkplaceCategoryEditController'
            });
            $stateProvider.state('admin.masters_workplace_category.delete', {
                'url': '/:workplaceCategoryId/delete',
                'templateUrl': templateRoot + '/masters/workplace_category/delete.html',
                'controller': 'WorkplaceCategoryDeleteController'
            });
        })
        .controller('WorkplaceCategoryListController', function (WorkplaceCategoryService, WorkplaceAreaService, $scope, $stateParams, $state, paginationLimit) {
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

            $scope.nextWorkplaceCategories = WorkplaceCategoryService.query({
                'offset': $scope.nextOffset
            });

            $scope.workplaceCategories = WorkplaceCategoryService.query({
                'offset': $scope.currentOffset
            },
                    function () {
                        angular.forEach($scope.workplaceCategories, function (workplaceCategory) {
                            workplaceCategory.workplaceArea = WorkplaceAreaService.get({
                                'id': workplaceCategory.workplaceAreaId
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
        .controller('WorkplaceCategoryAddController', function (WorkplaceCategoryService, WorkplaceAreaService, $scope, $state) {
            $scope.editableWorkplaceCategory = {};
//                $scope.workplaceAreas = WorkplaceAreaService.query();
            $scope.setWorkplaceArea = function (workplaceArea) {
                console.log("Set Workplace TErm :%O", workplaceArea);
                $scope.editableWorkplaceCategory.workplaceAreaId = workplaceArea.id;
                console.log("$scope.editableWorkplaceCategory.workplaceAreaName", $scope.editableWorkplaceCategory.name);
                $scope.editableWorkplaceCategory.workplaceArea = workplaceArea;
                console.log("$scope.editableWorkplaceCategory.workplaceArea ", $scope.editableWorkplaceCategory.workplaceArea);
            };
            $scope.searchWorkplaceArea = function (searchTerm) {
                return WorkplaceAreaService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };
            $scope.saveWorkplaceCategory = function (workplaceCategory) {
                console.log("workplaceCategory", workplaceCategory);
                WorkplaceCategoryService.save(workplaceCategory, function () {
                    $state.go('admin.masters_workplace_category', null, {'reload': true});
                });
            };
            $scope.$watch('editableWorkplaceCategory.name', function (name) {
                console.log("Name :" + name);
                WorkplaceCategoryService.findByName({'name': name}).$promise.catch(function (response) {
                    if (response.status === 500) {
                        $scope.editableWorkplaceCategory.repeatName = false;
                    } else if (response.status === 404) {
                        $scope.editableWorkplaceCategory.repeatName = false;
                    } else if (response.status === 400) {
                        $scope.editableWorkplaceCategory.repeatName = false;
                    }
                }).then(function (workplaceCategory) {
                    if (workplaceCategory.name !== null) {
                        $scope.editableWorkplaceCategory.repeatName = true;
                    }
                    ;
                });
            });
        })
        .controller('WorkplaceCategoryEditController', function (WorkplaceCategoryService, WorkplaceAreaService, $scope, $stateParams, $state, paginationLimit) {
            $scope.workplaceAreas = WorkplaceAreaService.query();
            $scope.editableWorkplaceCategory = WorkplaceCategoryService.get({
                'id': $stateParams.workplaceCategoryId
            }, function () {
                $scope.editableWorkplaceCategory.workplaceArea = WorkplaceAreaService.get({
                    id: $scope.editableWorkplaceCategory.workplaceAreaId
                });
            });
            $scope.setWorkplaceArea = function (workplaceArea) {
                $scope.editableWorkplaceCategory.workplaceAreaId = workplaceArea.id;
                $scope.editableWorkplaceCategory.workplaceArea = workplaceArea;
            };
            $scope.searchWorkplaceArea = function (searchTerm) {
                return WorkplaceAreaService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };
            $scope.saveWorkplaceCategory = function (workplaceCategory) {
                workplaceCategory.$save(function () {
                    $state.go('admin.masters_workplace_category', null, {'reload': true});
                });
            };
        })
        .controller('WorkplaceCategoryDeleteController', function (WorkplaceCategoryService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editableWorkplaceCategory = WorkplaceCategoryService.get({'id': $stateParams.workplaceCategoryId});
            $scope.deleteWorkplaceCategory = function (workplaceCategory) {
                workplaceCategory.$delete(function () {
                    $state.go('admin.masters_workplace_category', null, {'reload': true});
                });
            };
        });