angular.module("safedeals.states.thanku", [])
        .config(function ($stateProvider, templateRoot) {
            $stateProvider.state('main.thanku', {
                'url': '/thanku',
                'templateUrl': templateRoot + '/thanku/thanku.html'
            });            
        });
