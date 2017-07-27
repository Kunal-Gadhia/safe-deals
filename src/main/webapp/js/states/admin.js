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
//            $stateProvider.state('admin.alert', {
//                'url': '/alert',
//                'templateUrl': templateRoot + '/alert.html',
//                'controller': 'AlertController'
//            });
        })
        .controller('AdminController', function ($scope, UserService) {
            $scope.unapprovedUser = [];
            UserService.findUnapprovedUser(function (data) {
                angular.forEach(data, function (user) {
                    $scope.unapprovedUser.push(user);
                });
                $scope.countOfUser = $scope.unapprovedUser.length;
            });

//            $scope.countUnapprovedUser = UserService.countUnapprovedUser();
//            console.log(" $scope.countUnapprovedUser", $scope.countUnapprovedUser);

//            $scope.unapprovedUserList = UserService.findUnapprovedUser();
//            console.log(" $scope.countUnapprovedUser", $scope.unapprovedUserList);

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
//        .controller('AlertController', function (UserService, $scope, $state) {
//            UserService.findUnapprovedUser(function (data) {
//                $scope.unapprovedUserList = data;
//            });
////            $scope.unapprovedUserList = UserService.findUnapprovedUser();
//        });
