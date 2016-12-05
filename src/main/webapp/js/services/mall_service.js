angular.module("safedeals.services.mall", []);
angular.module("safedeals.services.mall")
        .factory('MallService', function ($resource, restRoot) {
            return $resource(restRoot + '/mall/:id', {'id': '@id'}, {
                'findByLocationId': {
                    'method': 'GET',
                    'url': restRoot + '/mall/find/location_id',
                    'params': {
                        'locationId': '@locationId'
                    },
                    'isArray': true
                }
            });
        });