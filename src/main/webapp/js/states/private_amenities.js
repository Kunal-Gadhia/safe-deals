angular.module("safedeals.states.private_amenities", [])
        .config(function ($stateProvider, templateRoot) {
            $stateProvider.state('admin.private_amenities', {
                'url': '/private_amenities_master?offset',
                'templateUrl': templateRoot + '/masters/private_amenities/list.html',
                'controller': 'PrivateAmenitiesListController'
            });
            $stateProvider.state('admin.private_amenities.add', {
                'url': '/add',
                'templateUrl': templateRoot + '/masters/private_amenities/form.html',
                'controller': 'PrivateAmenitiesAddController'
            });
            $stateProvider.state('admin.private_amenities.edit', {
                'url': '/:privateAmenitiesId/edit',
                'templateUrl': templateRoot + '/masters/private_amenities/form.html',
                'controller': 'PrivateAmenitiesEditController'
            });
            $stateProvider.state('admin.private_amenities.delete', {
                'url': '/:privateAmenitiesId/delete',
                'templateUrl': templateRoot + '/masters/private_amenities/delete.html',
                'controller': 'PrivateAmenitiesDeleteController'
            });
        })
        .controller('PrivateAmenitiesListController', function (PrivateAmenitiesService, $scope, $stateParams, $state, paginationLimit) {
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

            $scope.nextPrivateAmenitiess = PrivateAmenitiesService.query({
                'offset': $scope.nextOffset
            });
//
            $scope.privateAmenitiess = PrivateAmenitiesService.query({
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
        .controller('PrivateAmenitiesAddController', function (PrivateAmenitiesService, $scope, $state) {
            $scope.editablePrivateAmenities = {};
            $scope.savePrivateAmenities = function (privateAmenities) {
                PrivateAmenitiesService.save(privateAmenities, function () {
                    $state.go('admin.private_amenities', null, {'reload': true});
                });
            };
            $scope.$watch('editablePrivateAmenities.name', function (name) {
                PrivateAmenitiesService.findByName({'name': name}).$promise.catch(function (response) {
                    console.log("Response :%O", response);
                    if (response.status === 500) {
                        $scope.editablePrivateAmenities.repeatName = false;
                    } else if (response.status === 404) {
                        $scope.editablePrivateAmenities.repeatName = false;
                    } else if (response.status === 400) {
                        $scope.editablePrivateAmenities.repeatName = false;
                    }
                }).then(function (privateAmenities) {
                    if (privateAmenities.name !== null) {
                        $scope.editablePrivateAmenities.repeatName = true;
                    }
                    ;
                });
            });
        })
        .controller('PrivateAmenitiesEditController', function (PrivateAmenitiesService, $scope, $stateParams, $state, paginationLimit) {

            $scope.editablePrivateAmenities = PrivateAmenitiesService.get({'id': $stateParams.privateAmenitiesId});

            $scope.savePrivateAmenities = function (privateAmenities) {
                console.log("Edit Object :%O", privateAmenities);
                privateAmenities.$save(function () {
                    $state.go('admin.private_amenities', null, {'reload': true});
                });
            };
        })
        .controller('PrivateAmenitiesDeleteController', function (PrivateAmenitiesService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editablePrivateAmenities = PrivateAmenitiesService.get({'id': $stateParams.privateAmenitiesId});
            $scope.deletePrivateAmenities = function (privateAmenities) {
                privateAmenities.$delete(function () {
                    $state.go('admin.private_amenities', null, {'reload': true});
                });
            };
        });







