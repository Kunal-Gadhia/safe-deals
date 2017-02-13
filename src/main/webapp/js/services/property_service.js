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
                },
                'findByLocationAndCity': {
                    'method': 'GET',
                    'url': restRoot + '/property/find/location_id/city_id/property_size',
                    'params': {
                        'locationId': '@locationId',
                        'cityId': '@cityId',
                        'propertySize': '@propertySize'
                    },
                    'isArray': true
                },
                'findByMinAndMaxBudget': {
                    'method': 'GET',
                    'url': restRoot + '/property/find/min_budget/max_budget/property_size',
                    'params': {
                        'minBudget': '@minBudget',
                        'maxBudget': '@maxBudget',
                        'propertySize': '@propertySize'
                    },
                    'isArray': true
                },
                'findByFilters': {
                    'method': 'GET',
                    'url': restRoot + '/property/find/filter',
                    'params': {
                        'cityId': '@cityId',
                        'locationId': '@locationId',
                        'propertySize': '@propertySize',
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