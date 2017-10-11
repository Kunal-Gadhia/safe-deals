angular.module("safedeals.services.location_category", []);
angular.module("safedeals.services.location_category")
        .factory('LocationCategoryService', function ($resource, restRoot) {
            return $resource(restRoot + '/location_category/:id', {'id': '@id'}, {
                'findByName': {
                    'method': 'GET',
                    'url': restRoot + '/location_category/find/name',
                    'params': {
                        'name': '@name'
                    },
                    'isArray': false
                },
                'findByNameLike': {
                    'method': 'GET',
                    'url': restRoot + '/location_category/find/location_category_like',
                    'params': {
                        'name': '@name'
                    },
                    'isArray': true
                }
            });
        })

        .directive('sdLocationCategoryMultiSelect', function (LocationCategoryService, $compile, templateRoot, paginationLimit, $filter, $sce) {
            /////////////////////////////////////////////////
            return{
                'restrict': 'AE',
                'templateUrl': $sce.trustAsResourceUrl(templateRoot + '/directives/selection_widgets/location_category_multi_select.html'),
                'scope': {
                    'onSelect': '&onSelect',
                    'showWidget': '=',
                    'locationcategoriesDir': '=',
                    'locationcategoriesIds': '='
                },
                'link': function (scope, element, attrs) {
                    console.log("Inside The Directive");

                    LocationCategoryService.query(function (locationCategoryList) {
                        scope.locationCategories = locationCategoryList;
                    });
                    scope.close = function () {
                        scope.showWidget = false;
                    };

                    var locationCategoryArrayMain = [];
                    scope.select = function (locationCategory) {
                        /*
                         call the callback function
                         */
                        console.log("Location Ctegory :%O", locationCategory);
                        if (locationCategory.selected) {
                            var foundLocationCategory = $filter('getById')(locationCategoryArrayMain, locationCategory.id);
                            if (foundLocationCategory === null || locationCategory.id !== foundLocationCategory.id) {

                                locationCategoryArrayMain.push(locationCategory);

                            }
                        } else {
                            var foundLocationCategory = $filter('getById')(locationCategoryArrayMain, locationCategory.id);
                            if (foundLocationCategory !== null && locationCategory.id === foundLocationCategory.id) {
                                angular.forEach(locationCategoryArrayMain, function (locationCategoryInMainArray, index) {
                                    if (foundLocationCategory.id === locationCategoryInMainArray.id) {
                                        locationCategoryArrayMain.splice(index, 1);
                                    }
                                });
                            }
                        }
                        console.log("Location Category Array :%O", locationCategoryArrayMain);
                    };
                    // Check All Flags For Employees
                    scope.selectAll = function (locationCategories) {
                        locationCategoryArrayMain = [];
                        locationCategories.forEach(function (locationCategory) {
                            locationCategory.selected = true;
                            locationCategoryArrayMain.push(locationCategory);
                        });
                    };

                    // Uncheck All For Employees
                    scope.clearAll = function () {
                        locationCategoryArrayMain.forEach(function (locationCategory) {
                            scope.locationCategories.forEach(function (emp) {
                                if (emp.id === locationCategory.id) {
                                    locationCategory.selected = false;
                                    emp.selected = false;
                                }
                            });
                        });
                        locationCategoryArrayMain = [];
                    };

                    scope.setLocationCategories = function () {
                        scope.showWidget = false;
                        scope.searchVisible = true;
                        scope.onSelect({
                            'locationCategories': locationCategoryArrayMain
                        });
                        var locationcategoriesIds = [];
                        angular.forEach(locationCategoryArrayMain, function (employee) {
                            locationcategoriesIds.push(employee.id);
                        });

                    };
                    // Keep watch on pre selected employees
                    scope.$watch('locationcategoriesDir', function (data) {
                        if (data) {
                            locationCategoryArrayMain = [];
                            locationCategoryArrayMain = data;
                        } else {
                            scope.clearAll();
                        }
                    });
                    // Manipulation of checks for employees
                    scope.locationCategories = locationCategoryArrayMain;
                    scope.$watch('locationCategories', function (locationCategories) {
                        if (scope.locationCategories !== undefined) {
                            angular.forEach(locationCategories, function (locationCategory) {
                                angular.forEach(locationCategoryArrayMain, function (locationCategorySelected) {
                                    if (locationCategory.id === locationCategorySelected.id) {
                                        locationCategory.selected = true;
                                    }
                                });
                            });
                        }
                    });
                }
            };
        });
