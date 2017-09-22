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
//                    scope.filterParam = {};
//
//                    scope.searchVisible = true;
//                    scope.showPanel = function () {
//                        scope.searchVisible = false;
//                    };
//                    scope.previousPanel = function () {
//                        scope.searchVisible = true;
//                    };


//                    scope.filterParam.department = {};
//                    scope.filterParam.designation = {};
//                    scope.filterParam.skill = {};
//                    scope.filterParam.facility = {};
//                    scope.filterParameters = function (filterParam) {
//                        scope.loading = true;
//                        scope.searchVisible = false;
//                        var functionalityMeeting = '';
//                        var functionalityProject = '';
//
//                        if ($rootScope.MY_MEETINGS || $rootScope.MY_MEETINGS === true) {
//                            functionalityMeeting = 'MY_MEETINGS';
//                        } else if ($rootScope.ALL_MEETINGS || $rootScope.ALL_MEETINGS === true) {
//                            functionalityMeeting = 'ALL_MEETINGS';
//                        }
//                        if ($rootScope.ALL_PROJECTS || $rootScope.ALL_PROJECTS === true) {
//                        } else if ($rootScope.MY_PROJECTS || $rootScope.MY_PROJECTS === true) {
//                            functionalityProject = 'ALL_PROJECTS';
//                            functionalityProject = 'MY_PROJECTS';
//                        }

                    LocationCategoryService.query(function (locationCategoryList) {
                        scope.locationCategories = locationCategoryList;
                    });


//                    EmployeeService.findByEmployeeFilter({
//                        'name': filterParam.name,
//                        'departmentId': filterParam.department.id,
//                        'designationId': filterParam.designation.id,
//                        'skillId': filterParam.skill.id,
//                        'facilityId': filterParam.facility.id,
//                        'functionalityProject': functionalityProject,
//                        'functionalityMeeting': functionalityMeeting,
//                        'reportCall': scope.reportCall
//                    }, function (employees) {
//                        scope.loading = true;
//                        scope.employees = employees;
//                        angular.forEach(scope.employees, function (employee) {
//                            employee.department = DepartmentService.get({
//                                'id': employee.departmentId
//                            });
//                            employee.designation = DesignationService.get({
//                                'id': employee.designationId
//                            });
//                        });
//                        console.log("scope.projectId ============", scope.projectId);
//                        if (!scope.reportCall || scope.reportCall == false) {
//                            ActivityTransactionService.occupiedEmployees({
//                                'startDate': new Date(scope.startDate).getTime(),
//                                'endDate': new Date(scope.endDate).getTime(),
//                                'activityTransactionId': scope.activityTransactionId === undefined ? 0 : scope.activityTransactionId,
//                                'projectId': scope.projectId === undefined ? 0 : scope.projectId
//                            }, function (occupiedEmployeesList) {
//                                if (occupiedEmployeesList.length > 0) {
//                                    angular.forEach(occupiedEmployeesList, function (ol) {
//                                        angular.forEach(scope.employees, function (employee) {
//                                            if (ol.id === employee.id) {
//                                                employee.occupied = true;
//                                            } else if (!employee.occupied) {
//                                                employee.occupied = false;
//                                            }
//
//                                        });
//                                    });
//                                } else {
//                                    console.log("occupied employees length=0");
//                                    angular.forEach(scope.employees, function (employee) {
//                                        employee.occupied = false;
//                                    });
//                                }
//
//                            }, function (err) {
////                                    scope.loading = false;
//                            });
//                        }
//
////                            scope.loading = false;
//                    }, function (error) {
////                            scope.loading = false;
//                    }).$promise;

//                    };

