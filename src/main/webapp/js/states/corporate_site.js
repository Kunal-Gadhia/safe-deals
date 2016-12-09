angular.module("safedeals.states.corporate_site", [])
        .config(function ($stateProvider, templateRoot) {
            $stateProvider
                    .state('corporate_site', {
                        'url': '/corporate_site',
                        'templateUrl': templateRoot + '/corporate_site.html',
                        'controller': 'CorporateSiteController'
                    })
                    .state('corporate_site.about_us', {
                        'url': '/about_us',
                        'templateUrl': templateRoot + '/corporate_site/about_us.html',
                        'controller': 'AboutUsController'
                    })
                    .state('corporate_site.home', {
                        'url': '/home',
                        'templateUrl': templateRoot + '/corporate_site/home.html',
                        'controller': 'HomeController'
                    })
                    .state('corporate_site.sdnetwork', {
                        'url': '/sdnetwork?offset',
                        'templateUrl': templateRoot + '/corporate_site/sdnetwork.html',
                        'controller': 'SdNetworkController'
                    })
                    .state('corporate_site.postQuery', {
                        'url': '/postQuery',
                        'templateUrl': templateRoot + '/corporate_site/post_query.html',
                        'controller': 'PostQueryController'
                    })
                    .state('corporate_site.contact', {
                        'url': '/contact',
                        'templateUrl': templateRoot + '/corporate_site/contact.html'
                    })
                    .state('corporate_site.events', {
                        'url': '/events',
                        'templateUrl': templateRoot + '/corporate_site/events.html',
                        'controller': 'EventsController'
                    })
                    .state('corporate_site.services', {
                        'url': '/services',
                        'templateUrl': templateRoot + '/corporate_site/service.html',
                        'controller': 'ServiceController'
                    })
                    .state('corporate_site.career', {
                        'url': '/career',
                        'templateUrl': templateRoot + '/corporate_site/career.html'
                    });
        })

        .controller('CorporateSiteController', function ($scope, $stateParams, $state) {
            var parrentDiv = $('#parrentDiv');
            parrentDiv.removeClass();
            parrentDiv.addClass('bg-site');
            $scope.hidden = true;
        })

        .controller('AboutUsController', function ($scope) {
            $scope.hidden = true;
            $scope.IsVisible = true;
            $scope.Toggle = true;
        })

        .controller('ServiceController', function ($scope, TestimonialService) {
            $scope.myInterval = 2000;
            $scope.noWrapSlides = false;
            $scope.active = 0;
            $scope.slides = [
                {
                    image: 'images/img5.jpg',
                    text: 'Lorem ipsum dolor sit amet consectetur adipisicing'
                },
                {
                    image: 'images/img6.jpg',
                    text: 'Awesome photograph'
                },
                {
                    image: 'images/img7.jpg',
                    text: 'That is so cool'
                },
                {
                    image: 'images/img8.jpg',
                    text: 'I love that'
                }
            ];

//            $scope.IsVisible = true;
////            $scope.IsHidden = true;
////            $scope.IsContent = true;
////            $scope.IsHide = true;
////            $scope.IsToggle = true;
//            $scope.testimonials = TestimonialService.query();
//            console.log("$scope.testimonials", $scope.testimonials);
//            $scope.myInterval = 3000;
        })

        .controller('SdNetworkController', function ($scope, FranchiseService, LocationService, CityService, $state, paginationLimit, $stateParams) {

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

            $scope.nextFranchises = FranchiseService.query({
                'offset': $scope.nextOffset
            });

            $scope.franchises = FranchiseService.query({
                'offset': $scope.currentOffset
            }
            , function (franchises) {
                angular.forEach(franchises, function (franchise) {

                    franchise.city = CityService.get({
                        'id': franchise.cityId
                    });
                    franchise.location = LocationService.get({
                        'id': franchise.locationId
                    });
                });
            });
            console.log("$scope.franchises", $scope.franchises);

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

        .controller('PostQueryController', function (EnquiryService, $scope, $state) {
            $scope.editableEnquiry = {};
            $scope.saveEnquiry = function (enquiry) {
                console.log("enquiry name:", enquiry);
                $scope.sendSms(enquiry.mobileNo);
                EnquiryService.save(enquiry, function () {
                    $state.go('corporate_site.home', null, {'reload': true});
                });
            };
            $scope.sendSms = function (clientNumber) {
                console.log("Coming to this additional function with number?? :%O", clientNumber);
                EnquiryService.sendSms();
            };
        })
        .controller('EventsController', function ($scope, EventService) {
            console.log("EventController");
            $scope.events = EventService.findByDate();
            console.log("$scope.events", $scope.events);

           $scope.myInterval = 2000;
            $scope.noWrapSlides = false;
            $scope.active = 0;
            $scope.slides = [
                {
                    image: 'images/banner.jpg'
                },
                {
                    image: 'images/banner.jpg'
                },
                {
                    image: 'images/banner.jpg'
                }

            ];
        })

        .controller('HomeController', function ($scope) {

            $scope.myInterval = 2000;
            $scope.noWrapSlides = false;
            $scope.active = 0;
            $scope.slides = [
                {
                    image: 'images/banner.jpg'
                },
                {
                    image: 'images/banner.jpg'
                },
                {
                    image: 'images/banner.jpg'
                }

            ];
        });