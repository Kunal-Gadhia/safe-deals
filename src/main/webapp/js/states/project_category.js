/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
angular.module("safedeals.states.project_category", [])
        .config(function ($stateProvider, templateRoot) {
            $stateProvider.state('admin.project_category', {
                'url': '/project_category_master?offset',
                'templateUrl': templateRoot + '/masters/projectcategory/list.html',
                'controller': 'ProjectCategoryListController'
            });
            $stateProvider.state('admin.project_category.add', {
                'url': '/add',
                'templateUrl': templateRoot + '/masters/projectcategory/form.html',
                'controller': 'ProjectCategoryAddController'
            });
            $stateProvider.state('admin.project_category.edit', {
                'url': '/:projectCategoryId/edit',
                'templateUrl': templateRoot + '/masters/projectcategory/form.html',
                'controller': 'ProjectCategoryEditController'
            });
            $stateProvider.state('admin.project_category.delete', {
                'url': '/:projectCategoryId/delete',
                'templateUrl': templateRoot + '/masters/projectcategory/delete.html',
                'controller': 'ProjectCategoryDeleteController'
            });
        })
        .controller('ProjectCategoryListController', function (ProjectCategoryService, $scope, $stateParams, $state, paginationLimit) {
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

            $scope.nextProjectCategories = ProjectCategoryService.query({
                'offset': $scope.nextOffset
            });
//
            $scope.projectCategories = ProjectCategoryService.query({
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
        .controller('ProjectCategoryAddController', function (ProjectCategoryService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editableProjectCategory = {};
            $scope.saveProjectCategory = function (projectCategory) {
                ProjectCategoryService.save(projectCategory, function () {
                    $state.go('admin.project_category', null, {'reload': true});
                });
            };
        })
        .controller('ProjectCategoryEditController', function (ProjectCategoryService, $scope, $stateParams, $state) {
            $scope.editableProjectCategory = ProjectCategoryService.get({'id': $stateParams.projectCategoryId});
            $scope.saveProjectCategory = function (projectCategory) {
                console.log("Edit Object :%O", projectCategory);
                projectCategory.$save(function () {
                    $state.go('admin.project_category', null, {'reload': true});
                });
            };
        })
        .controller('ProjectCategoryDeleteController', function (ProjectCategoryService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editableProjectCategory = ProjectCategoryService.get({'id': $stateParams.projectCategoryId});
            $scope.deleteProjectCategory = function (projectCategory) {
                projectCategory.$delete(function () {
                    $state.go('admin.project_category', null, {'reload': true});
                });
            };
        });