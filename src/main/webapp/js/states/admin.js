angular.module("safedeals.states.admin", [])
        .config(function ($stateProvider, templateRoot) {
            $stateProvider.state('admin', {
                'url': '/admin',
                'templateUrl': templateRoot + '/admin.html',
                'controller': 'AdminController'
            });
            $stateProvider.state('admin.masters', {
                'url': '/masters',
                'templateUrl': templateRoot + '/masters/menu.html'
            });
            $stateProvider.state('admin.logout', {
                'url': '/logout',
                'templateUrl': templateRoot + '/logout.html',
                'controller': 'LogoutController'
            });
        })
        .controller('AdminController', function ($scope, UserService) {
            $scope.unapprovedUser = [];
            UserService.findUnapprovedUser(function (data) {
                angular.forEach(data, function (user) {
                    $scope.unapprovedUser.push(user);
                });
                $scope.countOfUser = $scope.unapprovedUser.length;
            });
        })
        .controller('LogoutController', function (UserService, $scope, $state) {
            console.log("Coming to logout Controller??");
            $scope.logout = function () {
                UserService.logout({}, function () {
                    $state.go("login", {
                        'message': 'Logged Out Successfully!'
                    });
                });
            };
        });
