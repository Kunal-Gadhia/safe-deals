/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
angular.module("safedeals.states.testimonial", [])
        .config(function ($stateProvider, templateRoot) {
            $stateProvider
                    .state('admin.testimonial', {
                        'url': '/testimonial?offset',
                        'templateUrl': templateRoot + '/masters/testimonial/list.html',
                        'controller': 'TestimonialListController'
                    })
                    .state('admin.testimonial.add', {
                        'url': '/add',
                        'templateUrl': templateRoot + '/masters/testimonial/form.html',
                        'controller': 'TestimonialAddController'
                    })
                    .state('admin.testimonial.edit', {
                        'url': '/:testimonialId/edit',
                        'templateUrl': templateRoot + '/masters/testimonial/form.html',
                        'controller': 'TestimonialEditController'
                    })
                    .state('admin.testimonial.delete', {
                        'url': '/:testimonialId/delete',
                        'templateUrl': templateRoot + '/masters/testimonial/delete.html',
                        'controller': 'TestimonialDeleteController'
                    })
                    .state('admin.testimonial.photo', {
                        'url': '/:testimonialId/photo',
                        'templateUrl': templateRoot + '/masters/testimonial/photo.html',
                        'controller': 'TestimonialPhotoController'
                    })
                    .state('admin.testimonial.view', {
                        'url': '/:testimonialId/view',
                        'templateUrl': templateRoot + '/masters/testimonial/view.html',
                        'controller': 'TestimonialViewController'
                    });
        })
        .controller('TestimonialViewController', function (restRoot, FileUploader, TestimonialService, $scope, $stateParams, $state, paginationLimit, $timeout) {

            $scope.testimonial = {};
            $scope.testimonial.id = $stateParams.testimonialId;
            $scope.goBack = function () {
                $state.go('admin.testimonial', {}, {'reload': true});
            };

            console.log("THIS IS TESTIMONIAL ID", $stateParams.testimonialId);
        })
        .controller('TestimonialPhotoController', function (restRoot, FileUploader, TestimonialService, $scope, $stateParams, $state, paginationLimit, $timeout) {
            $scope.enableSaveButton = true;
            $scope.goBack = function () {
                $state.go('admin.testimonial', {}, {'reload': true});
            };
            var uploader = $scope.fileUploader = new FileUploader({
                url: restRoot + '/testimonial/' + $stateParams.testimonialId + '/attachment',
                autoUpload: true,
                alias: 'attachment'
            });
            uploader.onBeforeUploadItem = function (item) {
                $scope.uploadInProgress = true;
                $scope.uploadSuccess = false;

            };
            uploader.onErrorItem = function (fileItem, response, status, headers) {
                $scope.uploadFailed = true;
                $scope.uploadInProgress = false;
                $scope.uploadSuccess = false;

            };
            uploader.onCompleteItem = function (fileItem, response, status, headers) {
                if (status === 200) {
                    $scope.uploadInProgress = false;
                    $scope.uploadFailed = false;
                    $scope.uploadSuccess = true;
                    $scope.enableSaveButton = false;
                } else if (status === 500)
                {
                    $scope.uploadInProgress = false;
                    $scope.uploadFailed = false;

                } else {
                    $scope.uploadInProgress = false;
                    $scope.uploadFailed = true;
                }

            };
        })
        .controller('TestimonialListController', function (TestimonialService, $scope, $stateParams, $state, paginationLimit) {

            $scope.length = false;
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

            $scope.nextTestimonials = TestimonialService.query({
                'offset': $scope.nextOffset
            });

            TestimonialService.findAll({
                'offset': $scope.currentOffset
            }, function (data) {
                $scope.testimonials = data;
                if (data.length !== 5) {

                    $scope.length = true;
                }
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
        .controller('TestimonialAddController', function (restRoot, TestimonialService, $scope, $stateParams, $state, $timeout, FileUploader) {
            $scope.editableTestimonial = {};
            $scope.saveTestimonial = function (data) {
                TestimonialService.save(data, function (data) {

                    $state.go('admin.testimonial', {}, {'reload': true});
                });
            };
            $scope.$watch('editableTestimonial.name', function (name) {

                TestimonialService.findByName({'name': name}).$promise.catch(function (response) {
                    if (response.status === 500) {
                        $scope.editableTestimonial.repeatName = false;
                    }
                    else if (response.status === 404) {
                        $scope.editableTestimonial.repeatName = false;
                    }
                    else if (response.status === 400) {
                        $scope.editableTestimonial.repeatName = false;
                    }
                }).then(function (account) {
                    if (account.name !== null) {
                        $scope.editableTestimonial.repeatName = true;
                    }
                    ;
                });
            });
        })
        .controller('TestimonialEditController', function (TestimonialService, $scope, $stateParams, $state) {
            $scope.editableTestimonial = {};
            TestimonialService.findById({'id': $stateParams.testimonialId}, function (data) {
                $scope.editableTestimonial = data;
            });

            $scope.saveTestimonial = function (data) {
                TestimonialService.save(data, function (data) {
                    $state.go('admin.testimonial', {}, {'reload': true});
                });
            };

        })
        .controller('TestimonialDeleteController', function (TestimonialService, $scope, $stateParams, $state) {
            $scope.editableTestimonial = {};
            TestimonialService.findById({'id': $stateParams.testimonialId}, function (data) {
                $scope.editableTestimonial = data;
            });

            $scope.deleteTestimonial = function (data) {
                TestimonialService.delete($scope.editableTestimonial, function (data) {
                    $state.go('admin.testimonial', {}, {'reload': true});
                });
            };
        });
