/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
angular.module("safedeals.services.project_category", []);
angular.module("safedeals.services.project_category")
        .factory('ProjectCategoryService', function ($resource, restRoot) {
            return $resource(restRoot + '/project_category/:id', {'id': '@id'},{
                'findByProjectCategoryLike': {
                    'method': 'GET',
                    'url': restRoot + '/project_category/find/project_category_like',
                    'params': {
                        'category': '@category'
                    },
                    'isArray': true
                }
            });
        });





