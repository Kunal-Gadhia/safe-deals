angular.module("safedeals.services.workplace_area", []);
angular.module("safedeals.services.workplace_area")
        .factory('WorkplaceAreaService', function ($resource, restRoot) {
            return $resource(restRoot + '/workplace_area/:id', {'id': '@id'},{
                'findByNameLike':{
                    'method': 'GET',
                    'url': restRoot + '/workplace_area/find/workplace_area_like',
                    'param': {
                        'name':'@name'
                    },
                    'isArray': true
                },
                'findByName': {
                    'method': 'GET',
                    'url': restRoot + '/workplace_area/find/name',
                    'params': {
                        'name': '@name'
                    },
                    'isArray': false
                }
            });
        });