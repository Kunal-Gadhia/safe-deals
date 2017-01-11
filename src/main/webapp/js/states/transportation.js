/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

angular.module("safedeals.states.transportation", [])
        .config(function ($stateProvider, templateRoot) {
            $stateProvider.state('admin.transportation', {
                'url': '/transportation_master?offset',
                'templateUrl': templateRoot + '/masters/transportation/list.html',
                'controller': 'TransportationListController'
            });
            $stateProvider.state('admin.transportation.add', {
                'url': '/add',
                'templateUrl': templateRoot + '/masters/transportation/form.html',
                'controller': 'TransportationAddController'
            });
            $stateProvider.state('admin.transportation.edit', {
                'url': '/:transportationId/edit',
                'templateUrl': templateRoot + '/masters/transportation/form.html',
                'controller': 'TransportationEditController'
            });
            $stateProvider.state('admin.transportation.delete', {
                'url': '/:transportationId/delete',
                'templateUrl': templateRoot + '/masters/transportation/delete.html',
                'controller': 'TransportationDeleteController'
            });
        })
        .controller('TransportationListController', function (TransportationService, $scope, $stateParams, $state, paginationLimit) {
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

            $scope.nextTransportations = TransportationService.query({
                'offset': $scope.nextOffset
            });

            $scope.transportations = TransportationService.query({
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
        .controller('TransportationAddController', function (TransportationService, $scope, $state) {
            $scope.editableTransportation = {};
            $scope.saveTransportation = function (transportation) {
                TransportationService.save(transportation, function () {
                    $state.go('admin.transportation', null, {'reload': true});
                });
            };
            $scope.$watch('editableTransportation.name', function (name) {
                TransportationService.findByName({'name': name}).$promise.catch(function (response) {
                    console.log("Response :%O", response);
                    if (response.status === 500) {
                        $scope.editableTransportation.repeatName = false;
                    } else if (response.status === 404) {
                        $scope.editableTransportation.repeatName = false;
                    } else if (response.status === 400) {
                        $scope.editableTransportation.repeatName = false;
                    }
                }).then(function (transportation) {
                    if (transportation.name !== null) {
                        $scope.editableTransportation.repeatName = true;
                    }
                    ;
                });
            });
        })
        .controller('TransportationEditController', function (TransportationService, $scope, $stateParams, $state, paginationLimit) {

            $scope.editableTransportation = TransportationService.get({'id': $stateParams.transportationId});

            $scope.saveTransportation = function (transportation) {
                console.log("Edit Object :%O", transportation);
                transportation.$save(function () {
                    $state.go('admin.transportation', null, {'reload': true});
                });
            };
        })
        .controller('TransportationDeleteController', function (TransportationService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editableTransportation = TransportationService.get({'id': $stateParams.transportationId});
            $scope.deleteTransportation = function (transportation) {
                transportation.$delete(function () {
                    $state.go('admin.transportation', null, {'reload': true});
                });
            };
        });








