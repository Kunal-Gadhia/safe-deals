angular.module("safedeals.services.inventory", []);
angular.module("safedeals.services.inventory")
        .factory('InventoryService', function ($resource, restRoot) {
            return $resource(restRoot + '/inventory/:id', {'id': '@id'});
        });


