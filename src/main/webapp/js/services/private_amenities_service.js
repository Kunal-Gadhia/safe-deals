/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

angular.module("safedeals.services.private_amenities", []);
angular.module("safedeals.services.private_amenities")
        .factory('PrivateAmenitiesService', function ($resource, restRoot) {
            return $resource(restRoot + '/private_amenities/:id', {'id': '@id'}, {
                'findByName': {
                    'method': 'GET',
                    'url': restRoot + '/private_amenities/find/name',
                    'params': {
                        'name': '@name'
                    },
                    'isArray': false
                }
            });
        });





