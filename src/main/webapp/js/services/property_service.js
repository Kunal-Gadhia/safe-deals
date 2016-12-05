angular.module("safedeals.services.property", []);
angular.module("safedeals.services.property")
        .factory('PropertyService', function ($resource, restRoot) {
            return $resource(restRoot + '/property/:id', {'id': '@id'},{
                'findByLocationId':{
                    'method': 'GET',
                    'url': restRoot + '/property/find/location_id',
                    'params': {
                        'locationId': '@locationId'
                    },
                    'isArray': true
                },
                'findByPropertyCost':{
                    'method': 'GET',
                    'url': restRoot + '/property/find/property_cost',
                    'params': {
                        'propertyCost': '@propertyCost'
                    },
                    'isArray': true
                }
            });
        });