//                    // search Facility typeahead
//                    scope.searchFacility = function (data) {
//                        if (data !== null) {
//                            return FacilityService.findBySearch({
//                                'name': data
//                            }).$promise;
//                        }
//                    };
//                    // search Department typeahead
//                    scope.searchDepartment = function (data) {
//                        if (data !== null) {
//                            return DepartmentService.findBySearch({
//                                'name': data
//                            }).$promise;
//                        }
//                    };
//                    // search Skill typeahead
//                    scope.searchSkill = function (data) {
//                        if (data !== null) {
//                            return SkillService.findBySearch({
//                                'name': data
//                            }).$promise;
//                        }
//                    };
//                    // search Designation typeahead
//                    scope.searchDesignation = function (data) {
//                        if (data !== null) {
//                            return DesignationService.findByDesignationSearch({
//                                'name': data
//                            }).$promise;
//                        }
//                    };

//                    scope.clearDesignation = function () {
//                        scope.filterParam.designation = {};
//                    };
//                    scope.clearDepartment = function () {
//                        scope.filterParam.department = {};
//
//                    };
//                    scope.clearFacility = function () {
//                        scope.filterParam.facility = {};
//                    };
//                    scope.clearSkill = function () {
//                        scope.filterParam.skill = {};
//                    };
//                    scope.setDesignation = function (designation) {
//                        if (designation !== null) {
//                            scope.filterParam.designation = designation;
//                        } else {
//                            scope.filterParamfilterParam.designation = null;
//                        }
//                    };
//
//                    scope.setDepartment = function (department) {
//                        if (department !== null) {
//                            scope.filterParam.department = department;
//                        } else {
//                            scope.filterParam.department = null;
//                        }
//                    };
//
//                    scope.setFacility = function (facility) {
//                        if (facility !== null) {
//                            scope.filterParam.facility = facility;
//                        } else {
//                            scope.filterParam.facility = null;
//                        }
//                    };
//                    scope.setSkill = function (skill) {
//                        if (skill !== null) {
//                            scope.filterParam.skill = skill;
//                        } else {
//                            scope.filterParam.skill = null;
//                        }
//                    };
                    scope.close = function () {
                        scope.showWidget = false;
                    };

//                    scope.clearFilter = function () {
//                        scope.searchVisible = true;
//                        scope.filterParam.name = null;
//                        scope.filterParam.department = {};
//                        scope.filterParam.designation = {};
//                        scope.filterParam.skill = {};
//                        scope.filterParam.facility = {};
//                    };

                    // Check employee
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
//                        if (locationcategoriesIds.length !== 0) {
//                            switch (scope.redirectTo) {
//                                case 'incompleteActions' :
//                                    break;
//
//                                case 'analytical' :
//                                    break;
//                            }
//                        }
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
            /////////////////////////////////////////////////
