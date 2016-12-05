angular.module("safedeals.services.guidelines", []);
angular.module("safedeals.services.guidelines")
        .factory('GuidelinesService', function ($resource, restRoot) {
            return $resource(restRoot + '/guidelines',{},{
                'sendGuidelinesReportByMail':{
                        'method': 'POST',
                        'url': restRoot + '/guidelines/report',
                        'params': {
                            'name': '@name',
                            'email' : '@email',
                            'cashInHand' : '@cashInHand',
                            'loanEligibility' : '@loanEligibility',
                            'grossBudget' : '@grossBudget',
                            'emiEligibility' : '@emiEligibility',
                            'eligiblePropertyValue' : '@eligiblePropertyValue'
                        }
                    }
            });
        });