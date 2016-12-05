angular.module("safedeals.services.salary_range", []);
angular.module("safedeals.services.salary_range")
        .factory('SalaryRangeService', function ($resource, restRoot) {
            return $resource(restRoot + '/salary_range/:id', {'id': '@id'}, {
                'findByDescriptionLike': {
                    'method': 'GET',
                    'url': restRoot + '/salary_range/find/salary_range_like',
                    'param': {
                        'description':'@description'
                },
                'isArray': true
            }
            });
        });
