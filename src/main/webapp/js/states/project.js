angular.module("safedeals.states.project", [])
        .config(function ($stateProvider, templateRoot) {
            $stateProvider.state('main.project', {
                'url': '/project',
                'templateUrl': templateRoot + '/project/project.html'
//                'controller': 'ProjectController'
            });
        });
        
//        .controller('ProjectController', function ($scope) {
//            $scope;
//        });