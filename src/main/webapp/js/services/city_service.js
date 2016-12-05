angular.module("safedeals.services.city", []);
angular.module("safedeals.services.city")
        .factory('CityService', function ($resource, restRoot) {
            return $resource(restRoot + '/city/:id', {'id': '@id'}, {
                'findByStateId': {
                    'method': 'GET',
                    'url': restRoot + '/city/find/state_id',
                    'params': {
                        'stateId': '@stateId'
                    },
                    'isArray': true
                },
                'findAllCities': {
                    'method': 'GET',
                    'url': restRoot + '/city/cities',
                    'isArray': true
                },
                'findByName': {
                    'method': 'GET',
                    'url': restRoot + '/city/find/name',
                    'params': {
                        'name': '@name',
                        'stateId': '@stateId'
                    },
                    'isArray': true
                },
                'findByNameAndCountryId': {
                    'method': 'GET',
                    'url': restRoot + '/city/find/country/name',
                    'params': {
                        'name': '@name',
                        'countryId': '@countryId'
                    },
                    'isArray': true
                },
                'findByNameAndStateId': {
                    'method': 'GET',
                    'url': restRoot + '/city/find/name',
                    'params': {
                        'name': '@name',
                        'stateId': '@stateId'
                    },
                    'isArray': true
                },
                'exportAllCities': {
                    'method': 'POST',
                    'url': restRoot + '/city/export'
                },
                'findByNameLike': {
                    'method': 'GET',
                    'url': restRoot + '/city/find/city_like',
                    'params': {
                        'name': '@name'
                    },
                    'isArray': true
                },
                'findByCityName': {
                    'method': 'GET',
                    'url': restRoot + '/city/find_by_name',
                    'params': {
                        'name': '@name'
                    },
                    'isArray': false
                }
            });
        })
        .directive('vsCitySelect', function (CityService, templateRoot, paginationLimit) {
            return{
                'restrict': 'AE',
                'templateUrl': templateRoot + '/directives/selection_widgets/city.html',
                'scope': {
                    'onSelect': '&onSelect',
                    'showWidget': '=',
                    'status': '=status'
                },
                'link': function (scope, element, attrs) {
                    scope.currentOffset = 0;

                    var refreshPage = function () {
                        scope.cities = CityService.query({
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

                    scope.select = function (selectedCity) {
                        scope.close();
                        console.log("Value of selected selectedCity: " + selectedCity);
                        //call the callback function
                        scope.onSelect({
                            'city': selectedCity
                        });
                    };

                    scope.close = function () {
                        //hide the widget
                        scope.showWidget = false;
                    };
                    refreshPage();
                }
            };
        });
