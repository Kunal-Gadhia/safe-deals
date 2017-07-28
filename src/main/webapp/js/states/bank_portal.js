angular.module("safedeals.states.bank_portal", [])
        .config(function ($stateProvider, templateRoot) {
            $stateProvider
                    .state('admin.bank_portal', {
                        'url': '/bank_portal',
                        'templateUrl': templateRoot + '/bank/bank_menu.html',
                        'controller': 'BankPortalController'
                    });
        })
        
        .controller('BankPortalController', function () {

        });


