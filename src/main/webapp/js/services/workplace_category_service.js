angular.module("safedeals.services.workplace_category", []);
angular.module("safedeals.services.workplace_category")
        .factory('WorkplaceCategoryService', function ($resource, restRoot) {
            return $resource(restRoot + '/workplace_category/:id', {'id': '@id'},{
                'findByNameLike':{
                    'method': 'GET',
                    'url': restRoot + '/workplace_category/find/workplace_category_like',
                    'param':{
                        'name':'@name'
                    },
                    'isArray': true
                },
                'findByName': {
                    'method': 'GET',
                    'url': restRoot + '/workplace_category/find/name',
                    'params': {
                        'name': '@name'
                    },
                    'isArray': false
                }
                
            });
        });