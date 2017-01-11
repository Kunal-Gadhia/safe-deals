angular.module("safedeals.services.property_type", []);
angular.module("safedeals.services.property_type")
        .factory('PropertyTypeService', function ($resource, restRoot) {
            return $resource(restRoot + '/property_type/:id', {'id': '@id'}, {
                'findByNumberOfRooms':{
                    'method': 'GET',
                    'url': restRoot + '/property_type/find/number_of_rooms',
                    'params': {
                        'numberOfRooms': '@numberOfRooms'
                    }
                },
                'findByNumberOfBhkLike':{
                    'method': 'GET',
                    'url': restRoot + '/property_type/find/number_of_bhk_like',
                    'params': {
                        'numberOfBhkLike': '@numberOfBhkLike'
                    },
                    'isArray': true
                }
            });
        });