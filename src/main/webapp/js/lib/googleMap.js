angular.module("safedeals.map", []);
angular.module("safedeals.map")
        .factory('MapService', function () {
            return{
                'drawMap': function (mapData) {
                    console.log("mapData in service", mapData);
                    var mapProp = {
                        center: new google.maps.LatLng(mapData.mapCenter.lat, mapData.mapCenter.lng),
                        zoom: 13,
                        mapTypeId: google.maps.MapTypeId.ROADMAP
                    };
                    var map = new google.maps.Map(mapData.mapContainer, mapProp);
                    if (mapData.markers !== undefined){

                        angular.forEach(mapData.markers, function (marker) {
                            console.log("are we here ", marker);
                            new google.maps.Marker({
                                position: new google.maps.LatLng(marker.lat, marker.lng),
                                map: map,
//                    icon: 'images/map_markers/office-building.png',
                                title: marker.title
                            });
                        });
                    }
                }
            };
        });