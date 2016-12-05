angular.module("safedeals.services.branch", []);
angular.module("safedeals.services.branch")
        .factory('BranchService', function ($resource, restRoot) {
            return $resource(restRoot + '/branch/:id', {'id': '@id'}, {
                'findByLocationId': {
                    'method': 'GET',
                    'url': restRoot + '/branch/find/location_id',
                    'params': {
                        'locationId': '@locationId'
                    },
                    'isArray': true
                },
                'findByName': {
                    'method': 'GET',
                    'url': restRoot + '/branch/find/name',
                    'params': {
                        'name': '@name'
                    },
                    'isArray': false
                }

            });
        });