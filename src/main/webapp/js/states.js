angular.module("safedeals.states", ['ngAnimate', 'ui.bootstrap'])
        .config(function ($stateProvider, templateRoot, $sceDelegateProvider) {
            $stateProvider.state('login', {
                'url': '/login',
                'templateUrl': templateRoot + '/login.html',
                'controller': 'LoginController'
            });
            $stateProvider.state('portalLogin', {
                'url': '/portalLogin',
                'templateUrl': templateRoot + '/login.html',
                'controller': 'LoginController'
            });
//            $stateProvider.state('main.logout', {
//                'url': '/logout',
//                'templateUrl': templateRoot + '/logout.html',
//                'controller': 'LogoutController'
//            });
            $stateProvider.state('main', {
                'url': '/main',
                'templateUrl': templateRoot + '/main.html',
                'controller': 'MainController'
            });
            $stateProvider.state('main.masters', {
                'url': '/masters',
                'templateUrl': templateRoot + '/masters/menu.html'
            });
            $sceDelegateProvider.resourceUrlWhitelist([
                'https://www.youtube.com/embed/**'
            ]);
        })
        .controller('MainController', function ($scope, VideoService, $state, $window, $location, $anchorScroll) {
            $scope.introVideo = VideoService.findIntroVideo();
            var parrentDiv = $('#parrentDiv');
            parrentDiv.removeClass();
            parrentDiv.addClass('bg-city-spcl');

            $scope.gotoTop = function () {
                $location.hash('top');
                $anchorScroll();
            };
        })
        .controller('LoginController', function ($scope, $state, $stateParams, $timeout, UserService, AuthFactory) {
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
                }, function (data) {
                    console.log("Data :%O", data);
                    AuthFactory.refresh();
                    UserService.findByUsername({
                        'username': data.username
                    }, function (data) {
                        if (data.userType === "UT_GUEST") {
                            $state.go("main.intro.intro_tagline", {reload: 'true'});
                        } else if (data.userType === "UT_SUPER_ADMIN") {
                            $state.go("admin.masters", {reload: 'true'});
                        } else if (data.userType === "UT_BUILDER") {
                            $state.go("admin.masters", {reload: 'true'});
                        } else if (data.userType === "UT_BUSINESS_ASSOCIATE") {
                            $state.go("admin.masters", {reload: 'true'});
                        } else if (data.userType === "UT_BANK") {
                            $state.go("admin.masters", {reload: 'true'});
                        } else if (data.userType === "UT_FRANCHISE") {
                            $state.go("admin.masters", {reload: 'true'});
                        }

                    });
//                    if (data.username === "guest") {
//                        $state.go("main.intro.intro_tagline", {reload: 'true'});
//                    } else {
//                        $state.go("admin.masters", {reload: 'true'});
//                    }
                }, function () {
                    $scope.error = "Login Failed. Invalid Credentials.";
                });
            };
            $scope.guestLogin = function () {
                $scope.login("guest", "guest");
            };

            $scope.newUser = {};
            $scope.saveNewUser = function (newUserInfo) {
                console.log("new user info :%O", newUserInfo);
            };

//            $scope.$watch('$scope.userType', function (userType) {
//                console.log("Detecting Change with value :%O", userType);
//            });

            function googleTranslateElementInit() {
                console.log("Into Translator");
                new google.translate.TranslateElement({pageLanguage: 'en'}, 'google_translate_element');
            }
            ;
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



