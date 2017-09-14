/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
angular.module("safedeals.services.inventory_detail", []);
angular.module("safedeals.services.inventory_detail")
        .factory('InventoryDetailService', function ($resource, restRoot) {
            return $resource(restRoot + '/inventory_detail/:id', {'id': '@id'},{
                'findByInventoryHeadId': {
                    'method': 'GET',
                    'url': restRoot + '/inventory_detail/find/inventoryHeadId',
                    'params': {
                        'inventoryHeadId': '@inventoryHeadId'
                    },
                    'isArray': true
                }
            });
        });


