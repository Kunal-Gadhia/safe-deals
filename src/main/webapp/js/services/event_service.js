angular.module("safedeals.services.event", []);
angular.module("safedeals.services.event")
        .factory('EventService', function ($resource, restRoot) {
            return $resource(restRoot + '/event/:id', {'id': '@id'},{
                'findByName': {
                    'method': 'GET',
                    'url': restRoot + '/event/find/name',
                    'params': {
                        'name': '@name'
                    },
                    'isArray': false
                },
                'findByDate': {
                    'method': 'GET',
                    'url': restRoot + '/event/find/date',
                    'isArray':true
                }
            });
        });


