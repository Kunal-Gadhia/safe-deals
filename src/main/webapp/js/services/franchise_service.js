angular.module("safedeals.services.franchise", []);
angular.module("safedeals.services.franchise")
        .factory('FranchiseService', function ($resource, restRoot) {
            return $resource(restRoot + '/franchise/:id', {'id': '@id'},{
                'findByName': {
                    'method': 'GET',
                    'url': restRoot + '/franchise/find/name',
                    'params': {
                        'name': '@name'
                    },
                    'isArray': false
                }
            });
        });
