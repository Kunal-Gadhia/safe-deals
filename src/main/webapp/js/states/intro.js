angular.module("safedeals.states.intro", [])
        .config(function ($stateProvider, templateRoot) {
            $stateProvider.state('main.intro', {
                'url': '/intro_sidebar',
                'templateUrl': templateRoot + '/intro/intro_sidebar.html',
                'controller': 'IntroSidebarController'
            });
            $stateProvider.state("main.intro.intro_content", {
                'url': '/:videoId/intro_content',
                'templateUrl': templateRoot + '/intro/intro_content.html',
                'controller': 'IntroContentController'
            });
            $stateProvider.state("main.intro.intro_tagline", {
                'url': '/intro_tagline',
                'templateUrl': templateRoot + '/intro/intro_tagline.html',
                'controller': 'IntroTaglineController'
            });

        })

        .controller('IntroSidebarController', function (VideoService, $scope, $stateParams) {
            console.log("In Intro Side Bar video");
//            $scope.videos = [];
            VideoService.findAllVideos(function (videos) {
                console.log("Videos :", videos);
                angular.forEach(videos, function (video) {
                    console.log("Video :", video);
                    video.videoId = video.videoUrl.match(/youtube\.com.*(\?v=|\/embed\/)(.{11})/).pop();
                    video.thumbnailImgSrc = 'http://img.youtube.com/vi/' + video.videoId + '/0.jpg';
                });
                $scope.videos = videos;
                console.log("videos list", $scope.videos);
            });


        })
        .controller('IntroContentController', function (VideoService, $scope, $stateParams) {

            VideoService.get({
                'id': $stateParams.videoId
            }, function (data) {
                $scope.selectedVideo = data;
                console.log("$scope.selectedVideo ", $scope.selectedVideo);
            });
        })
        .controller('IntroTaglineController', function (VideoService, $scope, $stateParams) {
            $scope.introVideo = VideoService.findIntroVideo(function (data) {
                console.log("intro Video ", data);
            });
        });

