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
//            $stateProvider.state('admin.masters_video.upload', {
//                'url': '/:videoId/upload',
//                'templateUrl': templateRoot + '/masters/video/upload.html',
//                'controller': 'VideoUploadController'
//            });
        })

        .controller('VideoListController', function (VideoService, $scope, $stateParams, $state, paginationLimit) {
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

            VideoService.query({
                'offset': $scope.currentOffset
            }, function (s) {
                $scope.videos = s;
                console.log("VideoService ", s);
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
        .controller('VideoAddController', function (VideoService, $scope, $stateParams, $state, paginationLimit, $timeout, FileUploader, restRoot) {

            $scope.editableVideo = {};

            $scope.saveVideo = function (video) {
                console.log("video", video);
                VideoService.save(video, function () {
                    $state.go('admin.masters_video', null, {'reload': true});
                });
            };


        })
        .controller('VideoEditController', function (VideoService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editableVideo = VideoService.get({'id': $stateParams.videoId});

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
//        .controller('VideoUploadController', function (VideoService, $scope, $stateParams, $state, paginationLimit, $timeout, FileUploader, restRoot) {
//            $scope.editableVideo = VideoService.get({'id': $stateParams.videoId});
//            console.log("are we here?",$scope.editableVideo);
//
//            console.log("showDetails", $scope.showDetails);
//            var uploader = $scope.videoUploader = new FileUploader({
//                url: restRoot + '/video/' + $stateParams.videoId + '/setVideo',
//                autoUpload: true,
//                alias: 'video'
//            });
//
//            uploader.onBeforeUploadItem = function (item) {
//                $scope.uploadInProgress = true;
//                console.log("before upload item:", item);
//                console.log("uploader", uploader);
//            };
//            uploader.onErrorItem = function (fileItem, response, status, headers) {
//                $scope.uploadFailed = true;
//                $timeout(function () {
//                    $scope.uploadFailed = false;
//                }, 2000);
//                console.log("upload error");
//            };
//            uploader.onCompleteItem = function (fileItem, response, status, headers) {
//                $scope.uploadInProgress = true;
//                $timeout(function () {
//                    $scope.uploadInProgress = false;
//                }, 2000);
////                $scope.refreshRawReadyReckoner();
//                console.log("upload completion", fileItem);
//
//            };
//            var photouploader = $scope.photoUploader = new FileUploader({
//                url: restRoot + '/video/' + $stateParams.videoId + '/setPhoto',
//                autoUpload: true,
//                alias: 'photo'
//            });
//            photouploader.onBeforeUploadItem = function (item) {
//                $scope.uploadInProgress = true;
//                console.log("before upload item:", item);
//                console.log("uploader", photouploader);
//            };
//            photouploader.onErrorItem = function (fileItem, response, status, headers) {
//                $scope.uploadFailed = true;
//                $timeout(function () {
//                    $scope.uploadFailed = false;
//                }, 2000);
//                console.log("upload error");
//            };
//            photouploader.onCompleteItem = function (fileItem, response, status, headers) {
//                $scope.uploadInProgress = true;
//                $timeout(function () {
//                    $scope.uploadInProgress = false;
//                }, 2000);
////                $scope.refreshRawReadyReckoner();
//                console.log("upload completion", fileItem);
//
//            };
//            $scope.saveVideo = function (video) {
//                console.log("video line no 153", video);
//                VideoService.save(video, function (savedVideo) {
//                    $state.go('admin.masters_video', {videoId : savedVideo.id}, {'reload': true});
//                    console.log("video save after function", video);
//                });
//            };
////            $scope.saveVideo = function (video) {
////                console.log("video save", video);
////                video.$save(function () {
////                    $state.go('admin.masters_video', null, {'reload': true});
////                console.log("video save after function", video);
////                });
////            };
//        });

        