angular.module("safedeals.states.project", [])
        .config(function ($stateProvider, templateRoot) {
            $stateProvider.state('main.project', {
                'url': '/project',
                'templateUrl': templateRoot + '/project/project.html',
                'controller': 'ProjectController'
            });
            $stateProvider.state('project_details_print', {
                'url': '/project_detail_print',
                'templateUrl': templateRoot + '/project/project_detail_print.html',
                'controller': 'ProjectPrintController'
            });
        })

        .controller('ProjectController', function ($scope, $state) {
            console.log("Project Controller");
            $scope.print = function () {
                console.log("Inside Print Function");
                $state.go('project_details_print');
            };
        })
        .controller('ProjectPrintController', function ($scope) {
            console.log("Project Print Cntroller");
        });