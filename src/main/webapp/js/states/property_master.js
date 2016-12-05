angular.module("safedeals.states.property_master", [])
        .config(function ($stateProvider, templateRoot) {
            $stateProvider.state('admin.masters_property', {
                'url': '/property_master?offset',
                'templateUrl': templateRoot + '/masters/property/list.html',
                'controller': 'PropertyListController'
            });
            $stateProvider.state('admin.masters_property.add', {
                'url': '/add',
                'templateUrl': templateRoot + '/masters/property/form.html',
                'controller': 'PropertyAddController'
            });
            $stateProvider.state('admin.masters_property.edit', {
                'url': '/:propertyId/edit',
                'templateUrl': templateRoot + '/masters/property/form.html',
                'controller': 'PropertyEditController'
            });
            $stateProvider.state('admin.masters_property.delete', {
                'url': '/:propertyId/delete',
                'templateUrl': templateRoot + '/masters/property/delete.html',
                'controller': 'PropertyDeleteController'
            });
        })
        .controller('PropertyListController', function (CityService, CountryService, StateService, PropertyService, $scope, $stateParams, $state, paginationLimit) {
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

            $scope.nextProperties = PropertyService.query({
                'offset': $scope.nextOffset
            });

            $scope.properties = PropertyService.query({
                'offset': $scope.currentOffset
            });
//            , function () {
//                angular.forEach($scope.properties, function (property) {
////                    property.country = CountryService.get({
////                        'id': property.countryId
////                    });
////                    property.state = StateService.get({
////                        'id': property.stateId
////                    });
//                    property.city = CityService.get({
//                        'id': property.cityId
//                    });
//                });
//            });

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
        .controller('PropertyAddController', function (CityService, StateService, CountryService, PropertyService, $scope, $stateParams, $state, paginationLimit) {
//            $scope.countries = CountryService.query();
//            $scope.states = StateService.query();
            $scope.cities = CityService.query();
            $scope.editableProperty = {};
            $scope.datePicker = {
                opened: false,
                toggle: function () {
                    this.opened = !this.opened;
                }
            };
//            $scope.setCountry = function (country) {
//                $scope.editableProperty.countryId = country.id;
//                $scope.editableProperty.country = country;
//            };
//            $scope.setState = function (state) {
//                $scope.editableProperty.stateId = state.id;
//                $scope.editableProperty.state = state;
//            };
            $scope.setCity = function (city) {
                $scope.editableProperty.cityId = city.id;
                $scope.editableProperty.city = city;
            };
            $scope.saveProperty = function (property) {
                console.log("property name:" + property.name);
                PropertyService.save(property, function () {
                    $state.go('admin.masters_property', null, {'reload': true});
                });
            };
        })
        .controller('PropertyEditController', function (CityService, StateService, CountryService, PropertyService, $scope, $stateParams, $state, paginationLimit) {
//            $scope.countries = CountryService.query();
//            $scope.states = StateService.query();
            $scope.cities = CityService.query();

            $scope.editableProperty = PropertyService.get({'id': $stateParams.propertyId});

            $scope.datePicker = {
                opened: false,
                toggle: function () {
                    this.opened = !this.opened;
                }
            };
//            $scope.setCountry = function (country) {
//                $scope.editableProperty.countryId = country.id;
//                $scope.editableProperty.country = country;
//            };
//            $scope.setState = function (state) {
//                $scope.editableProperty.stateId = state.id;
//                $scope.editableProperty.state = state;
//            };
            $scope.setCity = function (city) {
                $scope.editableProperty.cityId = city.id;
                $scope.editableProperty.city = city;
            };
            $scope.saveProperty = function (property) {
                property.$save(function () {
                    $state.go('admin.masters_property', null, {'reload': true});
                });
            };
        })
        .controller('PropertyDeleteController', function (PropertyService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editableProperty = PropertyService.get({'id': $stateParams.propertyId});

            $scope.deleteProperty = function (property) {
                property.$delete(function () {
                    $state.go('admin.masters_property', null, {'reload': true});
                });
            };
        });
