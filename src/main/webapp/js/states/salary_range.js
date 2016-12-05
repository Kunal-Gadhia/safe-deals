angular.module("safedeals.states.salary_range", [])
        .config(function ($stateProvider, templateRoot) {
            $stateProvider.state('admin.masters_salary_range', {
                'url': '/salary_range_master?offset',
                'templateUrl': templateRoot + '/masters/salaryrange/list.html',
                'controller': 'SalaryRangeListController'
            });
            $stateProvider.state('admin.masters_salary_range.add', {
                'url': '/add',
                'templateUrl': templateRoot + '/masters/salaryrange/form.html',
                'controller': 'SalaryRangeAddController'
            });
            $stateProvider.state('admin.masters_salary_range.edit', {
                'url': '/:salaryRangeId/edit',
                        'templateUrl': templateRoot + '/masters/salaryrange/form.html',
                'controller': 'SalaryRangeEditController'
            });
            $stateProvider.state('admin.masters_salary_range.delete', {
                'url': '/:salaryRangeId/delete',
                        'templateUrl': templateRoot + '/masters/salaryrange/delete.html',
                'controller': 'SalaryRangeDeleteController'
            });
        })

        .controller('SalaryRangeListController', function (SalaryRangeService, $scope, $stateParams, $state, paginationLimit) {
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

            $scope.nextSalaryRanges = SalaryRangeService.query({
                'offset': $scope.nextOffset
            });

            $scope.salaryRanges = SalaryRangeService.query({
                'offset': $scope.currentOffset
            }, function(s){
                console.log("salary ",s);
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
        .controller('SalaryRangeAddController', function (SalaryRangeService, /*SalaryRangeService, LocationService, CityService, */$scope, $stateParams, $state, paginationLimit) {
           
            $scope.editableSalaryRange = {};
//            $scope.locations = LocationService.query();
//            $scope.salary_ranges = SalaryRangeService.query();
//            $scope.cities = CityService.query();


            $scope.saveSalaryRange = function (salary_range) {
                console.log("salary_range",salary_range);
                SalaryRangeService.save(salary_range, function () {
                    $state.go('admin.masters_salary_range', null, {'reload': true});
                });
            };
        })
        .controller('SalaryRangeEditController', function (SalaryRangeService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editableSalaryRange = SalaryRangeService.get({'id': $stateParams.salaryRangeId});

            $scope.saveSalaryRange = function (salary_range) {
                salary_range.$save(function () {
                    $state.go('admin.masters_salary_range', null, {'reload': true});
                });
            };
        })
        .controller('SalaryRangeDeleteController', function (SalaryRangeService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editableSalaryRange = SalaryRangeService.get({'id': $stateParams.salaryRangeId});
            console.log("are we here?");
            $scope.deleteSalaryRange = function (salary_range) {
                salary_range.$delete(function () {
                    $state.go('admin.masters_salary_range', null, {'reload': true});
                });
            };
        });
        