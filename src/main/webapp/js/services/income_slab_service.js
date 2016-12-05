angular.module("safedeals.services.income_slab", []);
angular.module("safedeals.services.income_slab")
        .factory('IncomeSlabService', function ($resource, restRoot) {
            return $resource(restRoot + '/incomeslab/:id', {'id': '@id'},{
                'findByBankId': {
                    'method': 'GET',
                    'url': restRoot + '/incomeslab/find/bank_id',
                    'params': {
                        'bankId': '@bankId'
                    },
                    'isArray': true
                }
                
            })
        })