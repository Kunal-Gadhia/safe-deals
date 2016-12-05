angular.module("safedeals.states.final_deal", [])
        .config(function ($stateProvider, templateRoot) {
            $stateProvider.state('main.final', {
                'url': '/final_deal',
                'templateUrl': templateRoot + '/final/final_deal.html'
            });            
        });

