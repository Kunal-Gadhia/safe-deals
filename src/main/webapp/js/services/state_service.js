angular.module("safedeals.services.state", []);
angular.module("safedeals.services.state")
        .factory('StateService', function ($resource, restRoot) {
            return $resource(restRoot + '/state/:id', {'id': '@id'}, {
                'findByCountryId': {
                    'method': 'GET',
                    'url': restRoot + '/state/find/country_id',
                    'params': {
                        'countryId': '@countryId'
                    },
                    'isArray': true
                },
                'findByName': {
                    'method': 'GET',
                    'url': restRoot + '/state/find/name',
                    'params': {
                        'name': '@name',
                        'countryId': '@countryId'
                    },
                    'isArray': true
                },
                'findByState': {
                    'method': 'GET',
                    'url': restRoot + '/state/find/state',
                    'params': {
                        'name': '@name'
                    },
                    'isArray': false
                },
                'findByNameLike': {
                    'method': 'GET',
                    'url': restRoot + '/state/find/name_like',
                    'params': {
                        'name': '@name'
                    },
                    'isArray': true
                }
            });
        })
        .directive('vsStateSelect', function (StateService, templateRoot, paginationLimit) {
            return{
                'restrict': 'AE',
                'templateUrl': templateRoot + '/directives/selection_widgets/state.html',
                'scope': {
                    'onSelect': '&onSelect',
                    'showWidget': '=',
                    'status': '=status'
                },
                'link': function (scope, element, attrs) {
                    scope.currentOffset = 0;

                    var refreshPage = function () {
                        scope.states = StateService.query({
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

                    scope.select = function (selectedState) {
                        scope.close();
                        console.log("Value of selected selectedState: " + selectedState);
                        //call the callback function
                        scope.onSelect({
                            'state': selectedState
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

        