angular.module("safedeals.services.team", []);
angular.module("safedeals.services.team")
        .factory('TeamService', function ($resource, restRoot) {
            return $resource(restRoot + "/team/:id", {'id': '@id'});
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
       