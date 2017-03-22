angular.module("safedeals.services.price_range", []);
angular.module("safedeals.services.price_range")
        .factory('PriceRangeService', function ($resource, restRoot) {
            return $resource(restRoot + '/pricerange/:id', {'id': '@id'}, {
                'findByMinBudget': {
                    'method': 'GET',
                    'url': restRoot + '/pricerange/find_by_minbudget',
                    'isArray': true
                },
                'findAllList': {
                    'method': 'GET',
                    'url': restRoot + '/pricerange/find_all',
                    'isArray': true
                }
            });
        });