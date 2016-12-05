angular.module("safedeals.states.workplace_category_detail", [])
        .config(function ($stateProvider, templateRoot) {
            $stateProvider.state('admin.masters_workplace_category_detail', {
                'url': '/workplace_category_detail_master?offset',
                'templateUrl': templateRoot + '/masters/workplacecategorydetail/list.html',
                'controller': 'WorkplaceCategoryDetailListController'
            });
            $stateProvider.state('admin.masters_workplace_category_detail.add', {
                'url': '/add',
                'templateUrl': templateRoot + '/masters/workplacecategorydetail/form.html',
                'controller': 'WorkplaceCategoryDetailAddController'
            });
            $stateProvider.state('admin.masters_workplace_category_detail.edit', {
                'url': '/:workplaceCategoryDetailId/edit',
                'templateUrl': templateRoot + '/masters/workplacecategorydetail/form.html',
                'controller': 'WorkplaceCategoryDetailEditController'
            });
            $stateProvider.state('admin.masters_workplace_category_detail.delete', {
                'url': '/:workplaceCategoryDetailId/delete',
                'templateUrl': templateRoot + '/masters/workplacecategorydetail/delete.html',
                'controller': 'WorkplaceCategoryDetailDeleteController'
            });
        })

        .controller('WorkplaceCategoryDetailListController', function (WorkplaceCategoryDetailService, SalaryRangeService, WorkplaceCategoryService, $scope, $stateParams, $state, paginationLimit) {
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

            $scope.nextWorkplaceCategoryDetails = WorkplaceCategoryDetailService.query({
                'offset': $scope.nextOffset
            });

            $scope.workplaceCategoryDetails = WorkplaceCategoryDetailService.query({
                'offset': $scope.currentOffset
            }
            , function () {
                angular.forEach($scope.workplaceCategoryDetails, function (workplaceCategoryDetail) {
                    workplaceCategoryDetail.workplaceCategory = WorkplaceCategoryService.get({
                        'id': workplaceCategoryDetail.workplaceCategoryId
                    });
                    workplaceCategoryDetail.salaryRange = SalaryRangeService.get({
                        'id': workplaceCategoryDetail.salaryRangeId
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
        .controller('WorkplaceCategoryDetailAddController', function (WorkplaceCategoryDetailService, WorkplaceCategoryService, SalaryRangeService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editableWorkplaceCategoryDetail = {};
//            $scope.workplaceAreas = WorkplaceAreaService.query();
            $scope.setWorkplaceCategory = function (workplaceCategory) {
                console.log("Set Workplace TErm :%O", workplaceCategory);
                $scope.editableWorkplaceCategoryDetail.workplaceCategoryId = workplaceCategory.id;
                $scope.editableWorkplaceCategoryDetail.workplaceCategory = workplaceCategory;
                console.log("$scope.editableWorkplaceCategoryDetail.workplaceCategory ", $scope.editableWorkplaceCategoryDetail.workplaceCategory);
            };
            $scope.setSalaryRange = function (salaryRange) {
                console.log("Set Workplace TErm :%O", salaryRange);
                $scope.editableWorkplaceCategoryDetail.salaryRangeId = salaryRange.id;
                console.log("$scope.editableWorkplaceCategoryDetail.salaryRangeDescription ", $scope.editableWorkplaceCategoryDetail.description);
                $scope.editableWorkplaceCategoryDetail.salaryRange = salaryRange;
                console.log("$scope.editableWorkplaceCategoryDetail.salaryRange ", $scope.editableWorkplaceCategoryDetail.salaryRange);
            };
            $scope.searchWorkplaceCategory = function (searchTerm) {
                console.log("searchTerm", searchTerm);
                return WorkplaceCategoryService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };
            $scope.searchSalaryRange = function (searchTerm) {
                return SalaryRangeService.findByDescriptionLike({
                    'description': searchTerm
                }).$promise;
            };

            $scope.saveWorkplaceCategoryDetail = function (workplaceCategoryDetail) {
                WorkplaceCategoryDetailService.save(workplaceCategoryDetail, function () {
                    $state.go('admin.masters_workplace_category_detail', null, {'reload': true});
                });
            };
        })
        .controller('WorkplaceCategoryDetailEditController', function (WorkplaceCategoryDetailService, WorkplaceCategoryService, SalaryRangeService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editableWorkplaceCategoryDetail = WorkplaceCategoryDetailService.get({
                'id': $stateParams.workplaceCategoryDetailId
            }, function () {
                $scope.editableWorkplaceCategoryDetail.workplaceCategory = WorkplaceCategoryService.get({
                    'id': $scope.editableWorkplaceCategoryDetail.workplaceCategoryId
                });
                $scope.editableWorkplaceCategoryDetail.salaryRange = SalaryRangeService.get({
                    'id': $scope.editableWorkplaceCategoryDetail.salaryRangeId
                });
            });
            $scope.setWorkplaceCategory = function (workplaceCategory) {
                console.log("Set Workplace TErm :%O", workplaceCategory);
                $scope.editableWorkplaceCategoryDetail.workplaceCategoryId = workplaceCategory.id;
                $scope.editableWorkplaceCategoryDetail.workplaceCategory = workplaceCategory;
                console.log("$scope.editableWorkplaceCategoryDetail.workplaceCategory ", $scope.editableWorkplaceCategoryDetail.workplaceCategory);
            };
            $scope.setSalaryRange = function (salaryRange) {
                console.log("Set Workplace TErm :%O", salaryRange);
                $scope.editableWorkplaceCategoryDetail.salaryRangeId = salaryRange.id;
                $scope.editableWorkplaceCategoryDetail.salaryRangeDescription = salaryRange.description;
                $scope.editableWorkplaceCategoryDetail.salaryRange = salaryRange;
                console.log("$scope.editableWorkplaceCategoryDetail.salaryRange ", $scope.editableWorkplaceCategoryDetail.salaryRange);
            };
            $scope.searchWorkplaceCategory = function (searchTerm) {
                console.log("searchTerm", searchTerm);
                return WorkplaceCategoryService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };
            $scope.searchSalaryRange = function (searchTerm) {
                return SalaryRangeService.findByDescriptionLike({
                    'description': searchTerm
                }).$promise;
            };
            $scope.saveWorkplaceCategoryDetail = function (workplaceCategoryDetail) {
                workplaceCategoryDetail.$save(function () {
                    $state.go('admin.masters_workplace_category_detail', null, {'reload': true});
                    console.log("workplaceCategoryDetail", workplaceCategoryDetail);
                });
            };
        })
        .controller('WorkplaceCategoryDetailDeleteController', function (WorkplaceCategoryDetailService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editableWorkplaceCategoryDetail = WorkplaceCategoryDetailService.get({'id': $stateParams.workplaceCategoryDetailId});
            $scope.deleteWorkplaceCategoryDetail = function (workplaceCategoryDetail) {
                workplaceCategoryDetail.$delete(function () {
                    $state.go('admin.masters_workplace_category_detail', null, {'reload': true});
                });
            };
        });