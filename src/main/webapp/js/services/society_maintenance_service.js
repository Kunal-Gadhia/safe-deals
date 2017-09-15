/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


angular.module("safedeals.services.society_maintenance", []);
angular.module("safedeals.services.society_maintenance")
        .factory('SocietyMaintenanceService', function ($resource, restRoot) {
            return $resource(restRoot + '/society_maintenance/:id', {'id': '@id'}, {
                'findByNameLike': {
                    'method': 'GET',
                    'url': restRoot + '/society_maintenance/find/maintenance_name_like',
                    'params': {
                        'maintenanceName': '@maintenanceName'
                    },
                    'isArray': true
                }
            });
        });


