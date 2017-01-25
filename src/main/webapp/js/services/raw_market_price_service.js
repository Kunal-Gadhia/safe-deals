angular.module("safedeals.services.raw_market_price", []);
angular.module("safedeals.services.raw_market_price")
        .factory('RawMarketPriceService', function ($resource, restRoot) {
            return $resource(restRoot + '/rawmarketprice/:id', {'id': '@id'}, {
                'checkExcelData': {
                    'method': 'POST',
                    'url': restRoot + '/rawmarketprice/read',
                    'transformResponse': function (data, headers) {
                        return {data: data};
                    }
                },
                'saveExcelData': {
                    'method': 'POST',
                    'url': restRoot + '/rawmarketprice/save_excel'
                },
                'exportAllRawMarketPrice': {
                    'method': 'POST',
                    'url': restRoot + '/rawmarketprice/export'
                },
                'findAllRawMarketPrice': {
                    'method': 'GET',
                    'url': restRoot + '/rawmarketprice/raw_market_price',
                    'isArray': true
                },
                'findByUniqueLocation': {
                    'method': 'GET',
                    'url': restRoot + '/rawmarketprice/find_by_unique_location',
                    'isArray': true
                },
                'findByUniqueLocationWithCityId': {
                    'method': 'GET',
                    'url': restRoot + '/rawmarketprice/find_by_unique_location_city',
                    'params': {
                        'cityId': '@cityId'
                    },
                    'isArray': true
                },
                'getAll': {
                    'method': 'GET',
                    'url': restRoot + '/rawmarketprice/raw_market_prices',
                    'isArray': true
                }
            });
        });
           