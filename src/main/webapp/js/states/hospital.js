angular.module("safedeals.states.hospital", [])
        .config(function ($stateProvider, templateRoot) {
            $stateProvider.state('admin.masters_hospital', {
                'url': '/hospital_master?offset',
                'templateUrl': templateRoot + '/masters/hospital/list.html',
                'controller': 'HospitalListController'
            });
            $stateProvider.state('admin.masters_hospital.add', {
                'url': '/add',
                'templateUrl': templateRoot + '/masters/hospital/form.html',
                'controller': 'HospitalAddController'
            });
            $stateProvider.state('admin.masters_hospital.edit', {
                'url': '/:hospitalId/edit',
                'templateUrl': templateRoot + '/masters/hospital/form.html',
                'controller': 'HospitalEditController'
            });
            $stateProvider.state('admin.masters_hospital.delete', {
                'url': '/:hospitalId/delete',
                'templateUrl': templateRoot + '/masters/hospital/delete.html',
                'controller': 'HospitalDeleteController'
            });
        })
        .controller('HospitalListController', function (HospitalService, LocationService, $scope, $stateParams, $state, paginationLimit) {
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

            $scope.nextHospitals = HospitalService.query({
                'offset': $scope.nextOffset
            });

            $scope.hospitals = HospitalService.query({
                'offset': $scope.currentOffset
            }, function () {
                angular.forEach($scope.hospitals, function (hospital) {
                    hospital.location = LocationService.get({
                        'id': hospital.locationId
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
        .controller('HospitalAddController', function (HospitalService, LocationService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editableHospital = {};
            $scope.setLocation = function (location) {
                $scope.editableHospital.locationId = location.id;
                $scope.editableHospital.location = location;
            };
            $scope.searchLocations = function (searchTerm) {
                return LocationService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };

            $scope.$watch('editableHospital.name', function (name) {
                console.log("Branch Name :" + name);
                HospitalService.findByName({'name': name}).$promise.catch(function (response) {
                    if (response.status === 500) {
                        $scope.editableHospital.repeatHospital = false;
                    }
                    else if (response.status === 404) {
                        $scope.editableHospital.repeatHospital = false;
                    }
                    else if (response.status === 400) {
                        $scope.editableHospital.repeatHospital = false;
                    }
                }).then(function (hospital) {
                    if (hospital.name !== null) {
                        $scope.editableHospital.repeatHospital = true;
                    }
                    ;
                });
            });

            $scope.saveHospital = function (hospital) {
                console.log("Save ??");
                HospitalService.save(hospital, function () {
                    $state.go('admin.masters_hospital', null, {'reload': true});
                });
            };
        })
        .controller('HospitalEditController', function (HospitalService, LocationService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editableHospital = HospitalService.get({
                'id': $stateParams.hospitalId
            }, function () {
                $scope.editableHospital.location = LocationService.get({
                    'id': $scope.editableHospital.locationId
                });
            });
            $scope.setLocation = function (location) {
                console.log("Set Workplace TErm :%O", location);
                $scope.editableHospital.locationId = location.id;
                console.log("$scope.editableHospital.locationName", $scope.editableHospital.name);
                $scope.editableHospital.location = location;
                console.log("$scope.editableHospital.location ", $scope.editableHospital.location);
            };
            $scope.searchLocations = function (searchTerm) {
                return LocationService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };
            $scope.saveHospital = function (hospital) {
                hospital.$save(function () {
                    $state.go('admin.masters_hospital', null, {'reload': true});
                });
            };
        })
        .controller('HospitalDeleteController', function (HospitalService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editableHospital = HospitalService.get({'id': $stateParams.hospitalId});
            console.log("are we here?");
            $scope.deleteHospital = function (hospital) {
                hospital.$delete(function () {
                    $state.go('admin.masters_hospital', null, {'reload': true});
                });
            };
        });