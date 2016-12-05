angular.module("safedeals.services.raw_ready_reckoner", []);
angular.module("safedeals.services.raw_ready_reckoner")
        .factory('RawReadyReckonerService', function ($resource, restRoot) {
            return $resource(restRoot + '/rawreadyreckoner/:id', {'id': '@id'}, {
                'saveExcelData': {
                    'method': 'POST',
                    'url': restRoot + '/rawreadyreckoner/read'
                },
                'findByUniqueLocation': {
                    'method': 'GET',
                    'url': restRoot + '/rawreadyreckoner/find_by_unique_location',
                    'isArray': true
                },
                'getAll': {
                    'method': 'GET',
                    'url': restRoot + '/rawreadyreckoner/raw_ready_reckoners',
                    'isArray': true
                }
            })
        })
           