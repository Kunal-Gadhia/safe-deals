/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
angular.module("safedeals.services.unit", []);
angular.module("safedeals.services.unit")
        .factory('UnitService', function ($resource, restRoot) {
            return $resource(restRoot + '/unit/:id', {'id': '@id'}, {
                'findByName': {
                    'method': 'GET',
                    'url': restRoot + '/unit/find/name',
                    'params': {
                        'name': '@name'
                    },
                    'isArray': false
                }
            });
        });





