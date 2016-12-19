angular.module("safedeals.states", ['ngAnimate', 'ui.bootstrap'])
        .config(function ($stateProvider, templateRoot, $sceDelegateProvider) {
            $stateProvider.state('login', {
                'url': '/login',
                'templateUrl': templateRoot + '/login.html',
                'controller': 'LoginController'
            });
//            $stateProvider.state('main.logout', {
//                'url': '/logout',
//                'templateUrl': templateRoot + '/logout.html',
//                'controller': 'LogoutController'
//            });
//            $stateProvider.state('main', {
//                'url': '/main',
//                'templateUrl': templateRoot + '/main.html',
//                'controller': 'MainController'
//            });
            $stateProvider.state('main.masters', {
                'url': '/masters',
                'templateUrl': templateRoot + '/masters/menu.html'
            });
            $sceDelegateProvider.resourceUrlWhitelist([
                'https://www.youtube.com/embed/**'
            ]);
        })
//        .controller('MainController', function ($scope, VideoService, $state, $window) {
//            $scope.introVideo = VideoService.findIntroVideo();
//            var parrentDiv = $('#parrentDiv');
//            parrentDiv.removeClass();
//            parrentDiv.addClass('bg-city-spcl');
//        })
        .controller('LoginController', function ($scope, $state, $stateParams, $timeout, UserService) {
            $scope.username = $stateParams.username;
            $scope.message = $stateParams.message;
            $scope.error = $stateParams.error;
            $timeout(function () {
                $scope.message = false;
            }, 3000);
            $scope.login = function (username, password) {
                UserService.login({
                    'username': username,
                    'password': password
                }, function () {
                    if (username === "guest" && password === "guest") {
                        $state.go("corporate_site.home", {reload: 'true'});
                    } else {
                        $state.go("admin.masters");
                    }
                }, function () {
                    $scope.error = "Login Failed. Invalid Credentials.";
                });
            };
            $scope.guestLogin = function () {
                $scope.login("guest", "guest");
            };
        });
//        .controller('LogoutController', function (UserService, $scope, $state) {
//            $scope.logout = function () {
//                UserService.logout({}, function () {
//                    $state.go("login", {
//                        'message': 'Logged Out Successfully!'
//                    });
//                });
//            };
//        });



