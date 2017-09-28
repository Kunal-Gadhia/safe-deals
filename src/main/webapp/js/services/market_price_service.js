angular.module("safedeals.services.market_price", []);
angular.module("safedeals.services.market_price")
        .factory('MarketPriceService', function ($resource, restRoot) {
            return $resource(restRoot + '/market_price/:id', {'id': '@id'}, {
                'findByRequirement': {
                    'method': 'GET',
                    'url': restRoot + '/market_price/find_by_requirement',
                    'params': {
                        'cityId': '@cityId',
                        'locationMinBudget': '@locationMinBudget',
                        'locationMaxBudget': '@locationMaxBudget'
                    },
                    'isArray': true
                },
                'findByLocationAndYear': {
                    'method': 'GET',
                    'url': restRoot + '/market_price/find_by_location/year',
                    'params': {
                        'locationId': '@locationId',
                        'year': '@year'
                    },
                    'isArray': true
                },
                'findByLocation': {
                    'method': 'GET',
                    'url': restRoot + '/market_price/find_by_location',
                    'params': {
                        'locationId': '@locationId'
                    },
                    'isArray': true
                },
                'checkExcelData': {
                    'method': 'POST',
                    'url': restRoot + '/market_price/read',
                    'transformResponse': function (data, headers) {
                        return {data: data};
                    }
                },
                'saveExcelData': {
                    'method': 'POST',
                    'url': restRoot + '/market_price/save_excel'

                },
                'findByUniqueLocation': {
                    'method': 'GET',
                    'url': restRoot + '/market_price/find_by_unique_location',
                    'isArray': true
                },
                'getAll': {
                    'method': 'GET',
                    'url': restRoot + '/market_price/market_prices',
                    'isArray': true
                }
            });
        });

