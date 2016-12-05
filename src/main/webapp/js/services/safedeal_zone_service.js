angular.module("safedeals.services.safedeal_zone", []);
angular.module("safedeals.services.safedeal_zone")
        .factory('SafedealZoneService', function ($resource, restRoot) {
            return $resource(restRoot + '/safedeal_zone/:id', {'id': '@id'},{
                'findAllSafedealZones':{
                    'method': 'GET',
                    'url': restRoot + '/safedeal_zone/safedealzones',
                    'isArray': true
                },
                'findByNameLike': {
                    'method': 'GET',
                    'url': restRoot + '/safedeal_zone/find/safedealzones_like',
                    'params': {
                        'name': '@name'
                    },
                    'isArray': true
                },
                'findByName': {
                    'method': 'GET',
                    'url': restRoot + '/safedeal_zone/find/name',
                    'params': {
                        'name': '@name'
                    },
                    'isArray': false
                }
            });
        });
