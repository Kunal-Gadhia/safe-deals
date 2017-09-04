/* 
 * To change this license header, choose License Headers in Property Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
angular.module("safedeals.services.property_category", []);
angular.module("safedeals.services.property_category")
        .factory('PropertyCategoryService', function ($resource, restRoot) {
            return $resource(restRoot + '/property_category/:id', {'id': '@id'}, {
                'findByPropertyCategoryLike': {
                    'method': 'GET',
                    'url': restRoot + '/property_category/find/property_category_like',
                    'params': {
                        'category': '@category'
                    },
                    'isArray': true
                }
            });
        });





