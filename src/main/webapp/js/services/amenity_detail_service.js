angular.module("safedeals.services.amenity_detail", []);
angular.module("safedeals.services.amenity_detail")
        .factory('AmenityDetailService', function ($resource, restRoot) {
            return $resource(restRoot + '/amenitydetail/:id', {'id': '@id'}, {
                'findByADFilter': {
                    'method': 'GET',
                    'url': restRoot + '/amenitydetail/ad_filter',
                    'params': {
                        'amenityDetailFilter': '@amenityDetailFilter'
                    },
                    'isArray': true
                },
                'exportAllLocations': {
                    'method': 'POST',
                    'url': restRoot + '/amenitydetail/export'
                },
                'findByAmenityDetailName': {
                    'method': 'GET',
                    'url': restRoot + '/amenitydetail/find/amenity_detail_name',
                    'params': {
                        'name': '@name'
                    },
                    'isArray': false
                },
                'findByAmenityIdCityId': {
                    'method': 'GET',
                    'url': restRoot + '/amenitydetail/find/amenity_id/city_id',
                    'params': {
                        'amenityId': '@amenityId',
                        'cityId': '@cityId'
                    },
                    'isArray': true
                },
                'findByNameLike': {
                    'method': 'GET',
                    'url': restRoot + '/amenitydetail/find/name_like',
                    'params': {
                        'name': '@name'
                    },
                    'isArray': true
                }
            });
        });

