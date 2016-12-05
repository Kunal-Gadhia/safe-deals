angular.module("safedeals.services.location_type", []);
angular.module("safedeals.services.location_type")
        .factory('LocationTypeService', function ($resource, restRoot) {
            return $resource(restRoot + '/location_type/:id', {'id': '@id'},{
                'findAllLocationTypes':{
                    'method': 'GET',
                    'url': restRoot + '/location_type/locationtypes',
                    'isArray': true
                },
                'findByName': {
                    'method': 'GET',
                    'url': restRoot + '/location_type/find/name',
                    'params': {
                        'name': '@name'
                    },
                    'isArray': false
                },
                'findByNameLike': {
                    'method': 'GET',
                    'url': restRoot + '/location_type/find/location_type_like',
                    'params': {
                        'name': '@name'
                    },
                    'isArray': true
                }
            });
        });