//            return{
//                'restrict': 'AE',
//                'templateUrl': templateRoot + '/directives/selection_widgets/location_category_multi_select.html',
//                'scope': {
//                    'onSelect': '&onSelect',
//                    'showWidget': '=',
//                    'locationDir': '=locationDir',
//                    'locationIds': '=locationIds'
//                },
//                'link': function (scope, element, attrs) {
////                    console.log("LOCATION CATEGORY link function");
//                    scope.currentOffset = 0;
//                    var lcArrayMain = [];
//                    scope.limit = paginationLimit;
//                    scope.$watchCollection('locationDir ', function (lcids) {
//                        //console.log("locationDir lcids" , lcids);
//                        if (scope.locationDir !== null && scope.locationDir !== undefined && lcids !== undefined) {
//                            var uniqueLcids = _.uniq(lcids, function (x) {
//                                return x.id;
//                            });
//                            console.log("uniqueLcids", uniqueLcids);
//                            angular.forEach(lcids, function (lcid) {
//                                lcArrayMain.push(lcid);
//                            });
//                            //console.log("lcArrayMain",lcArrayMain);
//                            refreshPage();
//                        } else {
//                            lcArrayMain = [];
//                        }
//                    });
//                    var refreshPage = function () {
//                        var category = [];
//                        scope.locationCategories = LocationCategoryService.query(function (data) {
//                            //console.log("daatataa", data);
//                            angular.forEach(data, function (lc, key) {
//                                if (key >= scope.currentOffset && key < scope.limit) {
//                                    category.push(lc);
//                                }
//                                scope.locationCategories = category;
//                            });
//                        });
//                        scope.$watch('locationDir', function (data) {
//                            // console.log("data", data);
//                            if (data === undefined) {
//                                angular.forEach(scope.locationCategories, function (locationCategory) {
//                                    locationCategory.selected = false;
//                                });
//                            }
//                        });
//                        //scope.locationCategories=locationCategoryArrayMain;
//                        scope.$watch('locationCategories', function (lcs) {
//                            //console.log("are we in watch of location IDS" , locationCategories);
//                            if (scope.locationCategories !== undefined) {
//                                angular.forEach(lcs, function (lc) {
//                                    angular.forEach(lcArrayMain, function (lcSelected) {
//                                        //console.log("lcArrayMain IIIIIIIIIII" , lcArrayMain);
//                                        //console.log("lc.id IIIIIIIIIII" , lc.id);
//                                        //console.log("lcselected " , lcSelected);
//                                        if (lc.id === lcSelected.id) {
//                                            //console.log("lc IF KE ANDAR" , lcSelected);
//                                            //console.log("lc IF KE ANDAR" , lc);
//                                            lc.selected = true;
//                                        }
//                                    });
//                                });
//                            }
//                        });
//                    };
//                    $compile();
//                    scope.nextPage = function () {
//                        console.log("main array on nect page", lcArrayMain);
//                        scope.currentOffset += paginationLimit;
//                        scope.limit += paginationLimit;
//                        refreshPage();
//                    };
//                    scope.previousPage = function () {
//                        if (scope.currentOffset <= 0) {
//                            return;
//                        }
//                        scope.currentOffset -= paginationLimit;
//                        scope.limit -= paginationLimit;
//                        refreshPage();
//                    };
//
//                    scope.select = function (lc) {
//                        //console.log("lc", lc);
//                        if (lc.selected) {
//                            //console.log("lc.selected :IF: line 95",lc);
//                            var foundLc = $filter('getById')(lcArrayMain, lc.id);
//                            //console.log("foundLc aa gaye kya", foundLc);
//                            console.log("foundLc aa gaye kya", lc.id);
//                            //console.log("lcArrayMain", lc.id);
//                            //console.log("lcArrayMain", lcArrayMain);
//
//
//                            if (foundLc === null || lc.id !== foundLc.id) {
//                                lcArrayMain.push(lc);
//                                // console.log("lcArrayMain", lc);
//                                //console.log("lcArrayMain",foundLc);
//                                // console.log("lcArrayMain", lcArrayMain);
//                            }
//                            //console.log("lcArrayMain", lc.selected);
//                            //console.log("lcArrayMain", lcArrayMain);
//
//                        } else {
//                            var foundLc = $filter('getById')(lcArrayMain, lc.id);
//                            //console.log("foundLc aa gaya kya",lcArrayMain );
//                            //console.log("foundLc remove milya kya", lc.id);
//                            // console.log("lcArrayMain", lc.id);
//                            //console.log("lcArrayMain",lcArrayMain);
//                            //console.log("lcArrayMain", foundLc);
//
//                            if (foundLc !== null && lc.id === foundLc.id) {
//                                //console.log("lcArrayMain",lc.id);
//                                //console.log("lcArrayMain", lcArrayMain);
//
//                                angular.forEach(lcArrayMain, function (lcInMainArray, index) {
//                                    if (foundLc.id === lcInMainArray.id) {
//                                        // console.log("index", index);
//                                        //console.log("lcArrayMainBefore poppin", lcArrayMain);
//                                        lcArrayMain.splice(index, 1);
//                                        // console.log("lcArrayMain ELSE", lcArrayMain);
//                                    }
//                                });
//                            }
//                        }
//                    };
//
//                    scope.done = function () {
//                        scope.showWidget = false;
//                        console.log("arrrray", _.uniq(lcArrayMain, function (x) {
//                            //console.log("x",x);
//                            return x.id;
//                        }));
//                        scope.onSelect({
//                            'locationCategories': _.uniq(lcArrayMain, function (x) {
//                                //console.log("scopelocationCategories",scope.locationCategories);
//
//                                return x.id;
//                            })
//                        });
//                    };
//                    scope.close = function () {
//                        scope.showWidget = false;
//                    };
//                    refreshPage();
//
//                    $compile(element.contents())(scope);
//
//                }
//            };
        });
