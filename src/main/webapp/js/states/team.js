angular.module("safedeals.states.team", [])
        .config(function ($stateProvider, templateRoot) {
            $stateProvider
                    .state('admin.team', {
                        'url': '/team?offset',
                        'templateUrl': templateRoot + '/masters/team/list.html',
                        'controller': 'TeamListController'
                    })
                    .state('admin.team.add', {
                        'url': '/add',
                        'templateUrl': templateRoot + '/masters/team/form.html',
                        'controller': 'TeamAddController'
                    })
                    .state('admin.team.edit', {
                        'url': '/:teamId/edit',
                        'templateUrl': templateRoot + '/masters/team/form.html',
                        'controller': 'TeamEditController'
                    })
                    .state('admin.team.delete', {
                        'url': '/:teamId/delete',
                        'templateUrl': templateRoot + '/masters/team/delete.html',
                        'controller': 'TeamDeleteController'
                    })
                    .state('admin.team.photo', {
                        'url': '/:teamId/photo',
                        'templateUrl': templateRoot + '/masters/team/photo.html',
                        'controller': 'TeamPhotoController'
                    })
                    .state('admin.team.view', {
                        'url': '/:teamId/view',
                        'templateUrl': templateRoot + '/masters/team/view.html',
                        'controller': 'TeamViewController'
                    });
        })
        .controller('TeamViewController', function ($scope, $stateParams, $state) {

            $scope.team = {};
            $scope.team.id = $stateParams.teamId;
            $scope.goBack = function () {
                $state.go('admin.team', {}, {'reload': true});
            };

            console.log("THIS IS TEAM ID", $stateParams.teamId);
        })
        .controller('TeamPhotoController', function (restRoot, FileUploader, $scope, $stateParams, $state) {
            $scope.enableSaveButton = false;
            $scope.goBack = function () {
                $state.go('admin.team', {}, {'reload': true});
            };
            var uploader = $scope.fileUploader = new FileUploader({
                url: restRoot + '/team/' + $stateParams.teamId + '/attachment',
                autoUpload: true,
                alias: 'attachment'
            });
            uploader.onBeforeUploadItem = function (item) {
                $scope.uploadInProgress = true;
                $scope.uploadSuccess = false;
                console.log("before upload item:", item);
                console.log("uploader", uploader);
            };
            uploader.onErrorItem = function ($scope) {
                $scope.uploadFailed = true;
                $scope.uploadInProgress = false;
                $scope.uploadSuccess = false;
//                    $state.go('.', {}, {'reload': true});
                console.log("upload error");
//                $scope.refreshRawMarketPrice();
            };
            uploader.onCompleteItem = function ($scope, response, status) {
                console.log("Status :%O", status);
                if (status === 200) {
                    console.log("Coming to 200 ??");
                    $scope.uploadInProgress = false;
                    $scope.uploadFailed = false;
                    $scope.uploadSuccess = true;
                    $scope.enableSaveButton = true;
                    console.log("In Progress :" + $scope.uploadInProgress);
                    console.log("Failed :" + $scope.uploadFailed);
                    console.log("Success :" + $scope.uploadSuccess);
                    console.log("Save Button :" + $scope.enableSaveButton);
                } else if (status === 500)
                {
                    $scope.uploadInProgress = false;
                    $scope.uploadFailed = false;
//                    $scope.uploadWarning = true;
                } else {
                    console.log("Coming to else??");
                    $scope.uploadInProgress = false;
                    $scope.uploadFailed = true;
                }

                console.log("upload completion", response);
            };
        })
        .controller('TeamListController', function (TeamService, UserService, $scope, $stateParams, $state, paginationLimit) {

            $scope.length = false;
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

            $scope.nextTeams = TeamService.query({
                'offset': $scope.nextOffset
            });
            
//           $scope.teams = TeamService.query({
//                'offset': $scope.currentOffset
//            }, function (data) {
//                $scope.teams = data;
//                if (data.length !== 5) {
//                    $scope.length = true;
//                }
//            },function () {
//                angular.forEach($scope.teams, function (team) {
//                    team.user = UserService.get({
//                        'id': team.userId
//                    });
//                });
//            });
            $scope.teams = TeamService.query({
                'offset': $scope.currentOffset
            }, function (teamList) {
                angular.forEach(teamList, function (team) {
                    team.user = UserService.get({
                        'id': team.userId
                    });
                    console.log("Team User :%O", team.user);
                });
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
        .controller('TeamAddController', function (TeamService, $scope, $state, UserService) {
            $scope.editableTeam = {};

            $scope.setUser = function (user) {
                $scope.editableTeam.userId = user.id;
                $scope.editableTeam.user = user;
            };

            $scope.searchUsers = function (searchTerm) {
                return UserService.findByNameLike({
                    'username': searchTerm
                }).$promise;
            };

            $scope.saveTeam = function (data) {
                TeamService.save(data, function (data) {
                    console.log("DATA DATA", data);
                    $state.go('admin.team', {}, {'reload': true});
                });
            };
        })
        .controller('TeamEditController', function (TeamService, UserService, $scope, $stateParams, $state) {
            $scope.editableTeam = {};
            TeamService.get({
                'id': $stateParams.teamId
            }, function (data) {
                console.log("Data :%O", data);
                $scope.editableTeam = data;
                $scope.editableTeam.user = UserService.get({
                    'id': data.userId
                });
            });
            
            $scope.setUser = function (user) {
                $scope.editableTeam.userId = user.id;
                $scope.editableTeam.user = user;
            };

            $scope.searchUsers = function (searchTerm) {
                return UserService.findByNameLike({
                    'username': searchTerm
                }).$promise;
            };
            
            $scope.saveTeam = function (team) {
                team.$save(team, function () {
                    $state.go('admin.team', null, {'reload': true});
                });
            };
//            TeamService.get({'id': $stateParams.teamId}, function (data) {
//                $scope.editableTeam = data;
//                
//                $scope.editableTeam = UserService.get({'id': $stateParams.teamId}, function () {
//                
//                $scope.editableTeam.user = UserService.get({
//                'id': $scope.editableTeam.userId
//            });
//            });
//            });
//
//            $scope.setUser = function (user) {
//                $scope.editableTeam.userId = user.id;
//                $scope.editableTeam.user = user;
//            };
//
//            $scope.searchUsers = function (searchTerm) {
//                return UserService.findByNameLike({
//                    'username': searchTerm
//                }).$promise;
//            };
//
//
//            $scope.saveTeam = function (data) {
//                TeamService.save(data, function () {
//                    $state.go('admin.team', {}, {'reload': true});
//                });
//            };

        })
//        .controller('TeamDeleteController', function (TeamService, $scope, $stateParams, $state) {
//            $scope.editableTeam = {};
//            TeamService.get({'id': $stateParams.teamId}, function (data) {
//                $scope.editableTeam = data;
//            });
//
//            $scope.deleteTeam = function (data) {
//                TeamService.delete($scope.editableTeam, function (data) {
//                    $state.go('admin.team', {}, {'reload': true});
//                });
//            };
//        });

        .controller('TeamDeleteController', function (TeamService, $scope, $stateParams, $state) {
            $scope.editableTeam = TeamService.get({'id': $stateParams.teamId});
            $scope.deleteTeam = function (team) {
                team.$delete(function () {
                    $state.go('admin.team', null, {'reload': true});
                });
            };
        });
