/* 
 * To change this license header, choose License Headers in Property Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
angular.module("safedeals.states.property_category", [])
        .config(function ($stateProvider, templateRoot) {
            $stateProvider.state('admin.property_category', {
                'url': '/property_category_master?offset',
                'templateUrl': templateRoot + '/masters/propertycategory/list.html',
                'controller': 'PropertyCategoryListController'
            });
            $stateProvider.state('admin.property_category.add', {
                'url': '/add',
                'templateUrl': templateRoot + '/masters/propertycategory/form.html',
                'controller': 'PropertyCategoryAddController'
            });
            $stateProvider.state('admin.property_category.edit', {
                'url': '/:propertyCategoryId/edit',
                'templateUrl': templateRoot + '/masters/propertycategory/form.html',
                'controller': 'PropertyCategoryEditController'
            });
            $stateProvider.state('admin.property_category.delete', {
                'url': '/:propertyCategoryId/delete',
                'templateUrl': templateRoot + '/masters/propertycategory/delete.html',
                'controller': 'PropertyCategoryDeleteController'
            });
        })
        .controller('PropertyCategoryListController', function (PropertyCategoryService, $scope, $stateParams, $state, paginationLimit) {
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

            $scope.nextPropertyCategories = PropertyCategoryService.query({
                'offset': $scope.nextOffset
            });
//
            $scope.propertyCategories = PropertyCategoryService.query({
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
        .controller('PropertyCategoryAddController', function (PropertyCategoryService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editablePropertyCategory = {};
            $scope.savePropertyCategory = function (propertyCategory) {
                PropertyCategoryService.save(propertyCategory, function () {
                    $state.go('admin.property_category', null, {'reload': true});
                });
            };
        })
        .controller('PropertyCategoryEditController', function (PropertyCategoryService, $scope, $stateParams, $state) {
            $scope.editablePropertyCategory = PropertyCategoryService.get({'id': $stateParams.propertyCategoryId});
            $scope.savePropertyCategory = function (propertyCategory) {
                console.log("Edit Object :%O", propertyCategory);
                propertyCategory.$save(function () {
                    $state.go('admin.property_category', null, {'reload': true});
                });
            };
        })
        .controller('PropertyCategoryDeleteController', function (PropertyCategoryService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editablePropertyCategory = PropertyCategoryService.get({'id': $stateParams.propertyCategoryId});
            $scope.deletePropertyCategory = function (propertyCategory) {
                propertyCategory.$delete(function () {
                    $state.go('admin.property_category', null, {'reload': true});
                });
            };
        });