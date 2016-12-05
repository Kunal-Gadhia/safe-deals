angular.module("safedeals.states.faqs", [])
        .config(function ($stateProvider, templateRoot) {
            $stateProvider.state('main.faqs', {
                'url': '/faqs',
                'templateUrl': templateRoot + '/faqs/faqs.html'
            });            
        });

