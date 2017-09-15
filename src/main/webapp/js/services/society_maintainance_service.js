/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


angular.module("safedeals.services.society_maintainance", []);
angular.module("safedeals.services.society_maintainance")
        .factory('SocietyMaintainanceService', function ($resource, restRoot) {
            return $resource(restRoot + '/society_maintainance/:id', {'id': '@id'}, {
                'findByNameLike': {
                    'method': 'GET',
                    'url': restRoot + '/society_maintainance/find/maintenance_name_like',
                    'params': {
                        'maintainanceName': '@maintainanceName'
                    },
                    'isArray': true
                }
            });
        });


