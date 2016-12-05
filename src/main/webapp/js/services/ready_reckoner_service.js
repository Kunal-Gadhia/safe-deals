angular.module("safedeals.services.ready_reckoner", []);
angular.module("safedeals.services.ready_reckoner")
        .factory('ReadyReckonerService', function ($resource, restRoot) {
            return $resource(restRoot + '/readyreckoner/:id', {'id': '@id'}, {
                'saveExcelData': {
                    'method': 'POST',
                    'url': restRoot + '/readyreckoner/read'
                }
            })
        })