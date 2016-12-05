angular.module("safedeals.states.help", [])
        .config(function ($stateProvider, templateRoot) {
            $stateProvider.state('main.help', {
                'url': '/help',
                'templateUrl': templateRoot + '/help/help.html'
            })
        });



