/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
angular.module("safedeals.states.portal", [])
        .config(function ($stateProvider, templateRoot) {
            $stateProvider
                    .state('portal', {
                        'url': '/portal',
                        'templateUrl': templateRoot + '/portal.html'
                    })
                    .state('portal.my_profile', {
                        'url': '/my_profile',
                        'templateUrl': templateRoot + '/portal/my_profile.html'
                    })
                    .state('portal.contct_us', {
                        'url': '/contact_us',
                        'templateUrl': templateRoot + '/portal/contact_us.html'
                    })
                    .state('portal.my_properties', {
                        'url': '/my_properties',
                        'templateUrl': templateRoot + '/portal/my_properties.html'
                    });

            $stateProvider.state('portal_bank', {
                'url': '/portal_bank',
                'templateUrl': templateRoot + '/portal/portal_bank.html'
            });

            $stateProvider.state('portal_franchise', {
                'url': '/portal_franchise',
                'templateUrl': templateRoot + '/portal/portal_franchise.html'
            });

            $stateProvider.state('portal_businessassociate', {
                'url': '/portal_businessassociate',
                'templateUrl': templateRoot + '/portal/portal_businessassociate.html'
            });

            $stateProvider.state('portal_builderanddeveloper', {
                'url': '/portal_builderanddeveloper',
                'templateUrl': templateRoot + '/portal/portal_builderanddeveloper.html'
            });
        });

