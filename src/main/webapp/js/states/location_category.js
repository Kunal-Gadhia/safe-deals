angular.module("safedeals.states.location_category", [])
        .config(function ($stateProvider, templateRoot) {
            $stateProvider.state('admin.masters_location_category', {
                'url': '/location_category_master?offset',
                'templateUrl': templateRoot + '/masters/location_category/list.html',
                'controller': 'LocationCategoryListController'
            });
            $stateProvider.state('admin.masters_location_category.add', {
                'url': '/add',
                'templateUrl': templateRoot + '/masters/location_category/form.html',
                'controller': 'LocationCategoryAddController'
            });
            $stateProvider.state('admin.masters_location_category.edit', {
                'url': '/:locationCategoryId/edit',
                'templateUrl': templateRoot + '/masters/location_category/form.html',
                'controller': 'LocationCategoryEditController'
            });
            $stateProvider.state('admin.masters_location_category.delete', {
                'url': '/:locationCategoryId/delete',
                'templateUrl': templateRoot + '/masters/location_category/delete.html',
                'controller': 'LocationCategoryDeleteController'
            });
        })

        .controller('LocationCategoryListController', function (LocationCategoryService, $scope, $stateParams, $state, paginationLimit) {
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

            $scope.nextLocationCategories = LocationCategoryService.query({
                'offset': $scope.nextOffset
            });

            $scope.locationCategories = LocationCategoryService.query({
                'offset': $scope.currentOffset
            }, function (s) {
                console.log("locationCategory ", s);
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
        .controller('LocationCategoryAddController', function (LocationCategoryService, $scope, $stateParams, $state, paginationLimit) {

            $scope.editableLocationCategory = {};

            $scope.saveLocationCategory = function (locationCategory) {
                console.log("locationCategory", locationCategory);
                LocationCategoryService.save(locationCategory, function () {
                    $state.go('admin.masters_location_category', null, {'reload': true});
                });
            };
            $scope.$watch('editableLocationCategory.name', function (name) {
                console.log("Name :" + name);
                LocationCategoryService.findByName({'name': name}).$promise.catch(function (response) {
                    if (response.status === 500) {
                        $scope.editableLocationCategory.repeatName = false;
                    }
                    else if (response.status === 404) {
                        $scope.editableLocationCategory.repeatName = false;
                    }
                    else if (response.status === 400) {
                        $scope.editableLocationCategory.repeatName = false;
                    }
                }).then(function (account) {
                    if (account.name !== null) {
                        $scope.editableLocationCategory.repeatName = true;
                    }
                    ;
                });
            });
        })
        .controller('LocationCategoryEditController', function (LocationCategoryService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editableLocationCategory = LocationCategoryService.get({'id': $stateParams.locationCategoryId});

            $scope.saveLocationCategory = function (locationCategory) {
                locationCategory.$save(function () {
                    $state.go('admin.masters_location_category', null, {'reload': true});
                });
            };
        })
        .controller('LocationCategoryDeleteController', function (LocationCategoryService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editableLocationCategory = LocationCategoryService.get({'id': $stateParams.locationCategoryId});
            console.log("are we here?");
            $scope.deleteLocationCategory = function (locationCategory) {
                locationCategory.$delete(function () {
                    $state.go('admin.masters_location_category', null, {'reload': true});
                });
            };
        });
        