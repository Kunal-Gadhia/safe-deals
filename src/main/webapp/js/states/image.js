/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

angular.module("safedeals.states.image", [])
        .config(function ($stateProvider, templateRoot) {
            $stateProvider.state('admin.masters_image', {
                'url': '/image_master?offset',
                'templateUrl': templateRoot + '/masters/image/list.html',
                'controller': 'ImageListController'
            });
            $stateProvider.state('admin.masters_image.add', {
                'url': '/add',
                'templateUrl': templateRoot + '/masters/image/form.html',
                'controller': 'ImageAddController'
            });
            $stateProvider.state('admin.masters_image.edit', {
                'url': '/:imageId/edit',
                'templateUrl': templateRoot + '/masters/image/form.html',
                'controller': 'ImageEditController'
            });
            $stateProvider.state('admin.masters_image.delete', {
                'url': '/:imageId/delete',
                'templateUrl': templateRoot + '/masters/image/delete.html',
                'controller': 'ImageDeleteController'
            });
            $stateProvider.state('admin.masters_image.photo', {
                'url': '/:imageId/photo',
                'templateUrl': templateRoot + '/masters/image/photo.html',
                'controller': 'ImagePhotoController'
            });
            $stateProvider.state('admin.masters_image.view', {
                'url': '/:imageId/view',
                'templateUrl': templateRoot + '/masters/image/view.html',
                'controller': 'ImageViewController'
            });
        })
        .controller('ImageViewController', function ($scope, $stateParams, $state) {
            $scope.image = {};
            $scope.image.id = $stateParams.imageId;
            $scope.goBack = function () {
                $state.go('admin.masters_image', {}, {'reload': true});
            };
        })
        .controller('ImagePhotoController', function (restRoot, FileUploader, $scope, $stateParams, $state) {
            $scope.enableSaveButton = false;
            $scope.goBack = function () {
                $state.go('admin.masters_image', {}, {'reload': true});
            };
            var uploader = $scope.fileUploader = new FileUploader({
                url: restRoot + '/image/' + $stateParams.imageId + '/attachment',
                autoUpload: true,
                alias: 'attachment'
            });
            uploader.onBeforeUploadItem = function (item) {
                $scope.uploadInProgress = true;
                $scope.uploadSuccess = false;
                console.log("before upload item:", item);
                console.log("uploader", uploader);
            };
            uploader.onErrorItem = function ($scope) {
                $scope.uploadFailed = true;
                $scope.uploadInProgress = false;
                $scope.uploadSuccess = false;
//                    $state.go('.', {}, {'reload': true});
                console.log("upload error");
//                $scope.refreshRawMarketPrice();
            };
            uploader.onCompleteItem = function ($scope, response, status) {
                console.log("Status :%O", status);
                if (status === 200) {
                    console.log("Coming to 200 ??");
                    $scope.uploadInProgress = false;
                    $scope.uploadFailed = false;
                    $scope.uploadSuccess = true;
                    $scope.enableSaveButton = true;
                    console.log("In Progress :" + $scope.uploadInProgress);
                    console.log("Failed :" + $scope.uploadFailed);
                    console.log("Success :" + $scope.uploadSuccess);
                    console.log("Save Button :" + $scope.enableSaveButton);
                } else if (status === 500)
                {
                    $scope.uploadInProgress = false;
                    $scope.uploadFailed = false;
//                    $scope.uploadWarning = true;
                } else {
                    console.log("Coming to else??");
                    $scope.uploadInProgress = false;
                    $scope.uploadFailed = true;
                }

                console.log("upload completion", response);
            };
        })
        .controller('ImageListController', function (ImageService, ProjectService, LocationService, PropertyService, $scope, $stateParams, $state, paginationLimit) {
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

            $scope.nextImages = ImageService.query({
                'offset': $scope.nextOffset
            });

            $scope.images = ImageService.query({
                'offset': $scope.currentOffset
            }, function (imageList) {
                angular.forEach($scope.images, function (images) {
                    console.log("Images :%O", images);
                    if (images.projectId !== null) {
                        console.log("Project Id Null");
                        images.project = ProjectService.get({
                            'id': images.projectId
                        });
                    }
                    if (images.propertyId !== null) {
                        console.log("Property Id Null");
                        images.property = PropertyService.get({
                            'id': images.propertyId
                        });
                    }
                    if (images.locationId !== null) {
                        console.log("Location Id Null");
                        images.location = LocationService.get({
                            'id': images.locationId
                        });
                    }

                });
            });
            console.log("Images  111111 :%O", $scope.images);

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
        .controller('ImageAddController', function (ImageService, $scope, PropertyService, LocationService, ProjectService, $stateParams, $state, paginationLimit) {
            $scope.editableImage = {};
            $scope.setProject = function (project) {
                $scope.editableImage.projectId = project.id;
                $scope.editableImage.project = project;
            };
            $scope.searchProjects = function (searchTerm) {
                console.log("searchTerm", searchTerm);
                return ProjectService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };

            $scope.setProperty = function (property) {
                $scope.editableImage.propertyId = property.id;
                $scope.editableImage.property = property;
            };
            $scope.searchProperties = function (searchTerm) {
                console.log("searchTerm", searchTerm);
                return PropertyService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };

            $scope.setLocation = function (location) {
                $scope.editableImage.locationId = location.id;
                $scope.editableImage.location = location;
            };
            $scope.searchLocations = function (searchTerm) {
                console.log("searchTerm", searchTerm);
                return LocationService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };

            $scope.datePicker = {
                opened: false,
                toggle: function () {
                    this.opened = !this.opened;
                }
            };

            $scope.saveImage = function (image) {
                console.log("image name:", image);
                ImageService.save(image, function () {
                    $state.go('admin.masters_image', null, {'reload': true});
                });
            };
            $scope.$watch('editableImage.name', function (name) {
                console.log("Name :" + name);
                ImageService.findByName({'name': name}).$promise.catch(function (response) {
                    if (response.status === 500) {
                        $scope.editableImage.repeatName = false;
                    } else if (response.status === 404) {
                        $scope.editableImage.repeatName = false;
                    } else if (response.status === 400) {
                        $scope.editableImage.repeatName = false;
                    }
                }).then(function (image) {
                    if (image.name !== null) {
                        $scope.editableImage.repeatName = true;
                    }
                    ;
                });
            });
        })
        .controller('ImageEditController', function (ImageService, ProjectService, LocationService, PropertyService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editableImage = ImageService.get({'id': $stateParams.imageId}, function () {
                if ($scope.editableImage.projectId !== null) {
                    ProjectService.get({
                        'id': $scope.editableImage.projectId
                    }, function (project) {
                        $scope.editableImage.project = project;
                    });
                }
                if ($scope.editableImage.propertyId !== null) {
                    PropertyService.get({
                        'id': $scope.editableImage.propertyId
                    }, function (property) {
                        console.log("Property :%O", property);
                        $scope.editableImage.property = property;
                    });
                }
                if ($scope.editableImage.locationId !== null) {
                    LocationService.get({
                        'id': $scope.editableImage.locationId
                    }, function (location) {
                        console.log("location :%O", location);
                        $scope.editableImage.location = location;
                    });
                }

            });
            console.log("Ediatble Image Object :%O", $scope.editableImage);
            $scope.setProject = function (project) {
                $scope.editableImage.projectId = project.id;
                $scope.editableImage.project = project;
            };
            $scope.searchProjects = function (searchTerm) {
                console.log("searchTerm", searchTerm);
                return ProjectService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };

            $scope.setProperty = function (property) {
                $scope.editableImage.propertyId = property.id;
                $scope.editableImage.property = property;
            };
            $scope.searchProperties = function (searchTerm) {
                console.log("searchTerm", searchTerm);
                return PropertyService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };

            $scope.setLocation = function (location) {
                $scope.editableImage.locationId = location.id;
                $scope.editableImage.location = location;
            };
            $scope.searchLocations = function (searchTerm) {
                console.log("searchTerm", searchTerm);
                return LocationService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };


            $scope.datePicker = {
                opened: false,
                toggle: function () {
                    this.opened = !this.opened;
                }
            };

            $scope.saveImage = function (image) {
                image.$save(function () {
                    $state.go('admin.masters_image', null, {'reload': true});
                });
            };
        })
        .controller('ImageDeleteController', function (ImageService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editableImage = ImageService.get({'id': $stateParams.imageId});
            $scope.deleteImage = function (image) {
                image.$delete(function () {
                    $state.go('admin.masters_image', null, {'reload': true});
                });
            };
        });