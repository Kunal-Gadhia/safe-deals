/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

angular.module("safedeals.services.image", []);
angular.module("safedeals.services.image")
        .factory('ImageService', function ($resource, restRoot) {
            return $resource(restRoot + '/image/:id', {'id': '@id'}, {
                'findByName': {
                    'method': 'GET',
                    'url': restRoot + '/image/find/name',
                    'params': {
                        'name': '@name'
                    },
                    'isArray': false
                },
                'findByProjectId': {
                    'method': 'GET',
                    'url': restRoot + '/image/find/projectId',
                    'params': {
                        'projectId': '@projectId'
                    },
                    'isArray': true
                },
                'findByPropertyId': {
                    'method': 'GET',
                    'url': restRoot + '/image/find/propertyId',
                    'params': {
                        'propertyId': '@propertyId'
                    },
                    'isArray': true
                },
                'findByLocationId': {
                    'method': 'GET',
                    'url': restRoot + '/image/find/locationId',
                    'params': {
                        'locationId': '@locationId'
                    },
                    'isArray': true
                }
            });
        });



