angular.module("safedeals.states.agency", [])
        .config(function ($stateProvider, templateRoot) {
            $stateProvider.state('admin.masters_agency', {
                'url': '/agency_master?offset',
                'templateUrl': templateRoot + '/masters/agency/list.html',
                'controller': 'AgencyListController'
            });
            $stateProvider.state('admin.masters_agency.add', {
                'url': '/add',
                'templateUrl': templateRoot + '/masters/agency/form.html',
                'controller': 'AgencyAddController'
            });
            $stateProvider.state('admin.masters_agency.edit', {
                'url': '/:agencyId/edit',
                'templateUrl': templateRoot + '/masters/agency/form.html',
                'controller': 'AgencyEditController'
            });
            $stateProvider.state('admin.masters_agency.delete', {
                'url': '/:agencyId/delete',
                'templateUrl': templateRoot + '/masters/agency/delete.html',
                'controller': 'AgencyDeleteController'
            });
        })
        .controller('AgencyListController', function (AgencyService, $scope, $stateParams, $state, paginationLimit) {
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

            $scope.nextAgencies = AgencyService.query({
                'offset': $scope.nextOffset
            });

            $scope.agencies = AgencyService.query({
                'offset': $scope.currentOffset
            });
            console.log("$scope.agencies", $scope.agencies);

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
        .controller('AgencyAddController', function (AgencyService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editableAgency = {};
            $scope.saveAgency = function (agency) {
                console.log("agency :" + agency);
                AgencyService.save(agency, function () {
                    $state.go('admin.masters_agency', null, {'reload': true});
                });
            };
            $scope.$watch('editableAgency.name', function (name) {
                console.log("Name :" + name);
                AgencyService.findByName({'name': name}).$promise.catch(function (response) {
                    if (response.status === 500) {
                        $scope.editableAgency.repeatName = false;
                    } else if (response.status === 404) {
                        $scope.editableAgency.repeatName = false;
                    } else if (response.status === 400) {
                        $scope.editableAgency.repeatName = false;
                    }
                }).then(function (agency) {
                    if (agency.name !== null) {
                        $scope.editableAgency.repeatName = true;
                    }
                    ;
                });
            });
        })
        .controller('AgencyEditController', function (AgencyService, $scope, $stateParams, $state, paginationLimit) {

            $scope.editableAgency = AgencyService.get({'id': $stateParams.agencyId});
            $scope.saveAgency = function (agency) {
                console.log("aaa", agency);
                agency.$save(function () {
                    $state.go('admin.masters_agency', null, {'reload': true});
                });
            };
        })
        .controller('AgencyDeleteController', function (AgencyService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editableAgency = AgencyService.get({'id': $stateParams.agencyId});
            $scope.deleteAgency = function (agency) {
                agency.$delete(function () {
                    $state.go('admin.masters_agency', null, {'reload': true});
                });
            };
        }); /*AmenityDetailService, LocationService, CityService, */