//        .directive('sdLocationCategoryMultiSelect', function (SkillService, FacilityService, EmployeeService, DesignationService, DepartmentService, ActivityTransactionService, templateRoot, paginationLimit, $stateParams, $filter, $state, $rootScope) {
//            return{
//                'restrict': 'AE',
//                'templateUrl': templateRoot + '/directives/selection_widgets/location_category_multi_select.html',
//                'scope': {
//                    'onSelect': '&onSelect',
//                    'showWidget': '=',
//                    'employeesDir': '=',
//                    'endDate': '=',
//                    'startDate': '=',
//                    'activityTransactionId': '=',
//                    'projectId': '=',
//                    'reportCall': '='
//                },
//                'link': function (scope, element, attrs) {
//                    scope.filterParam = {};
//
//                    scope.searchVisible = true;
//                    scope.showPanel = function () {
//                        scope.searchVisible = false;
//                    };
//                    scope.previousPanel = function () {
//                        scope.searchVisible = true;
//                    };
//
//
//                    scope.filterParam.department = {};
//                    scope.filterParam.designation = {};
//                    scope.filterParam.skill = {};
//                    scope.filterParam.facility = {};
//                    scope.filterParameters = function (filterParam) {
//                        scope.loading = true;
//                        scope.searchVisible = false;
//                        var functionalityMeeting = '';
//                        var functionalityProject = '';
//
//                        if ($rootScope.MY_MEETINGS || $rootScope.MY_MEETINGS === true) {
//                            functionalityMeeting = 'MY_MEETINGS';
//                        } else if ($rootScope.ALL_MEETINGS || $rootScope.ALL_MEETINGS === true) {
//                            functionalityMeeting = 'ALL_MEETINGS';
//                        }
//                        if ($rootScope.ALL_PROJECTS || $rootScope.ALL_PROJECTS === true) {
//                        } else if ($rootScope.MY_PROJECTS || $rootScope.MY_PROJECTS === true) {
//                            functionalityProject = 'ALL_PROJECTS';
//                            functionalityProject = 'MY_PROJECTS';
//                        }
//
//
//                        EmployeeService.findByEmployeeFilter({
//                            'name': filterParam.name,
//                            'departmentId': filterParam.department.id,
//                            'designationId': filterParam.designation.id,
//                            'skillId': filterParam.skill.id,
//                            'facilityId': filterParam.facility.id,
//                            'functionalityProject': functionalityProject,
//                            'functionalityMeeting': functionalityMeeting,
//                            'reportCall': scope.reportCall
//                        }, function (employees) {
//                            scope.loading = true;
//                            scope.employees = employees;
//                            angular.forEach(scope.employees, function (employee) {
//                                employee.department = DepartmentService.get({
//                                    'id': employee.departmentId
//                                });
//                                employee.designation = DesignationService.get({
//                                    'id': employee.designationId
//                                });
//                            });
//                            console.log("scope.projectId ============", scope.projectId);
//                            if (!scope.reportCall || scope.reportCall == false) {
//                                ActivityTransactionService.occupiedEmployees({
//                                    'startDate': new Date(scope.startDate).getTime(),
//                                    'endDate': new Date(scope.endDate).getTime(),
//                                    'activityTransactionId': scope.activityTransactionId === undefined ? 0 : scope.activityTransactionId,
//                                    'projectId': scope.projectId === undefined ? 0 : scope.projectId
//                                }, function (occupiedEmployeesList) {
//                                    if (occupiedEmployeesList.length > 0) {
//                                        angular.forEach(occupiedEmployeesList, function (ol) {
//                                            angular.forEach(scope.employees, function (employee) {
//                                                if (ol.id === employee.id) {
//                                                    employee.occupied = true;
//                                                } else if (!employee.occupied) {
//                                                    employee.occupied = false;
//                                                }
//
//                                            });
//                                        });
//                                    } else {
//                                        console.log("occupied employees length=0");
//                                        angular.forEach(scope.employees, function (employee) {
//                                            employee.occupied = false;
//                                        });
//                                    }
//
//                                }, function (err) {
//                                    scope.loading = false;
//                                });
//                            }
//
//                            scope.loading = false;
//                        }, function (error) {
//                            scope.loading = false;
//                        }).$promise;
//
//                    };
//
//                    // search Facility typeahead
//                    scope.searchFacility = function (data) {
//                        if (data !== null) {
//                            return FacilityService.findBySearch({
//                                'name': data
//                            }).$promise;
//                        }
//                    };
//                    // search Department typeahead
//                    scope.searchDepartment = function (data) {
//                        if (data !== null) {
//                            return DepartmentService.findBySearch({
//                                'name': data
//                            }).$promise;
//                        }
//                    };
//                    // search Skill typeahead
//                    scope.searchSkill = function (data) {
//                        if (data !== null) {
//                            return SkillService.findBySearch({
//                                'name': data
//                            }).$promise;
//                        }
//                    };
//                    // search Designation typeahead
//                    scope.searchDesignation = function (data) {
//                        if (data !== null) {
//                            return DesignationService.findByDesignationSearch({
//                                'name': data
//                            }).$promise;
//                        }
//                    };
//
//                    scope.clearDesignation = function () {
//                        scope.filterParam.designation = {};
//                    };
//                    scope.clearDepartment = function () {
//                        scope.filterParam.department = {};
//
//                    };
//                    scope.clearFacility = function () {
//                        scope.filterParam.facility = {};
//                    };
//                    scope.clearSkill = function () {
//                        scope.filterParam.skill = {};
//                    };
//                    scope.setDesignation = function (designation) {
//                        if (designation !== null) {
//                            scope.filterParam.designation = designation;
//                        } else {
//                            scope.filterParamfilterParam.designation = null;
//                        }
//                    };
//
//                    scope.setDepartment = function (department) {
//                        if (department !== null) {
//                            scope.filterParam.department = department;
//                        } else {
//                            scope.filterParam.department = null;
//                        }
//                    };
//
//                    scope.setFacility = function (facility) {
//                        if (facility !== null) {
//                            scope.filterParam.facility = facility;
//                        } else {
//                            scope.filterParam.facility = null;
//                        }
//                    };
//                    scope.setSkill = function (skill) {
//                        if (skill !== null) {
//                            scope.filterParam.skill = skill;
//                        } else {
//                            scope.filterParam.skill = null;
//                        }
//                    };
//                    scope.close = function () {
//                        scope.showWidget = false;
//                    };
//
//                    scope.clearFilter = function () {
//                        scope.searchVisible = true;
//                        scope.filterParam.name = null;
//                        scope.filterParam.department = {};
//                        scope.filterParam.designation = {};
//                        scope.filterParam.skill = {};
//                        scope.filterParam.facility = {};
//                    };
//
//                    // Check employee
//                    var employeeArrayMain = [];
//                    scope.select = function (employee) {
//                        /*
//                         call the callback function
//                         */
//
//                        if (employee.selected) {
//                            var foundEmployee = $filter('getById')(employeeArrayMain, employee.id);
//                            if (foundEmployee === null || employee.id !== foundEmployee.id) {
//
//                                employeeArrayMain.push(employee);
//                            }
//                        } else {
//                            var foundEmployee = $filter('getById')(employeeArrayMain, employee.id);
//                            if (foundEmployee !== null && employee.id === foundEmployee.id) {
//                                angular.forEach(employeeArrayMain, function (employeeInMainArray, index) {
//                                    if (foundEmployee.id === employeeInMainArray.id) {
//                                        employeeArrayMain.splice(index, 1);
//                                    }
//                                });
//                            }
//                        }
//                    };
//                    // Check All Flags For Employees
//                    scope.selectAll = function (employees) {
//                        employeeArrayMain = [];
//                        employees.forEach(function (employee) {
//                            employee.selected = true;
//                            employeeArrayMain.push(employee);
//                        });
//                    };
//
//                    // Uncheck All For Employees
//                    scope.clearAll = function () {
//                        employeeArrayMain.forEach(function (employee) {
//                            scope.employees.forEach(function (emp) {
//                                if (emp.id === employee.id) {
//                                    employee.selected = false;
//                                    emp.selected = false;
//                                }
//                            });
//                        });
//                        employeeArrayMain = [];
//                    };
//
////                    scope.$watchGroup(['endDate', 'startDate'], function (plannedDates) {
////                        console.log("plannedDates-------------", plannedDates);
////
////                        if (plannedDates[0] !== undefined && plannedDates[1] !== undefined) {
////                            scope.employees = EmployeeService.findByEmployeeFilter({
////                                'name': scope.filterParam.name,
////                                'departmentId': scope.filterParam.department.id,
////                                'designationId': scope.filterParam.designation.id,
////                                'skillId': scope.filterParam.skill.id,
////                                'facilityId': scope.filterParam.facility.id
////
////                            }, function (employees) {
////                                console.log("employees----------------->>", employees);
////                            });
////
////                            ActivityTransactionService.occupiedEmployees({
////                                'startDate': new Date(plannedDates[1]).getTime(),
////                                'endDate': new Date(plannedDates[0]).getTime()
////                            }, function (occupiedEmployeesList) {
////                                console.log("occupiedEmployeesList----------[}", occupiedEmployeesList);
////                                if (occupiedEmployeesList.length > 0) {
////                                    angular.forEach(occupiedEmployeesList, function (ol) {
////                                        console.log("ol---------------------->>>", ol);
////                                        console.log("scope.employees11111------------>>>", scope.employees);
////                                        angular.forEach(scope.employees, function (employee) {
////                                            console.log("employee--------------->>>", employee);
////                                            if (ol.id === employee.id) {
////                                                console.log("employeeid-------------->>>",employee.id);
////                                                 console.log("olid-------------->>>",ol.id);
////                                                console.log("if------------------");
////                                                employee.occupied = true;
////
////                                            } else if(ol.id !== employee.id){
////                                                employee.occupied = false;
////                                                console.log("else--------------");
////                                            }
////
////                                        });
////                                    });
////                                } else {
////                                    console.log("occupied employees length=0");
////                                    angular.forEach(scope.employees, function (employee) {
////                                        employee.occupied = false;
////                                    });
////                                }
////
////                            }, function (err) {
//////                            $scope.loading = false;
////                            });
//////                        }
////
////                        }
////
////                    }, true);
//
//                    scope.setEmployees = function () {
//                        scope.showWidget = false;
//                        scope.searchVisible = true;
//                        scope.onSelect({
//                            'employees': employeeArrayMain
//                        });
//                        var employeeIds = [];
//                        angular.forEach(employeeArrayMain, function (employee) {
//                            employeeIds.push(employee.id);
//                        });
//                        if (employeeIds.length !== 0) {
//                            switch (scope.redirectTo) {
//                                case 'incompleteActions' :
//                                    break;
//
//                                case 'analytical' :
//                                    break;
//                            }
//                        }
//                    };
//                    // Keep watch on pre selected employees
//                    scope.$watch('employeesDir', function (data) {
//                        if (data) {
//                            employeeArrayMain = [];
//                            employeeArrayMain = data;
//                        } else {
//                            scope.clearAll();
//                        }
//                    });
//                    // Manipulation of checks for employees
//                    scope.employees = employeeArrayMain;
//                    scope.$watch('employees', function (employees) {
//                        if (scope.employees !== undefined) {
//                            angular.forEach(employees, function (employee) {
//                                angular.forEach(employeeArrayMain, function (employeeSelected) {
//                                    if (employee.id === employeeSelected.id) {
//                                        employee.selected = true;
//                                    }
//                                });
//                            });
//                        }
//                    });
//                }
//            };
//        });;
//
