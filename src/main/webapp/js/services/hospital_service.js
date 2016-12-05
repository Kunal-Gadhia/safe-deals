angular.module("safedeals.services.hospital", []);
angular.module("safedeals.services.hospital")
        .factory('HospitalService', function ($resource, restRoot) {
            return $resource(restRoot + '/hospital/:id', {'id': '@id'}, {
                'findByLocationId': {
                    'method': 'GET',
                    'url': restRoot + '/hospital/find/location_id',
                    'params': {
                        'locationId': '@locationId'
                    },
                    'isArray': true
                },
                'findByName': {
                    'method': 'GET',
                    'url': restRoot + '/hospital/find/name',
                    'params': {
                        'name': '@name'
                    },
                    'isArray': false
                }
            });
        })
        .filter('positive', function () {
            return function (input) {
                if (!input) {
                    return 0;
                }

                return Math.abs(input);
            };
        });




