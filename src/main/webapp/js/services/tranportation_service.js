angular.module("safedeals.services.tranportation", []);
angular.module("safedeals.services.tranportation")
        .factory('TranportationService', function ($resource, restRoot) {
            return $resource(restRoot + '/tranportation/:id', {'id': '@id'}, {
                'findByLocationId': {
                    'method': 'GET',
                    'url': restRoot + '/tranportation/find/location_id',
                    'params': {
                        'locationId': '@locationId'
                    },
                    'isArray': true
                }
            });
        });


