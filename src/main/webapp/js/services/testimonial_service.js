/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
angular.module("safedeals.services.testimonial", []);
angular.module("safedeals.services.testimonial", [])
        .factory('TestimonialService', function ($resource, restRoot) {
            return $resource(restRoot + "/testimonial/:id", {'id': '@id'},
            {
                'findAll': {
                    'method': 'GET',
                    'url': restRoot + '/testimonial',
                    'params': {
                        'offset': '@offset'
                    },
                    'isArray': true
                },
                'findByName': {
                    'method': 'GET',
                    'url': restRoot + '/testimonial/find/name',
                    'params': {
                        'name': '@name'
                    },
                    'isArray': false
                },
                'findById': {
                    'method': 'GET',
                    'url': restRoot + '/testimonial/find',
                    'params': {
                        'id': '@id'
                    }
                }
            });
        })
        .directive('fileModel', ['$parse', function ($parse) {
                return {
                    restrict: 'A',
                    link: function (scope, element, attrs) {
                        var model = $parse(attrs.fileModel);
                        var modelSetter = model.assign;

                        element.bind('change', function () {
                            scope.$apply(function () {
                                modelSetter(scope, element[0].files[0]);
                            });
                        });
                    }
                };
            }]);
      