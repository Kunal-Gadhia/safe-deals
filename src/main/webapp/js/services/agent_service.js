angular.module("safedeals.services.agent", []);
angular.module("safedeals.services.agent")
        .factory('AgentService', function ($resource, restRoot) {
            return $resource(restRoot + '/agent/:id', {'id': '@id'},{
                'findByName': {
                    'method': 'GET',
                    'url': restRoot + '/agent/find/name',
                    'params': {
                        'name': '@name'
                    },
                    'isArray': false
                }
            });
        });


