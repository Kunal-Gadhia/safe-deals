/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
angular.module("safedeals.services.inventory_head", []);
angular.module("safedeals.services.inventory_head")
        .factory('InventoryHeadService', function ($resource, restRoot) {
            return $resource(restRoot + '/inventory_head/:id', {'id': '@id'});
        });




