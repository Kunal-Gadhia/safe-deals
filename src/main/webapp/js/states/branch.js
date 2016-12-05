angular.module("safedeals.states.branch", [])
        .config(function ($stateProvider, templateRoot) {
            $stateProvider.state('admin.masters_branch', {
                'url': '/branch_master?offset',
                'templateUrl': templateRoot + '/masters/branch/list.html',
                'controller': 'BranchListController'
            });
            $stateProvider.state('admin.masters_branch.add', {
                'url': '/add',
                'templateUrl': templateRoot + '/masters/branch/form.html',
                'controller': 'BranchAddController'
            });
            $stateProvider.state('admin.masters_branch.edit', {
                'url': '/:branchId/edit',
                'templateUrl': templateRoot + '/masters/branch/form.html',
                'controller': 'BranchEditController'
            });
            $stateProvider.state('admin.masters_branch.delete', {
                'url': '/:branchId/delete',
                'templateUrl': templateRoot + '/masters/branch/delete.html',
                'controller': 'BranchDeleteController'
            });
        })
        .controller('BranchListController', function (BranchService, BankService, LocationService, CityService, $scope, $stateParams, $state, paginationLimit) {
            console.log("List COntroller ?");
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

            $scope.nextBranches = BranchService.query({
                'offset': $scope.nextOffset
            });

            $scope.branches = BranchService.query({
                'offset': $scope.currentOffset
            }
            , function () {
                angular.forEach($scope.branches, function (branch) {
                    branch.bank = BankService.get({
                        'id': branch.bankId
                    });
                    branch.location = LocationService.get({
                        'id': branch.locationId
                    });
                    branch.city = CityService.get({
                        'id': branch.cityId
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
        .controller('BranchAddController', function (BranchService, BankService, LocationService, CityService, $scope, $stateParams, $state, paginationLimit) {
            console.log("Coming to add controller");
            $scope.editableBranch = {};
//            $scope.locations = LocationService.query();
//            $scope.banks = BankService.query();
//            $scope.cities = CityService.query();

            $scope.setLocation = function (location) {
                $scope.editableBranch.locationId = location.id;
                $scope.editableBranch.location = location;
            };

            $scope.setBank = function (bank) {
                console.log("setBank", bank);
                $scope.editableBranch.bankId = bank.id;
                $scope.editableBranch.bank = bank;
            };

            $scope.setCity = function (city) {
                $scope.editableBranch.cityId = city.id;
                $scope.editableBranch.city = city;
            };

            $scope.searchBanks = function (searchTerm) {
                console.log("Search Term :%O", searchTerm);
                return BankService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };

            $scope.searchLocations = function (searchTerm) {
                console.log("City Id :%O", $scope.editableBranch.cityId);
                if ($scope.editableBranch.cityId === undefined) {
                    return LocationService.findByNameLike({
                        'name': searchTerm
                    }).$promise;
                } else {
                    return LocationService.findByNameAndCityId({
                        'name': searchTerm,
                        'cityId': $scope.editableBranch.cityId
                    }).$promise;
                }
            };

            $scope.searchCities = function (searchTerm) {
                console.log("Search Term :%O", searchTerm);
                return CityService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };

            $scope.$watch('editableBranch.name', function (name) {
                console.log("Branch Name :" + name);
                BranchService.findByName({'name': name}).$promise.catch(function (response) {
                    if (response.status === 500) {
                        $scope.editableBranch.repeatBranch = false;
                    }
                    else if (response.status === 404) {
                        $scope.editableBranch.repeatBranch = false;
                    }
                    else if (response.status === 400) {
                        $scope.editableBranch.repeatBranch = false;
                    }
                }).then(function (branch) {
                    if (branch.name !== null) {
                        $scope.editableBranch.repeatBranch = true;
                    }
                    ;
                });
            });
            $scope.saveBranch = function (branch) {
                console.log("Save ??");
                BranchService.save(branch, function () {
                    $state.go('admin.masters_branch', null, {'reload': true});
                });
            };
        })
        .controller('BranchEditController', function (BranchService, BankService, LocationService, CityService, $scope, $stateParams, $state, paginationLimit) {
            console.log("Coming to edit controller");
//            $scope.locations = LocationService.query();
//            $scope.banks = BankService.query();
//            $scope.cities = CityService.query();
            $scope.editableBranch = BranchService.get({'id': $stateParams.branchId}, function () {
                console.log("Inside Function :%O", $scope.editableBranch);

                $scope.editableBranch.bank = BankService.get({
                    'id': $scope.editableBranch.bankId
                });

                $scope.editableBranch.location = LocationService.get({
                    'id': $scope.editableBranch.locationId
                });

                $scope.editableBranch.city = CityService.get({
                    'id': $scope.editableBranch.cityId
                });
            });
            console.log("editableBranch", $scope.editableBranch);

            $scope.setLocation = function (location) {
                $scope.editableBranch.locationId = location.id;
                $scope.editableBranch.location = location;
            };

            $scope.setBank = function (bank) {
                $scope.editableBranch.bankId = bank.id;
                $scope.editableBranch.bank = bank;
            };

            $scope.setCity = function (city) {
                $scope.editableBranch.cityId = city.id;
                $scope.editableBranch.city = city;
            };

            $scope.searchBanks = function (searchTerm) {
                console.log("Search Term :%O", searchTerm);
                return BankService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };

            $scope.searchLocations = function (searchTerm) {
                console.log("City Id :%O", $scope.editableBranch.cityId);
                if ($scope.editableBranch.cityId === undefined) {
                    return LocationService.findByNameLike({
                        'name': searchTerm
                    }).$promise;
                } else {
                    return LocationService.findByNameAndCityId({
                        'name': searchTerm,
                        'cityId': $scope.editableBranch.cityId
                    }).$promise;
                }
            };

            $scope.searchCities = function (searchTerm) {
                console.log("Search Term :%O", searchTerm);
                return CityService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };

            $scope.saveBranch = function (branch) {
                branch.$save(branch, function () {
                    $state.go('admin.masters_branch', null, {'reload': true});
                });
            };
        })
        .controller('BranchDeleteController', function (BranchService, $scope, $stateParams, $state) {
            $scope.editableBranch = BranchService.get({'id': $stateParams.branchId});
            $scope.deleteBranch = function (branch) {
                branch.$delete(function () {
                    $state.go('admin.masters_branch', null, {'reload': true});
                });
            };
        });