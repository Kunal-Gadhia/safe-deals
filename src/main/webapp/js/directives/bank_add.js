angular.module('safedeals.services.bank_addition', []);
angular.module('safedeals.services.bank_addition', [])
        .directive('addBank', function (BankService, templateRoot) {
            return{
                'restrict': 'AE',
                'templateUrl': templateRoot + '/directives/bank_add.html',
                'scope': {
                    'onSelect': '&onSelect',
                    'showWidget': '='
                },
                'controller': function ($scope) {
                    $scope.editableBank = {};

                    $scope.saveBank = function (bank) {
                        BankService.save(bank, function () {
                            $scope.showWidget = false;
                        }).$promise.then(function () {

                        });
                    };
//                    close current form
                    $scope.close = function () {
                        $scope.showWidget = false;
                    };
                }
            };
        });

