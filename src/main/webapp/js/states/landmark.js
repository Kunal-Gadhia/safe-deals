angular.module("safedeals.states.landmark", [])
        .config(function ($stateProvider, templateRoot) {
            $stateProvider.state('admin.masters_landmark', {
                'url': '/landmark_master?offset',
                'templateUrl': templateRoot + '/masters/landmark/list.html',
                'controller': 'LandmarkListController'
            });
            $stateProvider.state('admin.masters_landmark.add', {
                'url': '/add',
                'templateUrl': templateRoot + '/masters/landmark/form.html',
                'controller': 'LandmarkAddController'
            });
            $stateProvider.state('admin.masters_landmark.edit', {
                'url': '/:landmarkId/edit',
                'templateUrl': templateRoot + '/masters/landmark/form.html',
                'controller': 'LandmarkEditController'
            });
            $stateProvider.state('admin.masters_landmark.delete', {
                'url': '/:landmarkId/delete',
                'templateUrl': templateRoot + '/masters/landmark/delete.html',
                'controller': 'LandmarkDeleteController'
            });
        })

        .controller('LandmarkListController', function (LandmarkService, LocationService, CityService, $scope, $stateParams, $state, paginationLimit) {
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

            $scope.nextLandmarks = LandmarkService.query({
                'offset': $scope.nextOffset
            });

            $scope.landmarks = LandmarkService.query({
                'offset': $scope.currentOffset
            }, function (landmark) {
                angular.forEach($scope.landmarks, function (landmarks) {
                    if (landmarks.cityId !== null) {
                        landmarks.city = CityService.get({
                            'id': landmarks.cityId
                        });
                    }
                    if (landmarks.locationId !== null) {
                        landmarks.location = LocationService.get({
                            'id': landmarks.locationId
                        });
                    }
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

        .controller('LandmarkAddController', function (LandmarkService, CityService, LocationService, $stateParams, $scope, $state) {
            $scope.editableLandmark = {};

            $scope.saveLandmark = function (landmark) {
                console.log("Save ??");
                LandmarkService.save(landmark, function () {
                    $state.go('admin.masters_landmark', null, {'reload': true});
                });
            };

            $scope.setCity = function (city) {
                $scope.editableLandmark.cityId = city.id;
                $scope.editableLandmark.city = city;
                console.log("$scope.editableLandmark.city ", $scope.editableLandmark.city);
            };
            $scope.setLocation = function (location) {
                $scope.editableLandmark.locationId = location.id;
                $scope.editableLandmark.location = location;
                console.log("$scope.editableLandmark.location ", $scope.editableLandmark.location);
            };

            $scope.searchCities = function (searchTerm) {
                console.log("City Id :%O", searchTerm);
                return CityService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };
            $scope.searchLocations = function (searchTerm) {
                if ($scope.editableLandmark.cityId === undefined) {
                    console.log("Coming to if ??");
                    return LocationService.findByNameLike({
                        'name': searchTerm
                    }).$promise;
                } else {
                    console.log("Coming to Else ??");
                    return LocationService.findByNameAndCityId({
                        'name': searchTerm,
                        'cityId': $scope.editableLandmark.cityId
                    }).$promise;
                }
            };

        })
        .controller('LandmarkEditController', function (LandmarkService, CityService, LocationService, $scope, $stateParams, $state) {
            console.log("are we here?");
            $scope.editableLandmark = LandmarkService.get({
                'id': $stateParams.landmarkId
            }, function () {
                console.log("$scope.editableLandmark", $scope.editableLandmark);
                if ($scope.editableLandmark.cityId !== null) {
                    CityService.get({
                        'id': $scope.editableLandmark.cityId
                    }, function (city) {
                        console.log("location :%O", city);
                        $scope.editableLandmark.city = city;
                    });
                }
                if ($scope.editableLandmark.locationId !== null) {
                    LocationService.get({
                        'id': $scope.editableLandmark.locationId
                    }, function (location) {
                        console.log("location :%O", location);
                        $scope.editableLandmark.location = location;
                    });
                }
            });

            $scope.setCity = function (city) {
                console.log("xyz", city);
                $scope.editableLandmark.cityId = city.id;
                $scope.editableLandmark.city = city;
                console.log("$scope.editableLandmark.city ", $scope.editableLandmark.city);
            };
            $scope.setLocation = function (location) {
                $scope.editableLandmark.locationId = location.id;
                $scope.editableLandmark.location = location;
                console.log("$scope.editableLandmark.location ", $scope.editableLandmark.location);
            };
            $scope.searchCities = function (searchTerm) {
                console.log("City Id :%O", searchTerm);
                return CityService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };
            $scope.searchLocations = function (searchTerm) {
                console.log("Search Term :%O", searchTerm);
                console.log("City Id :%O", $scope.editableLandmark.cityId);
                if ($scope.editableLandmark.cityId === undefined) {
                    console.log("Coming to if ??");
                    return LocationService.findByNameLike({
                        'name': searchTerm
                    }).$promise;
                } else {
                    console.log("Coming to Else ??");
                    return LocationService.findByNameAndCityId({
                        'name': searchTerm,
                        'cityId': $scope.editableLandmark.cityId
                    }).$promise;
                }
            };

            $scope.saveLandmark = function (landmark) {
                console.log("Edit ??");
                landmark.$save(landmark, function () {
                    $state.go('admin.masters_landmark', null, {'reload': true});
                    console.log("save ??");
                });
            };
        })
        .controller('LandmarkDeleteController', function (LandmarkService, $scope, $stateParams, $state) {
            $scope.editableLandmark = LandmarkService.get({'id': $stateParams.landmarkId});
            console.log("are we here?");
            $scope.deleteLandmark = function (landmark) {
                landmark.$delete(function () {
                    $state.go('admin.masters_landmark', null, {'reload': true});
                });
            };
        });



