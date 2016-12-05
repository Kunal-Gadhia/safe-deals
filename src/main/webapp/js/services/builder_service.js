angular.module("safedeals.services.builder", []);
angular.module("safedeals.services.builder")
        .factory('BuilderService', function ($resource, restRoot) {
            return $resource(restRoot + '/builder/:id', {'id': '@id'},{
                'findByName': {
                    'method': 'GET',
                    'url': restRoot + '/builder/find/name',
                    'params': {
                        'name': '@name'
                    },
                    'isArray': false
                }
            });
        });



