angular.module("safedeals.services.video", []);
angular.module("safedeals.services.video")
        .factory('VideoService', function ($resource, restRoot) {
            return $resource(restRoot + '/video/:id', {'id': '@id'}, {
                'findAllVideos': {
                    'method': 'GET',
                    'url': restRoot + '/video/videos',
                    'isArray': true
                },
                'findIntroVideo': {
                    'method': 'GET',
                    'url': restRoot + '/video/find_intro_video'
                },
                'findByProjectId': {
                    'method': 'GET',
                    'url': restRoot + '/video/find/projectId',
                    'params': {
                        'projectId': '@projectId'
                    },
                    'isArray': true
                },
                'findByPropertyId': {
                    'method': 'GET',
                    'url': restRoot + '/video/find/propertyId',
                    'params': {
                        'propertyId': '@propertyId'
                    },
                    'isArray': true
                }
            });
        });