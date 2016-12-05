///* 
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//
//angular.module("safedeals.states.enquiry", [])
//        .config(function ($stateProvider, templateRoot) {
//            $stateProvider.state('admin.masters_enquiry', {
//                'url': '/enquiry_master?offset',
//                'templateUrl': templateRoot + '/masters/enquiry/list.html',
//                'controller': 'EnquiryListController'
//            });
//            $stateProvider.state('admin.masters_enquiry.add', {
//                'url': '/add',
//                'templateUrl': templateRoot + '/masters/enquiry/form.html',
//                'controller': 'EnquiryAddController'
//            });
//        })
//        .controller('EnquiryListController', function (EnquiryService, $scope, $stateParams, $state, paginationLimit) {
//            if (
//                    $stateParams.offset === undefined ||
//                    isNaN($stateParams.offset) ||
//                    new Number($stateParams.offset) < 0)
//            {
//                $scope.currentOffset = 0;
//            } else {
//                $scope.currentOffset = new Number($stateParams.offset);
//            }
//
//            $scope.enquirys = EnquiryService.query({
//                'offset': $scope.currentOffset
//            });
//
//            $scope.nextPage = function () {
//                $scope.currentOffset += paginationLimit;
//                $state.go(".", {'offset': $scope.currentOffset}, {'reload': true});
//            };
//            $scope.previousPage = function () {
//                if ($scope.currentOffset <= 0) {
//                    return;
//                }
//                $scope.currentOffset -= paginationLimit;
//                $state.go(".", {'offset': $scope.currentOffset}, {'reload': true});
//            };
//
//        })
//        .controller('EnquiryAddController', function (EnquiryService, $scope, $stateParams, $state, paginationLimit) {
//            $scope.editableEnquiry = {};
//
//            $scope.datePicker = {
//                opened: false,
//                toggle: function () {
//                    this.opened = !this.opened;
//                }
//            };
//
//            $scope.saveEnquiry = function (enquiry) {
//                console.log("enquiry name:", enquiry);
//                EnquiryService.save(enquiry, function () {
//                    $state.go('admin.masters_enquiry', null, {'reload': true});
//                });
//            };
//        });
//
//
//
