angular.module("safedeals.services.amenity_code", []);
angular.module("safedeals.services.amenity_code")
        .factory('AmenityCodeService', function ($resource, restRoot) {
            return $resource(restRoot + '/amenity_code/:id', {'id': '@id'},{
                'findByName': {
                    'method': 'GET',
                    'url': restRoot + '/amenity_code/find_by_name',
                    'params': {
                        'name': '@name'
                    },
                    'isArray': false
                },
                'findByTabName': {
                    'method': 'GET',
                    'url': restRoot + '/amenity_code/find_by_tab_name',
                    'params': {
                        'name': '@name'
                    },
                    'isArray': true
                },
                'findAmenities': {
                    'method': 'GET',
                    'url': restRoot + '/amenity_code/find_amenities',
                    'isArray': true
                },
                'findWorkplaces': {
                    'method': 'GET',
                    'url': restRoot + '/amenity_code/find_workplaces',                    
                    'isArray': true
                },
                'findProjects': {
                    'method': 'GET',
                    'url': restRoot + '/amenity_code/find_projects',                    
                    'isArray': true
                },
                'findProperties': {
                    'method': 'GET',
                    'url': restRoot + '/amenity_code/find_properties',                    
                    'isArray': true
                },
                'findByNameLike': {
                    'method': 'GET',
                    'url': restRoot + '/amenity_code/find/name_like',
                    'params': {
                        'name': '@name'
                    },
                    'isArray': true
                }
            });
        });


