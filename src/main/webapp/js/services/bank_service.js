angular.module("safedeals.services.bank", []);
angular.module("safedeals.services.bank")
        .factory('BankService', function ($resource, restRoot) {
            return $resource(restRoot + '/bank/:id', {'id': '@id'}, {
                'findAllBanks': {
                    'method': 'GET',
                    'url': restRoot + '/bank/banks',
                    'isArray': true
                },
                'findByName': {
                    'method': 'GET',
                    'url': restRoot + '/bank/find/name',
                    'params': {
                        'name': '@name'
                    },
                    'isArray': false
                },
                'findByNameLike': {
                    'method': 'GET',
                    'url': restRoot + '/bank/find/bank_like',
                    'params': {
                        'name': '@name'
                    },
                    'isArray': true
                }
            });
        })
        .directive('sdBankSelect', function (BankService, templateRoot, paginationLimit) {
            return {
                'restrict': 'AE',
                'templateUrl': templateRoot + '/directives/selection_widgets/bank.html',
                'scope': {
                    'onSelect': '&onSelect',
                    'showWidget': '='
                },
                'link': function (scope, element, attrs) {
                    scope.currentOffset = 0;

                    var refreshPage = function () {
                        scope.banks = BankService.query({
                            'offset': scope.currentOffset
                        });
                    };

                    scope.nextPage = function () {
                        scope.currentOffset += paginationLimit;
                        refreshPage();
                    };
                    scope.previousPage = function () {
                        if (scope.currentOffset <= 0) {
                            return;
                        }
                        scope.currentOffset -= paginationLimit;
                        refreshPage();
                    };

                    scope.select = function (selectedBank) {
                        scope.close();
                        console.log(selectedBank);
                        //call the callback function
                        scope.onSelect({
                            'bank': selectedBank
                        });
                    };

                    scope.clear = function () {
                        scope.select(null);
                    };

                    scope.close = function () {
                        //hide the widget
                        scope.showWidget = false;
                    };
                    refreshPage();
                }
            };
        });