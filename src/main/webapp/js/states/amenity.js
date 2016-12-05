angular.module("safedeals.states.amenity", [])
        .config(function ($stateProvider, templateRoot) {
            $stateProvider.state('admin.masters_amenity', {
                'url': '/amenity_master?offset',
                'templateUrl': templateRoot + '/masters/amenity/list.html',
                'controller': 'AmenityListController'
            });
            $stateProvider.state('admin.masters_amenity.add', {
                'url': '/add',
                'templateUrl': templateRoot + '/masters/amenity/form.html',
                'controller': 'AmenityAddController'
            });
            $stateProvider.state('admin.masters_amenity.edit', {
                'url': '/:amenityId/edit',
                'templateUrl': templateRoot + '/masters/amenity/form.html',
                'controller': 'AmenityEditController'
            });
            $stateProvider.state('admin.masters_amenity.delete', {
                'url': '/:amenityId/delete',
                'templateUrl': templateRoot + '/masters/amenity/delete.html',
                'controller': 'AmenityDeleteController'
            });
        })

        .controller('AmenityListController', function (AmenityService, AmenityCodeService, $scope, $stateParams, $state, paginationLimit) {
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

            $scope.nextAmenities = AmenityService.query({
                'offset': $scope.nextOffset
            });

            $scope.amenities = AmenityService.query({
                'offset': $scope.currentOffset
            }, function (amenityDetails) {
                angular.forEach(amenityDetails, function (amenityDetail) {
                    console.log("Amenity Detail:%O", amenityDetail);
                    amenityDetail.amenityCodeObject = AmenityCodeService.get({
                        'id': amenityDetail.amenityCodeId
                    });
                });
            });

            console.log("Amenities List :%O", $scope.amenities);

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

            $scope.amenityExport = function () {
                console.log("are we in export?");
                AmenityService.exportAllAmenities(function (a) {
                    console.log("a", a);
                    alert("Downloaded successfully");
                });

            };
        })
        .controller('AmenityAddController', function (AmenityService, AmenityCodeService, $scope, $state) {
            $scope.options = [{value: "Residential", id: 1}, {value: "Commercial", id: 2}, {value: "Industrial", id: 3}];

            $scope.editableAmenity = {};

            $scope.$watch('editableAmenity.name', function (name) {
                console.log("Amenity Name :" + name);
                AmenityService.findByAmenityName({'name': name}).$promise.catch(function (response) {
                    if (response.status === 500) {
                        $scope.editableAmenity.repeatAmenity = false;
                    }
                    else if (response.status === 404) {
                        $scope.editableAmenity.repeatAmenity = false;
                    }
                    else if (response.status === 400) {
                        $scope.editableAmenity.repeatAmenity = false;
                    }
                }).then(function (amenity) {
                    if (amenity.name !== null) {
                        $scope.editableAmenity.repeatAmenity = true;
                    }
                    ;
                });
            });

            $scope.setAmenityCode = function (amenityCode) {
                console.log("set Amenity Code", amenityCode);
                $scope.editableAmenity.amenityCodeId = amenityCode.id;
                $scope.editableAmenity.amenityCode = amenityCode;
            };

            $scope.searchAmenityCodes = function (searchTerm) {
                console.log("Search Term :%O", searchTerm);
                return AmenityCodeService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };

            $scope.saveAmenity = function (amenity) {
                console.log("amenity name:", amenity);
                AmenityService.save(amenity, function () {
                    $state.go('admin.masters_amenity', null, {'reload': true});
                });
            };
        })
        .controller('AmenityEditController', function (AmenityService, AmenityCodeService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editableAmenity = AmenityService.get({'id': $stateParams.amenityId}, function(amenityObject){
                $scope.editableAmenity.amenityCode = AmenityCodeService.get({
                    'id': $scope.editableAmenity.amenityCodeId
                });
            });

            $scope.setAmenityCode = function (amenityCode) {
                console.log("set Amenity Code", amenityCode);
                $scope.editableAmenity.amenityCodeId = amenityCode.id;
                $scope.editableAmenity.amenityCode = amenityCode;
            };

            $scope.searchAmenityCodes = function (searchTerm) {
                console.log("Search Term :%O", searchTerm);
                return AmenityCodeService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };

            $scope.saveAmenity = function (amenity) {
                amenity.$save(function () {
                    $state.go('admin.masters_amenity', null, {'reload': true});
                });
            };
        })
        .controller('AmenityDeleteController', function (AmenityService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editableAmenity = AmenityService.get({'id': $stateParams.amenityId});
            console.log("are we here?");
            $scope.deleteAmenity = function (amenity) {
                amenity.$delete(function () {
                    $state.go('admin.masters_amenity', null, {'reload': true});
                });
            };
        });
        