angular.module("safedeals.states.country", [])
        .config(function ($stateProvider, templateRoot) {
            $stateProvider.state('admin.masters_country', {
                'url': '/country?offset',
                'templateUrl': templateRoot + '/masters/country/list.html',
                'controller': 'CountryListController'
            });
            $stateProvider.state('admin.masters_country.add', {
                'url': '/add',
                'templateUrl': templateRoot + '/masters/country/form.html',
                'controller': 'CountryAddController'
            });
            $stateProvider.state('admin.masters_country.state.edit', {
                'url': '/edit',
                'templateUrl': templateRoot + '/masters/country/form.html',
                'controller': 'CountryEditController'
            });
            $stateProvider.state('admin.masters_country.state.delete', {
                'url': '/delete',
                'templateUrl': templateRoot + '/masters/country/delete.html',
                'controller': 'CountryDeleteController'
            });
            $stateProvider.state('admin.masters_country.state', {
                'url': '/:countryId/state',
                'templateUrl': templateRoot + '/masters/state/list.html',
                'controller': 'StateListController'
            });
        })
        .controller('CountryListController', function (CountryService, $scope, $stateParams, $state, paginationLimit) {

            if (
                    $stateParams.offset === undefined ||
                    isNaN($stateParams.offset) ||
                    new Number($stateParams.offset) < 0)
            {
                $scope.currentOffset = 0;
            } else {
                $scope.currentOffset = new Number($stateParams.offset);
            }

            $scope.countries = CountryService.query({
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
        .controller('CountryAddController', function (CountryService, $state, $scope, $stateParams) {
            $scope.editableCountry = {};
            $scope.saveCountry = function (country) {
                CountryService.save(country, function () {
                    $state.go('admin.masters_country', null, {'reload': true});
                });
            };
        })
        .controller('CountryEditController', function (CountryService, $state, $scope, $stateParams) {
            $scope.editableCountry = CountryService.get({'id': $stateParams.countryId});
            $scope.saveCountry = function (country) {
                country.$save(function () {
                    $state.go('admin.masters_country.state', null, {'reload': true});
                });
            };
        })
        .controller('CountryDeleteController', function (CountryService, $state, $scope, $stateParams) {
            $scope.editableCountry = CountryService.get({'id': $stateParams.countryId});
            $scope.deleteCountry = function (country) {
                console.log(country);
                country.$delete(function () {
                    $state.go('admin.masters_country', null, {'reload': true});
                });
            };
        })
        .controller('StateListController', function (StateService, CountryService, $scope, $stateParams, $state, paginationLimit) {
            $scope.country = CountryService.get({
                'id': $stateParams.countryId
            });
            $scope.states = StateService.findByCountryId({
                'countryId': $stateParams.countryId
            });
        });
