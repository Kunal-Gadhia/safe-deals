angular.module("safedeals.services.landmark", []);
angular.module("safedeals.services.landmark")
        .factory('LandmarkService', function ($resource, restRoot) {
            return $resource(restRoot + '/landmark/:id', {'id': '@id'});
        });


