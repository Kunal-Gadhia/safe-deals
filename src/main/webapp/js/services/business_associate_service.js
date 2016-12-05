angular.module("safedeals.services.business_associate", []);
angular.module("safedeals.services.business_associate")
        .factory('BusinessAssociateService', function ($resource, restRoot) {
            return $resource(restRoot + '/business_associate/:id', {'id': '@id'},{
                'findByName': {
                    'method': 'GET',
                    'url': restRoot + '/business_associate/find/name',
                    'params': {
                        'name': '@name'
                    },
                    'isArray': false
                },
            });
        })


