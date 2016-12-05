angular.module("safedeals.states.property", [])
        .config(function ($stateProvider, templateRoot) {
            $stateProvider.state("main.property", {
                'url': '/property',
                'templateUrl': templateRoot + '/property/property_content.html',
                'controller': 'PropertyController'
            });
            $stateProvider.state("main.property.property_map_container", {
                'url': '/property_map',
                'templateUrl': templateRoot + '/property/property_right_sidebar/properties.html',
                'controller': 'PropertyMapController'
            });
            $stateProvider.state("main.property.project_map_container", {
                'url': '/project_map',
                'templateUrl': templateRoot + '/property/property_right_sidebar/projects.html',
                'controller': 'ProjectMapController'
            });
            $stateProvider.state("main.property.school_map_container", {
                'url': '/school_map',
                'templateUrl': templateRoot + '/property/property_right_sidebar/schools.html',
                'controller': 'SchoolMapController'
            });
        })


        .controller('PropertyController', function ($scope, CoordinateService, PropertyService, MapService, templateRoot) {
            $scope.slabs = [
                'INR 5Lac',
                'INR 25Lac',
                'INR 50Lac',
                'INR 75Lac'
            ];
            $scope.getMinSlabValue = function(slab){
                console.log(slab);
            };
        })
        .controller('PropertyMapController', function ($scope, CoordinateService, PropertyService, MapService, templateRoot) {
            $scope.mapData = {
                'mapContainer': document.getElementById('mapContainer'),
                'mapCenter': {
                    'lat': 21.1500,
                    'lng': 79.0900
                },
                'markers': [
                ]
            };
            $scope.properties = PropertyService.findByLocationId({
                'locationId': 1
            }, function (properties) {
                angular.forEach(properties, function (property) {
                    $scope.mapData.markers.push({
                        'lat': property.latitude,
                        'lng': property.longitude,
                        'title': property.name
                    });
                });
                MapService.drawMap($scope.mapData);
            });
//            console.log("mapData", $scope.mapData.markers);


//            var nagpur = new google.maps.LatLng(21.1500, 79.0900);
//            var dhantoliNagpur = new google.maps.LatLng(21.1418, 79.0843);
//            var initialize = function () {
//                var mapContainer = document.getElementById('mapContainer');
//                var mapProp = {
//                    center: nagpur,
//                    zoom: 13,
//                    mapTypeId: google.maps.MapTypeId.ROADMAP
//                };
//
//                var map = new google.maps.Map(mapContainer, mapProp);
//                var myCity = new google.maps.Circle({
//                    center: dhantoliNagpur,
//                    radius: 500,
//                    editable: true,
//                    draggable: true,
//                    strokeColor: "#0000FF",
//                    strokeOpacity: 0.5,
//                    strokeWeight: 2,
//                    fillColor: "#0000FF",
//                    fillOpacity: 0.2
//                });
//                var marker = new google.maps.Marker({
//                    position: dhantoliNagpur,
//                    map: map,
////                    icon: 'images/map_markers/office-building.png',
//                    title: 'Dhantoli'
//                });
//                var contentString = '<div id="content">' +
//                        '<div id="siteNotice">' +
//                        '</div>' +
//                        '<div id="bodyContent">' +
//                        '<p><b>Himalaya Mansion</b></br>P. S. Road, Dhantoli<hr>Rs.65 lakhs</br>2 bhk Appartment</p>' +
//                        '</div>' +
//                        '</div>';
//
//                var infowindow = new google.maps.InfoWindow({
//                    content: contentString
//                });
//                marker.addListener('mouseover', function () {
//                    infowindow.open(map, marker);
//                });
//                marker.addListener('mouseout', function () {
//                    infowindow.close(map, marker);
//                });
//                myCity.setMap(map);
//            };
////            google.maps.event.addDomListener(window, 'load', initialize);
//            initialize();
        })
        .controller('ProjectMapController', function ($scope, ProjectService, MapService, templateRoot) {
            $scope.mapData = {
                'mapContainer': document.getElementById('mapContainer'),
                'mapCenter': {
                    'lat': 21.104836,
                    'lng': 79.003682
                },
                'markers': [
                ]
            };
            $scope.projects = ProjectService.findByLocationId({
                'locationId': 1
            }, function (projects) {
                angular.forEach(projects, function (project) {
                    console.log("project", project);
                    $scope.mapData.markers.push({
                        'lat': project.latitude,
                        'lng': project.longitude,
                        'title': project.name
                    });
                });
                MapService.drawMap($scope.mapData);
            });
        })
        .controller('SchoolMapController', function ($scope, SchoolService, MapService, templateRoot) {
            $scope.mapData = {
                'mapContainer': document.getElementById('mapContainer'),
                'mapCenter': {
                    'lat': 21.104836,
                    'lng': 79.003682
                },
                'markers': [
                ]
            };
            $scope.schools = SchoolService.findByLocationId({
                'locationId': 1
            }, function (schools) {
                angular.forEach(schools, function (school) {
                    console.log("school", school);
                    $scope.mapData.markers.push({
                        'lat': school.latitude,
                        'lng': school.longitude,
                        'title': school.name
                    });
                });
                MapService.drawMap($scope.mapData);
            });
        });