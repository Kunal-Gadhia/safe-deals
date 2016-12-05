angular.module("safedeals.states.bank", [])
        .config(function ($stateProvider, templateRoot) {
            $stateProvider.state('admin.masters_bank', {
                'url': '/bank_master?offset',
                'templateUrl': templateRoot + '/masters/bank/list.html',
                'controller': 'BankListController'
            });
            $stateProvider.state('admin.masters_bank.add', {
                'url': '/add',
                'templateUrl': templateRoot + '/masters/bank/form.html',
                'controller': 'BankAddController'
            });
            $stateProvider.state('admin.masters_bank.edit', {
                'url': '/:bankId/edit',
                'templateUrl': templateRoot + '/masters/bank/form.html',
                'controller': 'BankEditController'
            });
            $stateProvider.state('admin.masters_bank.delete', {
                'url': '/:bankId/delete',
                'templateUrl': templateRoot + '/masters/bank/delete.html',
                'controller': 'BankDeleteController'
            });
        })
        .controller('BankListController', function (BankService, $scope, $stateParams, $state, paginationLimit) {
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

            $scope.nextBanks = BankService.query({
                'offset': $scope.nextOffset
            });

            $scope.banks = BankService.query({
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
        .controller('BankAddController', function (BankService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editableBank = {};

            $scope.saveBank = function (bank) {
                console.log("bank name:" + bank.name);
                BankService.save(bank, function () {
                    $state.go('admin.masters_bank', null, {'reload': true});
                });
            };
            $scope.$watch('editableBank.name', function (name) {
                console.log("Name :" + name);
                BankService.findByName({'name': name}).$promise.catch(function (response) {
                    if (response.status === 500) {
                        $scope.editableBank.repeatName = false;
                    }
                    else if (response.status === 404) {
                        $scope.editableBank.repeatName = false;
                    }
                    else if (response.status === 400) {
                        $scope.editableBank.repeatName = false;
                    }
                }).then(function (bank) {
                    if (bank.name !== null) {
                        $scope.editableBank.repeatName = true;
                    }
                    ;
                });
            });
        })
        .controller('BankEditController', function (BankService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editableBank = BankService.get({'id': $stateParams.bankId});

            $scope.saveBank = function (bank) {
                bank.$save(function () {
                    $state.go('admin.masters_bank', null, {'reload': true});
                });
            };
        })
        .controller('BankDeleteController', function (BankService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editableBank = BankService.get({'id': $stateParams.bankId});
            console.log("are we here?");
            $scope.deleteBank = function (bank) {
                bank.$delete(function () {
                    $state.go('admin.masters_bank', null, {'reload': true});
                });
            };
        });