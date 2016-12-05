angular.module("safedeals.services.agency", []);
angular.module("safedeals.services.agency")
        .factory('AgencyService', function ($resource, restRoot) {
            return $resource(restRoot + '/agency/:id', {'id': '@id'},{
                'findAllAgencies':{
                    'method': 'GET',
                    'url': restRoot + '/agency/agencies',
                    'isArray': true
                },
                'findByName': {
                    'method': 'GET',
                    'url': restRoot + '/agency/find/name',
                    'params': {
                        'name': '@name'
                    },
                    'isArray': false
                }
            });
        });