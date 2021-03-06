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
                        'templateUrl': templateRoot + '/corporate_site/contact.html',
                        'controller': 'ContactController'
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
                        'templateUrl': templateRoot + '/corporate_site/career.html',
                        'controller': 'CareerController'
                    });
        })

        .controller('CorporateSiteController', function ($scope, $stateParams, $state, $location, $anchorScroll) {
            var parrentDiv = $('#parrentDiv');
            parrentDiv.removeClass();
            parrentDiv.addClass('bg-site');
            $scope.hidden = true;

            $scope.gotoTop = function () {
                $location.hash('top');
                $anchorScroll();
            };
        })

        .controller('AboutUsController', function ($scope) {
            $scope.hidden = true;
            $scope.IsVisible = true;
            $scope.Toggle = true;
        })

        .controller('ServiceController', function ($scope, TestimonialService) {
            $scope.myInterval = 3000;
            $scope.noWrapSlides = false;
            $scope.active = 0;
            $scope.slides = TestimonialService.findByCategory();
        })

        .controller('SdNetworkController', function ($scope, CityService, FranchiseService, LocationService, CityService, $state, paginationLimit, $stateParams) {
            var map;
            var mapContainer = document.getElementById("mapContainerSdNetwork");
            var nagpurCoordinate = new google.maps.LatLng(21.1458, 79.0882);
            var mapProp = {
                center: nagpurCoordinate,
                zoom: 11,
                mapTypeId: google.maps.MapTypeId.ROADMAP
            };
            var drawMap = function () {
                map = new google.maps.Map(mapContainer, mapProp);
            };
            drawMap();
            var drawDynamicMap = function (newMapProp) {
                console.log("newMapProp %O", newMapProp);
                map = new google.maps.Map(mapContainer, newMapProp);
            };
            $scope.franchises = FranchiseService.findByCityId({
                'cityId': 78
            }, function (franchises) {
                angular.forEach(franchises, function (franchise) {
                    console.log("Single Object :%O", franchise);
                    LocationService.get({
                        'id': franchise.locationId
                    }, function (locationObject) {
                        drawMarker({lat: locationObject.latitude, lng: locationObject.longitude}, franchise.name, map);
                    });
                });
            });
            var drawMarker = function (position, title, map) {
                console.log("Position :%O", position);
                new google.maps.Marker({
                    map: map,
                    position: position,
                    title: title
                            // icon: 'images/icons_svg/dot.png'
                });
            };
            $scope.setCity = function (city) {
                $scope.city = city;
                $scope.franchises = FranchiseService.findByCityId({
                    'cityId': city.id
                }, function (franchiseList) {
                    angular.forEach(franchiseList, function (franchise) {
                        LocationService.get({
                            'id': franchise.locationId
                        }, function (locationObject) {
                            var newMapProp = {
                                center: new google.maps.LatLng(city.latitude, city.longitude),
                                zoom: 11,
                                mapTypeId: google.maps.MapTypeId.ROADMAP

                            };
                            console.log("new map prop %O", newMapProp);
                            drawDynamicMap(newMapProp);
                            drawMarker({lat: locationObject.latitude, lng: locationObject.longitude}, franchise.name, map);
                        });
                    });
                });
            };
            $scope.searchCities = function (searchTerm) {
                console.log("Search Term :%O", searchTerm);
                return CityService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };
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
            $scope.editableEnquiry = {
                'category': "PROPERTY_GUIDANCE"
            };    
            $scope.saveEnquiry = function (enquiry) {
                console.log("enquiry name:", enquiry);
                $scope.sendSms(enquiry.mobileNo);
                EnquiryService.save(enquiry, function () {
                    $state.go('corporate_site.home', null, {'reload': true});
                });
            };
            $scope.sendSms = function (clientNumber) {
                EnquiryService.sendSms();
            };
            $scope.$watch('editableEnquiry.category', function (category) {
                console.log("Category :" + category);
                if (category === "PROPERTY_GUIDANCE") {
                    $scope.propertyGuidance = true;
                    $scope.propertySelling = false;
                    $scope.propertyBuying = false;
                } else if (category === "PROPERTY_SELLING") {
                    $scope.propertyGuidance = false;
                    $scope.propertySelling = true;
                    $scope.propertyBuying = false;
                } else if (category === "PROPERTY_BUYING") {
                    $scope.propertyGuidance = false;
                    $scope.propertySelling = false;
                    $scope.propertyBuying = true;
                } else {
                    $scope.propertyGuidance = false;
                    $scope.propertySelling = false;
                    $scope.propertyBuying = false;
                }
            });
        })
        .controller('EventsController', function ($scope, EventService, imageRoot) {
            console.log("EventController");
            console.log("Image Root :%O", imageRoot);
            $scope.imageRoot = imageRoot;
            $scope.events = EventService.findByDate();
            console.log("What are EVents :%O", $scope.events);
            $scope.concludedEvents = EventService.findConcludedEvents();
            console.log("COncluded Events :%O", $scope.concludedEvents);
            console.log("$scope.events", $scope.events);
            $scope.myInterval = 5000;
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
        .controller('CareerController', function ($scope, TestimonialService) {
            $scope.myInterval = 3000;
            $scope.noWrapSlides = false;
            $scope.active = 0;
            $scope.slides = [
                {
                    image: 'images/img5.jpg',
                    name: 'Kristiana',
                    designation: 'Web Developer www.example1.com',
                    text: 'Lorem ipsum dolor sit amet consectetur quam felis, ultricies nec, pellentesque eu, pretium quis, sem\n\
Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec. In enim justo,rhoncus ut'
                },
                {
                    image: 'images/img6.jpg',
                    name: 'Kristiana',
                    designation: 'photographer www.example1.com',
                    text: 'Lorem ipsum dolor sit amet consectetur quam felis, ultricies nec, pellentesque eu, pretium quis, sem\n\
Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec. In enim justo,rhoncus ut'
                },
                {
                    image: 'images/img7.jpg',
                    name: 'Kristiana',
                    designation: 'Web Developer www.example1.com',
                    text: 'Lorem ipsum dolor sit amet consectetur quam felis, ultricies nec, pellentesque eu, pretium quis, sem\n\
Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec. In enim justo,rhoncus ut'
                },
                {
                    image: 'images/img8.jpg',
                    name: 'Kristiana',
                    designation: 'Web Developer www.example1.com',
                    text: 'Lorem ipsum dolor sit amet consectetur quam felis, ultricies nec, pellentesque eu, pretium quis, sem\n\
Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec. In enim justo,rhoncus ut'
                }
            ];
        })

        .controller('HomeController', function ($scope) {

            $scope.myInterval = 5000;
            $scope.noWrapSlides = false;
            $scope.active = 0;
            $scope.slides = [
                {
                    image: 'images/banner.jpg',
                    text: 'To take an Informed Decision'
                },
                {
                    image: 'images/banner.jpg'
                },
                {
                    image: 'images/banner.jpg'
                }
            ];
        })
        .controller('CareerController', function ($scope, TestimonialService) {
            $scope.myInterval = 3000;
            $scope.noWrapSlides = false;
            $scope.active = 0;
            $scope.slides = [
                {
                    image: 'images/img5.jpg',
                    name: 'KRISTIANA ANDREW',
                    designation: 'Web Developer www.example1.com',
                    text: 'Lorem ipsum dolor sit amet consectetur quam felis, ultricies nec, pellentesque eu, pretium quis, sem\n\
Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec. In enim justo,rhoncus ut'
                },
                {
                    image: 'images/img6.jpg',
                    name: 'JONH EDISON',
                    designation: 'Photographer www.example1.com',
                    text: 'Lorem ipsum dolor sit amet consectetur quam felis, ultricies nec, pellentesque eu, pretium quis, sem\n\
Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec. In enim justo,rhoncus ut'
                },
                {
                    image: 'images/img7.jpg',
                    name: 'ROBIN SHARMA',
                    designation: 'Web Developer www.example1.com',
                    text: 'Lorem ipsum dolor sit amet consectetur quam felis, ultricies nec, pellentesque eu, pretium quis, sem\n\
Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec. In enim justo,rhoncus ut'
                },
                {
                    image: 'images/img8.jpg',
                    name: 'ANDREW JONH',
                    designation: 'Photographer www.example1.com',
                    text: 'Lorem ipsum dolor sit amet consectetur quam felis, ultricies nec, pellentesque eu, pretium quis, sem\n\
Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec. In enim justo,rhoncus ut'
                }
            ];
        })
        .controller('ContactController', function ($scope, $state, MailService) {
            $scope.editableEnquiry = {};
            $scope.sendMail = function (mailObject) {
                console.log("Mail Object :%O", mailObject);
                MailService.sendEmail({
                    'mailId': mailObject.email
                }, function () {
                    alert("Enquiry Submitted Successfully.Safe-Deals team will get back to you soon regarding your enquiry.");
                    $state.go('corporate_site.contact', null, {'reload': true});
                });
            };
        });
