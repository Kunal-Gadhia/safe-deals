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
        });

