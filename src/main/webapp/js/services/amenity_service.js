angular.module("safedeals.services.amenity", []);
angular.module("safedeals.services.amenity")
        .factory('AmenityService', function ($resource, restRoot) {
            return $resource(restRoot + '/amenity/:id', {'id': '@id'}, {
                'findAllAmenities': {
                    'method': 'GET',
                    'url': restRoot + '/amenity/amenities',
                    'isArray': true
                },
                'findByNameLike': {
                    'method': 'GET',
                    'url': restRoot + '/amenity/find/amenity_like',
                    'params': {
                        'name': '@name'
                    },
                    'isArray': true
                },
                'exportAllAmenities': {
                    'method': 'POST',
                    'url': restRoot + '/amenity/export'
                },
                'findByAmenityName': {
                    'method': 'GET',
                    'url': restRoot + '/amenity/find/amenity_name',
                    'params': {
                        'name': '@name'
                    },
                    'isArray': false
                },
                'findByAmenityCode': {
                    'method': 'GET',
                    'url': restRoot + '/amenity//find_by_amenity_code',
                    'params': {
                        'amenityCodeId': '@amenityCodeId'
                    },
                    'isArray': true
                }
            });
        });

