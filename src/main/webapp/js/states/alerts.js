angular.module("safedeals.states.alerts", [])
        .config(function ($stateProvider, templateRoot) {
            $stateProvider.state('admin.alerts', {
                'url': '/alerts',
                'templateUrl': templateRoot + '/alert/alert.html',
                'controller': 'AlertsListController'
            });
            $stateProvider.state('admin.alerts.approved', {
                'url': '/:userId/approved',
                'templateUrl': templateRoot + '/alert/approved.html',
                'controller': 'AlertsApprovedController'
            });
            $stateProvider.state('admin.alerts.disapproved', {
                'url': '/:userId/disapproved',
                'templateUrl': templateRoot + '/alert/disapproved.html',
                'controller': 'AlertsDisapprovedController'
            });
        })

        .controller('AlertsListController', function (UserService, CityService, $scope) {
            $scope.unapprovedUserList = [];
            UserService.findUnapprovedUser(function (data) {
                angular.forEach(data, function (user) {
                    $scope.unapprovedUserList.push(user);

                    user.city = CityService.get({
                        'id': user.cityId
                    });
                });

            });
        })
        .controller('AlertsApprovedController', function (UserService, MailService, $scope, $stateParams, $state, paginationLimit) {
            console.log("User Id in approved :%O", $stateParams.userId);
            UserService.get({
                'id': $stateParams.userId
            }, function (user) {
                $scope.userObject = user;
            });

            $scope.approveUser = function (user) {
                console.log("User ID :%O", user);
                user.approved = true;
                user.$save(function () {
                    MailService.sendApprovedMail({
                        'mailId': user.username
                    });
                    $state.go("admin.alerts", null, {'reload': true});
                });
            };
        })

        .controller('AlertsDisapprovedController', function (UserService, MailService, $scope, $stateParams, $state, paginationLimit) {
            console.log("User Id in disapproved :%O", $stateParams.userId);
            UserService.get({
                'id': $stateParams.userId
            }, function (user) {
                $scope.userObject = user;
            });

            $scope.disapproveUser = function (user) {
                console.log("User ID :%O", user);
                user.$delete(function () {
                    MailService.sendRejectionMail({
                        'mailId': user.username
                    });
                    $state.go("admin.alerts", null, {'reload': true});
                });
            };
        });







