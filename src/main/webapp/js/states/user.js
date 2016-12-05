angular.module("safedeals.states.user", [])
        .config(function ($stateProvider, templateRoot) {
            $stateProvider.state('admin.masters_user', {
                'url': '/user_master?offset',
                'templateUrl': templateRoot + '/masters/user/list.html',
                'controller': 'UserListController'
            });
            $stateProvider.state('admin.masters_user.add', {
                'url': '/add',
                'templateUrl': templateRoot + '/masters/user/form.html',
                'controller': 'UserAddController'
            });
            $stateProvider.state('admin.masters_user.edit', {
                'url': '/:userId/edit',
                'templateUrl': templateRoot + '/masters/user/form.html',
                'controller': 'UserEditController'
            });
            $stateProvider.state('admin.masters_user.delete', {
                'url': '/:userId/delete',
                'templateUrl': templateRoot + '/masters/user/delete.html',
                'controller': 'UserDeleteController'
            });
        })

        .controller('UserListController', function (UserService, $scope, $stateParams, $state, paginationLimit) {
            if (
                    $stateParams.offset === undefined ||
                    isNaN($stateParams.offset) ||
                    new Number($stateParams.offset) < 0)
            {
                $scope.currentOffset = 0;
            } else {
                $scope.currentOffset = new Number($stateParams.offset);
            }

            $scope.nextOffset = $scope.currentOffset + 5;

            $scope.nextUsers = UserService.query({
                'offset': $scope.nextOffset
            });

            $scope.users = UserService.query({
                'offset': $scope.currentOffset
            }, function (s) {
                console.log("user ", s);
            });

            $scope.nextPage = function () {
                $scope.currentOffset += paginationLimit;
                $state.go(".", {'offset': $scope.currentOffset}, {'reload': true});
            };
            $scope.previousPage = function () {
                if ($scope.currentOffset <= 0) {
                    return;
                }
                $scope.currentOffset -= paginationLimit;
                $state.go(".", {'offset': $scope.currentOffset}, {'reload': true});
            };
        })
        .controller('UserAddController', function (UserService, $scope, $stateParams, $state, paginationLimit) {

            $scope.editableUser = {};

            $scope.saveUser = function (user) {
                console.log("user", user);
                UserService.save(user, function () {
                    $state.go('admin.masters_user', null, {'reload': true});
                });
            };
            
            $scope.$watch('editableUser.username', function (username) {
                console.log("Name :" + name);
                UserService.findByUsername({'username': username}).$promise.catch(function (response) {
                    if (response.status === 500) {
                        $scope.editableUser.repeatUsername = false;
                    } else if (response.status === 404) {
                        $scope.editableUser.repeatUsername = false;
                    } else if (response.status === 400) {
                        $scope.editableUser.repeatUsername = false;
                    }
                }).then(function (user) {
                    if (user.username !== null) {
                        $scope.editableUser.repeatUsername = true;
                    }
                    ;
                });
            });
        })
        .controller('UserEditController', function (UserService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editableUser = UserService.get({'id': $stateParams.userId});

            $scope.saveUser = function (user) {
                user.$save(function () {
                    $state.go('admin.masters_user', null, {'reload': true});
                });
            };
        })
        .controller('UserDeleteController', function (UserService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editableUser = UserService.get({'id': $stateParams.userId});
            console.log("are we here?");
            $scope.deleteUser = function (user) {
                user.$delete(function () {
                    $state.go('admin.masters_user', null, {'reload': true});
                });
            };
        });
        