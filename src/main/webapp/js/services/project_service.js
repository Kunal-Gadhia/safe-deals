angular.module("safedeals.services.project", []);
angular.module("safedeals.services.project")
        .factory('ProjectService', function ($resource, restRoot) {
            return $resource(restRoot + '/project/:id', {'id': '@id'}, {
                'findByLocationId': {
                    'method': 'GET',
                    'url': restRoot + '/project/find/location_id',
                    'params': {
                        'locationId': '@locationId'
                    },
                    'isArray': true
                },
                'findByProjectCost': {
                    'method': 'GET',
                    'url': restRoot + '/project/find/project_cost',
                    'params': {
                        'projectCost': '@projectCost'
                    },
                    'isArray': true
                }
            });
        });