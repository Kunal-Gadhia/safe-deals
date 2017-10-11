angular.module("safedeals.states.price_range", [])
        .config(function ($stateProvider, templateRoot) {
            $stateProvider.state('admin.masters_price_range', {
                'url': '/price_range?offset',
                'templateUrl': templateRoot + '/masters/price_range/list.html',
                'controller': 'PriceRangeListController'
            });
            $stateProvider.state('admin.masters_price_range.add', {
                'url': '/add',
                'templateUrl': templateRoot + '/masters/price_range/form.html',
                'controller': 'PriceRangeAddController'
            });
            $stateProvider.state('admin.masters_price_range.edit', {
                'url': '/:priceRangeId/edit',
                'templateUrl': templateRoot + '/masters/price_range/form.html',
                'controller': 'PriceRangeEditController'
            });
            $stateProvider.state('admin.masters_price_range.delete', {
                'url': '/:priceRangeId/delete',
                'templateUrl': templateRoot + '/masters/price_range/delete.html',
                'controller': 'PriceRangeDeleteController'
            });
        })
        .controller('PriceRangeListController', function (PriceRangeService, $scope, $stateParams, $state, paginationLimit) {
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

            $scope.nextPriceRanges = PriceRangeService.query({
                'offset': $scope.nextOffset
            });

            $scope.priceRanges = PriceRangeService.query({
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
        .controller('PriceRangeAddController', function (PriceRangeService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editablePriceRange = {};

            $scope.savePriceRange = function (priceRange) {
                console.log("priceRange :%O", priceRange);
                PriceRangeService.save(priceRange, function () {
                    $state.go('admin.masters_price_range', null, {'reload': true});
                });
            };
        })
        .controller('PriceRangeEditController', function (PriceRangeService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editablePriceRange = PriceRangeService.get({'id': $stateParams.priceRangeId});
            $scope.savePriceRange = function (priceRange) {
                priceRange.$save(function () {
                    $state.go('admin.masters_price_range', null, {'reload': true});
                });
            };
        })
        .controller('PriceRangeDeleteController', function (PriceRangeService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editablePriceRange = PriceRangeService.get({'id': $stateParams.priceRangeId});
            $scope.deletePriceRange = function (priceRange) {
                priceRange.$delete(function () {
                    $state.go('admin.masters_price_range', null, {'reload': true});
                });
            };
        });