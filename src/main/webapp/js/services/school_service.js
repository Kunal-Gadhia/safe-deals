angular.module("safedeals.services.school", []);
angular.module("safedeals.services.school")
        .factory('SchoolService', function ($resource, restRoot) {
            return $resource(restRoot + '/school/:id', {'id': '@id'}, {
                'findByLocationId': {
                    'method': 'GET',
                    'url': restRoot + '/school/find/location_id',
                    'params': {
                        'locationId': '@locationId'
                    },
                    'isArray': true
                }
            });
        });