angular.module("safedeals.services.workplace_category_detail", []);
angular.module("safedeals.services.workplace_category_detail")
        .factory('WorkplaceCategoryDetailService', function ($resource, restRoot) {
            return $resource(restRoot + '/workplace_category_detail/:id', {'id': '@id'});
        })


