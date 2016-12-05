angular.module("safedeals.services.location_category", []);
angular.module("safedeals.services.location_category")
        .factory('LocationCategoryService', function ($resource, restRoot) {
            return $resource(restRoot + '/location_category/:id', {'id': '@id'},{                
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

        .directive('sdLocationCategoryMultiSelect', function (LocationCategoryService, $compile, templateRoot, paginationLimit, $filter) {
            return{
                'restrict': 'AE',
                'templateUrl': templateRoot + '/directives/selection_widgets/location_category_multi_select.html',
                'scope': {
                    'onSelect': '&onSelect',
                    'showWidget': '=',
                    'locationDir': '=locationDir',
                    'locationIds': '=locationIds'
                   },
                'link': function (scope, element, attrs) {
//                    console.log("LOCATION CATEGORY link function");
                    scope.currentOffset = 0;
                    var lcArrayMain = [];
                    scope.limit = paginationLimit;
                    scope.$watchCollection('locationDir ', function (lcids) {
                        //console.log("locationDir lcids" , lcids);
                        if (scope.locationDir !== null && scope.locationDir !== undefined && lcids !== undefined) {
                            var uniqueLcids = _.uniq(lcids, function (x) {
                                return x.id;
                            });
                            console.log("uniqueLcids",uniqueLcids);
                            angular.forEach(lcids, function (lcid) {
                                lcArrayMain.push(lcid);
                            });
                          //console.log("lcArrayMain",lcArrayMain);
                            refreshPage();
                        } else {
                            lcArrayMain = [];
                        }
                    });
                    var refreshPage = function () {
                        var category = [];
                        scope.locationCategories = LocationCategoryService.query(function (data) {
                            //console.log("daatataa", data);
                            angular.forEach(data, function (lc, key) {
                                if (key >= scope.currentOffset && key < scope.limit) {
                                    category.push(lc);
                                }
                                scope.locationCategories = category;
                            });
                        });
                        scope.$watch('locationDir', function (data) {
                           // console.log("data", data);
                            if (data === undefined) {
                                angular.forEach(scope.locationCategories, function (locationCategory) {
                                    locationCategory.selected = false;
                                });
                            }
                        });
                       //scope.locationCategories=locationCategoryArrayMain;
                        scope.$watch('locationCategories', function (lcs) {
                            //console.log("are we in watch of location IDS" , locationCategories);
                            if (scope.locationCategories !== undefined) {
                                angular.forEach(lcs, function (lc) {
                                    angular.forEach(lcArrayMain, function (lcSelected) {
                                        //console.log("lcArrayMain IIIIIIIIIII" , lcArrayMain);
                                        //console.log("lc.id IIIIIIIIIII" , lc.id);
                                        //console.log("lcselected " , lcSelected);
                                        if (lc.id === lcSelected.id) {
                                          //console.log("lc IF KE ANDAR" , lcSelected);
                                          //console.log("lc IF KE ANDAR" , lc);
                                          lc.selected = true;
                                        }
                                    });
                                });
                            }
                        });
                    };
                    $compile();
                    scope.nextPage = function () {
                        console.log("main array on nect page", lcArrayMain);
                        scope.currentOffset += paginationLimit;
                        scope.limit += paginationLimit;
                        refreshPage();
                    };
                    scope.previousPage = function () {
                        if (scope.currentOffset <= 0) {
                            return;
                        }
                        scope.currentOffset -= paginationLimit;
                        scope.limit -= paginationLimit;
                        refreshPage();
                    };

                    scope.select = function (lc) {
                     //console.log("lc", lc);
                        if (lc.selected) {
                            //console.log("lc.selected :IF: line 95",lc);
                            var foundLc = $filter('getById')(lcArrayMain, lc.id);
                           //console.log("foundLc aa gaye kya", foundLc);
                           console.log("foundLc aa gaye kya", lc.id);
                           //console.log("lcArrayMain", lc.id);
                           //console.log("lcArrayMain", lcArrayMain);

                            
                            if (foundLc === null || lc.id !== foundLc.id) {
                              lcArrayMain.push(lc);
                         // console.log("lcArrayMain", lc);
                          //console.log("lcArrayMain",foundLc);
                         // console.log("lcArrayMain", lcArrayMain);
                            }
                         //console.log("lcArrayMain", lc.selected);
                         //console.log("lcArrayMain", lcArrayMain);
                                            
                        } else {
                            var foundLc = $filter('getById')(lcArrayMain, lc.id);
                              //console.log("foundLc aa gaya kya",lcArrayMain );
                              //console.log("foundLc remove milya kya", lc.id);
                               // console.log("lcArrayMain", lc.id);
                            //console.log("lcArrayMain",lcArrayMain);
                            //console.log("lcArrayMain", foundLc);

                            if (foundLc !== null && lc.id === foundLc.id) {
                                //console.log("lcArrayMain",lc.id);
                                //console.log("lcArrayMain", lcArrayMain);

                                angular.forEach(lcArrayMain, function (lcInMainArray, index) {
                                    if (foundLc.id === lcInMainArray.id) {
                                       // console.log("index", index);
                                        //console.log("lcArrayMainBefore poppin", lcArrayMain);
                                        lcArrayMain.splice(index, 1);
                                       // console.log("lcArrayMain ELSE", lcArrayMain);
                                    }
                                });
                            }
                        }
                    };

                    scope.done = function () {
                        scope.showWidget = false;
                        console.log("arrrray", _.uniq(lcArrayMain, function (x) {
                            //console.log("x",x);
                            return x.id;
                        }));
                        scope.onSelect({
                            
                            'locationCategories': _.uniq(lcArrayMain, function (x) {
                                                        //console.log("scopelocationCategories",scope.locationCategories);

                                return x.id;
                            })
                        });
                    };
                    scope.close = function () {
                        scope.showWidget = false;
                    };
                    refreshPage();

                    $compile(element.contents())(scope);

                }
            };
        });

