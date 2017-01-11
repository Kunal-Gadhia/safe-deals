angular.module("safedeals.services.transportation", []);
angular.module("safedeals.services.transportation")
        .factory('TransportationService', function ($resource, restRoot) {
            return $resource(restRoot + '/transportation/:id', {'id': '@id'}, {
                'findByName': {
                    'method': 'GET',
                    'url': restRoot + '/transportation/find/name',
                    'params': {
                        'name': '@name'
                    },
                    'isArray': false
                },
                'findByNameLike': {
                    'method': 'GET',
                    'url': restRoot + '/transportation/find/name_like',
                    'params': {
                        'name': '@name'
                    },
                    'isArray': true
                }
            });
        });


