angular.module("safedeals.services.country", []);
angular.module("safedeals.services.country")
        .factory('CountryService', function ($resource, restRoot) {
            return $resource(restRoot + '/country/:id', {'id': '@id'});
        })
        .directive('vsCountrySelect', function (CountryService, templateRoot, paginationLimit) {
            return{
                'restrict': 'AE',
                'templateUrl': templateRoot + '/directives/selection_widgets/country.html',
                'scope': {
                    'onSelect': '&onSelect',
                    'showWidget': '=',
                    'status': '=status'
                },
                'link': function (scope, element, attrs) {
                    scope.currentOffset = 0;

                    var refreshPage = function () {
                        scope.countries = CountryService.query({
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
                    
                    scope.select = function (selectedCountry) {
                        scope.close();
                        console.log("Value of selected selectedCountry: " + selectedCountry);
                        //call the callback function
                        scope.onSelect({
                            'country': selectedCountry
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
