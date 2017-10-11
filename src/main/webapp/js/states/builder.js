angular.module("safedeals.states.builder", [])
        .config(function ($stateProvider, templateRoot) {
            $stateProvider.state('admin.masters_builder', {
                'url': '/builder_master?offset',
                'templateUrl': templateRoot + '/masters/builder/list.html',
                'controller': 'BuilderListController'
            });
            $stateProvider.state('admin.masters_builder.add', {
                'url': '/add',
                'templateUrl': templateRoot + '/masters/builder/form.html',
                'controller': 'BuilderAddController'
            });
            $stateProvider.state('admin.masters_builder.edit', {
                'url': '/:builderId/edit',
                'templateUrl': templateRoot + '/masters/builder/form.html',
                'controller': 'BuilderEditController'
            });
            $stateProvider.state('admin.masters_builder.delete', {
                'url': '/:builderId/delete',
                'templateUrl': templateRoot + '/masters/builder/delete.html',
                'controller': 'BuilderDeleteController'
            });
        })
        .controller('BuilderListController', function (BuilderService, CityService, $scope, $stateParams, $state, paginationLimit) {
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

            $scope.nextBuilders = BuilderService.query({
                'offset': $scope.nextOffset
            });
            
            $scope.builders = BuilderService.query({
                'offset': $scope.currentOffset
            }
            , function (builders) {
                angular.forEach(builders, function (builder) {
                    builder.city = CityService.get({
                        'id': builder.cityId
                    });

                });
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
        .controller('BuilderAddController', function (BuilderService, CityService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editableBuilder = {};
            $scope.setCity = function (city) {
         
                $scope.editableBuilder.cityId = city.id;
                $scope.editableBuilder.city = city;
              
            };
            $scope.searchCities = function (searchTerm) {
                console.log("Search Term :%O", searchTerm);
                return CityService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };
            $scope.saveBuilder = function (builder) {
                //console.log("franchise :" + franchise);
                BuilderService.save(builder, function () {
                    $state.go('admin.masters_builder', null, {'reload': true});
                });
            };
            $scope.$watch('editableBuilder.name', function (name) {
                console.log("Name :" + name);
                BuilderService.findByName({'name': name}).$promise.catch(function (response) {
                    if (response.status === 500) {
                        $scope.editableBuilder.repeatName = false;
                    } else if (response.status === 404) {
                        $scope.editableBuilder.repeatName = false;
                    } else if (response.status === 400) {
                        $scope.editableBuilder.repeatName = false;
                    }
                }).then(function (builder) {
                    if (builder.name !== null) {
                        $scope.editableBuilder.repeatName = true;
                    }
                    ;
                });
            });
        })


        .controller('BuilderEditController', function (BuilderService, CityService, $scope, $stateParams, $state, paginationLimit) {

            $scope.editableBuilder = BuilderService.get({'id': $stateParams.builderId}, function () {
                $scope.editableBuilder.city = CityService.get({
                    id: $scope.editableBuilder.cityId
                });
                console.log("$scope.editableBuilder", $scope.editableBuilder);
            });
            $scope.setCity = function (city) {
                $scope.editableBuilder.cityId = city.id;
                $scope.editableBuilder.city = city;
            };
            $scope.searchCities = function (searchTerm) {
                console.log("Search Term :%O", searchTerm);
                return CityService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };

            $scope.saveBuilder = function (builder) {

                builder.$save(function () {
                    $state.go('admin.masters_builder', null, {'reload': true});
                });
            };
        })
        .controller('BuilderDeleteController', function (BuilderService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editableBuilder = BuilderService.get({'id': $stateParams.builderId});
            $scope.deleteBuilder = function (builder) {
                builder.$delete(function () {
                    $state.go('admin.masters_builder', null, {'reload': true});
                });
            };
        }); 









