/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
angular.module("safedeals.services.road", []);
angular.module("safedeals.services.road")
        .factory('RoadService', function ($resource, restRoot) {
            return $resource(restRoot + '/road/:id', {'id': '@id'}, {               
                'findByName': {
                    'method': 'GET',
                    'url': restRoot + '/road/find_by_name',
                    'params': {
                        'name': '@name'
                    },
                    'isArray': false
                },
                'findByNameLike': {
                    'method': 'GET',
                    'url': restRoot + '/road/find/name_like',
                    'params': {
                        'name': '@name'
                    },
                    'isArray': true
                }
            });
        });
