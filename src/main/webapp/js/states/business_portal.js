angular.module("safedeals.states.business_portal", [])
        .config(function ($stateProvider, templateRoot) {
            $stateProvider.state('admin.business_portal', {
                'url': '/business_portal?offset',
                'templateUrl': templateRoot + '/businessassociate/business_menu.html',
                'controller': 'BusinessPortalController'
            });
        })

        .controller('BusinessPortalController', function () {
           
        });

