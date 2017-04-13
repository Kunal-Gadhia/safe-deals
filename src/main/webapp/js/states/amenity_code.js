angular.module("safedeals.states.amenity_code", [])
        .config(function ($stateProvider, templateRoot) {
            $stateProvider.state('admin.amenity_code', {
                'url': '/amenity_code_master?offset',
                'templateUrl': templateRoot + '/masters/amenitycode/list.html',
                'controller': 'AmenityCodeListController'
            });
            $stateProvider.state('admin.amenity_code.add', {
                'url': '/add',
                'templateUrl': templateRoot + '/masters/amenitycode/form.html',
                'controller': 'AmenityCodeAddController'
            });
            $stateProvider.state('admin.amenity_code.edit', {
                'url': '/:amenityCodeId/edit',
                'templateUrl': templateRoot + '/masters/amenitycode/form.html',
                'controller': 'AmenityCodeEditController'
            });
            $stateProvider.state('admin.amenity_code.delete', {
                'url': '/:amenityCodeId/delete',
                'templateUrl': templateRoot + '/masters/amenitycode/delete.html',
                'controller': 'AmenityCodeDeleteController'
            });
        })
        .controller('AmenityCodeListController', function (AmenityCodeService, $scope, $stateParams, $state, paginationLimit) {
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

            $scope.nextAmenityCodes = AmenityCodeService.query({
                'offset': $scope.nextOffset
            });
//
            $scope.amenityCodes = AmenityCodeService.query({
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
        .controller('AmenityCodeAddController', function (AmenityCodeService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editableAmenityCode = {};
            $scope.saveAmenityCode = function (amenityCode) {
                AmenityCodeService.save(amenityCode, function () {
                    $state.go('admin.amenity_code', null, {'reload': true});
                });
            };
            $scope.$watch('editableAmenityCode.name', function (name) {
                AmenityCodeService.findByName({'name': name}).$promise.catch(function (response) {
                    console.log("Response :%O", response);
                    if (response.status === 500) {
                        $scope.editableAmenityCode.repeatName = false;
                    } else if (response.status === 404) {
                        $scope.editableAmenityCode.repeatName = false;
                    } else if (response.status === 400) {
                        $scope.editableAmenityCode.repeatName = false;
                    }
                }).then(function (amenityCode) {
                    if (amenityCode.name !== null) {
                        $scope.editableAmenityCode.repeatName = true;
                    }
                    ;
                });
            });
        })
        .controller('AmenityCodeEditController', function (AmenityCodeService, $scope, $stateParams, $state, paginationLimit) {

            $scope.editableAmenityCode = AmenityCodeService.get({'id': $stateParams.amenityCodeId});

            $scope.saveAmenityCode = function (amenityCode) {
                console.log("Edit Object :%O", amenityCode);
                amenityCode.$save(function () {
                    $state.go('admin.amenity_code', null, {'reload': true});
                });
            };
        })
        .controller('AmenityCodeDeleteController', function (AmenityCodeService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editableAmenityCode = AmenityCodeService.get({'id': $stateParams.amenityCodeId});
            $scope.deleteAmenityCode = function (amenityCode) {
                amenityCode.$delete(function () {
                    $state.go('admin.amenity_code', null, {'reload': true});
                });
            };
        });







