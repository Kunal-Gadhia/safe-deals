angular.module("safedeals.states.franchise_portal", [])
        .config(function ($stateProvider, templateRoot) {
            $stateProvider
                    .state('admin.franchise_portal', {
                        'url': '/franchise_portal',
                        'templateUrl': templateRoot + '/franchise/franchise_menu.html',
                        'controller': 'FranchisePortalController'
                    });
        });


