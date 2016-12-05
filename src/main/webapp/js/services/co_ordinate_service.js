angular.module("safedeals.services.co_ordinate", []);
angular.module("safedeals.services.co_ordinate")
        .factory('CoordinateService', function ($resource, restRoot) {
            return $resource(restRoot + '/coordinate/:id', {'id': '@id'}, {
                'findByLocationId': {
                    'method': 'GET',
                    'url': restRoot + '/coordinate/find/location_id',
                    'params': {
                        'locationId': '@locationId'
                    },
                    'isArray': true
                }

            });
        });