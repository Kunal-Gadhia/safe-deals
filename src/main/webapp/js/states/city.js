angular.module("safedeals.states.city", [])
        .config(function ($stateProvider, templateRoot) {
            $stateProvider.state('admin.masters_country.state.city.add', {
                'url': '/add',
                'templateUrl': templateRoot + '/masters/city/form.html',
                'controller': 'CityAddController'
            });
            $stateProvider.state('admin.masters_country.state.city.city_edit', {
                'url': '/:cityId/edit',
                'templateUrl': templateRoot + '/masters/city/form.html',
                'controller': 'CityEditController'
            });
            $stateProvider.state('admin.masters_country.state.city.city_delete', {
                'url': '/:cityId/delete',
                'templateUrl': templateRoot + '/masters/city/delete.html',
                'controller': 'CityDeleteController'
            });
        })
        .controller('CityAddController', function (CityService, CountryService, StateService, $state, $scope, $stateParams) {

            $scope.countries = CountryService.query();
            $scope.states = StateService.query();
            console.log("1==1 %0 :", $scope.countries);

            $scope.editableCity = {
                'countryId': $stateParams.countryId,
                'stateId': $stateParams.stateId
            };
//            $scope.cityExport = function () {
//                console.log("are we in export?");
//                CityService.exportAllCities(function (a) {
//                    console.log("a", a);
//                    alert("Downloaded successfully");
//                });
//
//            };
            $scope.saveCity = function (city) {
                CityService.save(city, function (savedCity) {
                    $state.go('admin.masters_country.state.city', null, {'reload': true});
                });
            };
        })
        .controller('CityEditController', function (CityService, CountryService, StateService, $state, $scope, $stateParams) {
            console.log("we here?");
            $scope.editableCity = CityService.get({'id': $stateParams.cityId});
            $scope.countries = CountryService.query();
            $scope.states = StateService.query();

            
            $scope.saveCity = function (city) {
                city.$save(function () {
                    $state.go('^', null, {'reload': true});
                });
            };
        })
        .controller('CityDeleteController', function (CityService, $state, $scope, $stateParams) {
            $scope.editableCity = CityService.get({'id': $stateParams.cityId});
            $scope.deleteCity = function (city) {
                city.$delete(function () {
                    $state.go('^', null, {'reload': true});
                });
            };
        });