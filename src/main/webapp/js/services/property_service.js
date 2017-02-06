angular.module("safedeals.services.property", []);
angular.module("safedeals.services.property")
        .factory('PropertyService', function ($resource, restRoot) {
            return $resource(restRoot + '/property/:id', {'id': '@id'}, {
                'findByLocationId': {
                    'method': 'GET',
                    'url': restRoot + '/property/find/location_id',
                    'params': {
                        'locationId': '@locationId'
                    },
                    'isArray': true
                }, 'findByLocationAndCity': {
                    'method': 'GET',
                    'url': restRoot + '/property/find/location_id/city_id',
                    'params': {
                        'locationId': '@locationId',
                        'cityId': '@cityId'
                    },
                    'isArray': true
                }, 'findByMinAndMaxBudget': {
                    'method': 'GET',
                    'url': restRoot + '/property/find/min_budget/max_budget',
                    'params': {
                        'minBudget': '@minBudget',
                        'maxBudget': '@maxBudget'
                    },
                    'isArray': true
                },
                'findByNameLike': {
                    'method': 'GET',
                    'url': restRoot + '/property/find/name_like',
                    'params': {
                        'name': '@name'
                    },
                    'isArray': true
                }
            });
        });