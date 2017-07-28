angular.module("safedeals.states.builder_portal", [])
        .config(function ($stateProvider, templateRoot) {
            $stateProvider.state('admin.builder_portal', {
                'url': '/builder_portal',
                'templateUrl': templateRoot + '/builderanddeveloper/builder_menu.html',
                'controller': 'BuilderPortalController'
            });
        })

        .controller('BuilderPortalController', function () {
           
        });



