angular.module("safedeals.services.location", []);
angular.module("safedeals.services.location")
        .factory('LocationService', function ($resource, restRoot) {
            return $resource(restRoot + '/location/:id', {'id': '@id'}, {
                'findAllLocations': {
                    'method': 'GET',
                    'url': restRoot + '/location/locations',
                    'isArray': true
                },
                'saveExcelData': {
                    'method': 'POST',
                    'url': restRoot + '/location/save_excel'

                },
                'exportAllLocations': {
                    'method': 'POST',
                    'url': restRoot + '/location/export'
                },

                'findByNameLike': {
                    'method': 'GET',
                    'url': restRoot + '/location/find/location_like',
                    'params': {
                        'name': '@name'
                    },
                    'isArray': true
                },
                'findByNameAndCityId': {
                    'method': 'GET',
                    'url': restRoot + '/location/find/name',
                    'params': {
                        'name': '@name',
                        'cityId': '@cityId'
                    },
                    'isArray': true
                }
            });
        })
        .directive('sdLocationSelect', function (LocationService, templateRoot, paginationLimit) {
            return{
                'restrict': 'AE',
                'templateUrl': templateRoot + '/directives/selection_widgets/location.html',
                'scope': {
                    'onSelect': '&onSelect',
                    'showWidget': '=',
                    'status': '=status'
                },
                'link': function (scope, element, attrs) {
                    scope.currentOffset = 0;

                    var refreshPage = function () {
                        scope.locations = LocationService.query({
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

                    scope.select = function (selectedLocation) {
                        scope.close();
                        console.log("Value of selected selectedLocation: " + selectedLocation);
                        //call the callback function
                        scope.onSelect({
                            'location': selectedLocation
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
