angular.module("safedeals.states.property_type", [])
        .config(function ($stateProvider, templateRoot) {
            $stateProvider.state('admin.masters_property_type', {
                'url': '/property_type_master?offset',
                'templateUrl': templateRoot + '/masters/propertytype/list.html',
                'controller': 'PropertyTypeListController'
            });
            $stateProvider.state('admin.masters_property_type.add', {
                'url': '/add',
                'templateUrl': templateRoot + '/masters/propertytype/form.html',
                'controller': 'PropertyTypeAddController'
            });
            $stateProvider.state('admin.masters_property_type.edit', {
                'url': '/:propertyTypeId/edit',
                'templateUrl': templateRoot + '/masters/propertytype/form.html',
                'controller': 'PropertyTypeEditController'
            });
            $stateProvider.state('admin.masters_property_type.delete', {
                'url': '/:propertyTypeId/delete',
                'templateUrl': templateRoot + '/masters/propertytype/delete.html',
                'controller': 'PropertyTypeDeleteController'
            });
        })
        .controller('PropertyTypeListController', function (PropertyTypeService, $scope, $stateParams, $state, paginationLimit) {
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

            $scope.nextPropertyTypes = PropertyTypeService.query({
                'offset': $scope.nextOffset
            });

            $scope.propertyTypes = PropertyTypeService.query({
                'offset': $scope.currentOffset
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

        .controller('PropertyTypeAddController', function (PropertyTypeService, $scope, $stateParams, $state, paginationLimit) {

            $scope.editablePropertyType = {};

            $scope.savePropertyType = function (property_type) {

                PropertyTypeService.save(property_type, function () {
                    $state.go('admin.masters_property_type', null, {'reload': true});
                });
            };
        })

        .controller('PropertyTypeEditController', function (PropertyTypeService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editablePropertyType = PropertyTypeService.get({'id': $stateParams.propertyTypeId});

            $scope.savePropertyType = function (property_type) {

                property_type.$save(function () {
                    $state.go('admin.masters_property_type', null, {'reload': true});
                });
            };
        })

        .controller('PropertyTypeDeleteController', function (PropertyTypeService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editablePropertyType = PropertyTypeService.get({'id': $stateParams.propertyTypeId});

            $scope.deletePropertyType = function (property_type) {
                property_type.$delete(function () {
                    $state.go('admin.masters_property_type', null, {'reload': true});
                });
            };
        });
        