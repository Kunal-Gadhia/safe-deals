angular.module("safedeals.states.state", [])
        .config(function ($stateProvider, templateRoot) {
            $stateProvider.state('admin.masters_country.state.add', {
                'url': '/add',
                'templateUrl': templateRoot + '/masters/state/form.html',
                'controller': 'StateAddController'
            });
            $stateProvider.state('admin.masters_country.state.city.edit', {
                'url': '/edit',
                'templateUrl': templateRoot + '/masters/state/form.html',
                'controller': 'StateEditController'
            });
            $stateProvider.state('admin.masters_country.state.city.delete', {
                'url': '/delete',
                'templateUrl': templateRoot + '/masters/state/delete.html',
                'controller': 'StateDeleteController'
            });
            $stateProvider.state('admin.masters_country.state.city', {
                'url': '/:stateId/city',
                'templateUrl': templateRoot + '/masters/city/list.html',
                'controller': 'CityListController'
            });
        })
        .controller('StateAddController', function (StateService, CountryService, $state, $scope, $stateParams) {            
            $scope.editableState = {
                'countryId': $stateParams.countryId
            };

            $scope.countries = CountryService.query();

            $scope.saveState = function (state) {
                StateService.save(state, function () {
                    $state.go('admin.masters_country.state', null, {'reload': true});
                });
            };
        })
        .controller('StateEditController', function (StateService, CountryService, $state, $scope, $stateParams) {
            $scope.editableState = StateService.get({'id': $stateParams.stateId});
            $scope.countries = CountryService.query();
            $scope.saveState = function (state) {
                state.$save(function () {
                    $state.go('^', null, {'reload': true});
                });
            };
        })
        .controller('StateDeleteController', function (StateService, $state, $scope, $stateParams) {
            $scope.editableState = StateService.get({'id': $stateParams.stateId});
            $scope.deleteState = function (state) {
                console.log(state);
                state.$delete(function () {
                    $state.go('admin.masters_country.state', null, {'reload': true});
                });
            };
        })
        .controller('CityListController', function (StateService, CityService, CountryService, $scope, $stateParams) {
            $scope.state = StateService.get({
                'id': $stateParams.stateId
            });
            $scope.cities = CityService.findByStateId({
                'stateId': $stateParams.stateId
            });

            $scope.cityExport = function () {
                console.log("are we in export?");
                CityService.exportAllCities(function (a) {
                    console.log("a", a);
                    alert("Downloaded successfully");
                });

            };
        });
