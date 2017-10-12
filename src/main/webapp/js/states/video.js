angular.module("safedeals.states.video", [])
        .config(function ($stateProvider, templateRoot) {
            $stateProvider.state('admin.masters_video', {
                'url': '/video_master?offset',
                'templateUrl': templateRoot + '/masters/video/list.html',
                'controller': 'VideoListController'
            });
            $stateProvider.state('admin.masters_video.add', {
                'url': '/add',
                'templateUrl': templateRoot + '/masters/video/form.html',
                'controller': 'VideoAddController'
            });
            $stateProvider.state('admin.masters_video.edit', {
                'url': '/:videoId/edit',
                'templateUrl': templateRoot + '/masters/video/form.html',
                'controller': 'VideoEditController'
            });
            $stateProvider.state('admin.masters_video.delete', {
                'url': '/:videoId/delete',
                'templateUrl': templateRoot + '/masters/video/delete.html',
                'controller': 'VideoDeleteController'
            });

        })

        .controller('VideoListController', function (VideoService, PropertyService, ProjectService, $scope, $stateParams, $state, paginationLimit) {
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

            $scope.nextVideos = VideoService.query({
                'offset': $scope.nextOffset
            });

            $scope.videos = VideoService.query({
                'offset': $scope.currentOffset
            }
            , function () {
                angular.forEach($scope.videos, function (video) {
                    video.project = ProjectService.get({
                        'id': video.projectId
                    });
                    video.property = PropertyService.get({
                        'id': video.propertyId
                    });
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
        .controller('VideoAddController', function (VideoService, ProjectService, PropertyService, $scope, $stateParams, $state, paginationLimit, $timeout, FileUploader, restRoot) {

            $scope.editableVideo = {};

            $scope.$watch('editableVideo.name', function (name) {
                    
                VideoService.findByVideoName({'name': name}).$promise.catch(function (response) {
                    if (response.status === 500) {
                        $scope.editableVideo.repeatVideo = false;
                    }
                    else if (response.status === 404) {
                        $scope.editableVideo.repeatVideo = false;
                    }
                    else if (response.status === 400) {
                        $scope.editableVideo.repeatVideo = false;
                    }
                }).then(function (video) {
                    if (video.name !== null) {
                        $scope.editableVideo.repeatVideo = true;
                    }
                    ;
                });
            });

            $scope.setProject = function (project) {
                console.log("setproject", project);
                $scope.editableVideo.projectId = project.id;
                $scope.editableVideo.project = project;
            };
            $scope.searchProjects = function (searchTerm) {
                console.log("Search Term :%O", searchTerm);
                return ProjectService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };

            $scope.setProperty = function (property) {
                console.log("setproject", property);
                $scope.editableVideo.propertyId = property.id;
                $scope.editableVideo.property = property;
            };
            $scope.searchProperties = function (searchTerm) {
                console.log("Search Term :%O", searchTerm);
                return PropertyService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };


            $scope.saveVideo = function (video) {
                console.log("video", video);
                VideoService.save(video, function () {
                    $state.go('admin.masters_video', null, {'reload': true});
                });
            };


        })
        .controller('VideoEditController', function (VideoService, ProjectService, PropertyService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editableVideo = VideoService.get({'id': $stateParams.videoId},
            function () {
                $scope.editableVideo.project = ProjectService.get({
                    'id': $scope.editableVideo.projectId
                });
                $scope.editableVideo.property = PropertyService.get({
                    'id': $scope.editableVideo.propertyId
                });
            });

            $scope.setProject = function (project) {
                console.log("setproject", project);
                $scope.editableVideo.projectId = project.id;
                $scope.editableVideo.project = project;
            };
            $scope.searchProjects = function (searchTerm) {
                console.log("Search Term :%O", searchTerm);
                return ProjectService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };

            $scope.setProperty = function (property) {
                console.log("setproject", property);
                $scope.editableVideo.propertyId = property.id;
                $scope.editableVideo.property = property;
            };
            $scope.searchProperties = function (searchTerm) {
                console.log("Search Term :%O", searchTerm);
                return PropertyService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };

            $scope.saveVideo = function (video) {
                video.$save(function () {
                    $state.go('admin.masters_video', null, {'reload': true});
                });
            };
        })
        .controller('VideoDeleteController', function (VideoService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editableVideo = VideoService.get({'id': $stateParams.videoId});
            console.log("are we here?");
            $scope.deleteVideo = function (video) {
                video.$delete(function () {
                    $state.go('admin.masters_video', null, {'reload': true});
                });
            };
        });
