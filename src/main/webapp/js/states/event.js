angular.module("safedeals.states.event", [])
        .config(function ($stateProvider, templateRoot) {
            $stateProvider.state('admin.masters_event', {
                'url': '/event_master?offset',
                'templateUrl': templateRoot + '/masters/event/list.html',
                'controller': 'EventListController'
            });
            $stateProvider.state('admin.masters_event.add', {
                'url': '/add',
                'templateUrl': templateRoot + '/masters/event/form.html',
                'controller': 'EventAddController'
            });
            $stateProvider.state('admin.masters_event.edit', {
                'url': '/:eventId/edit',
                'templateUrl': templateRoot + '/masters/event/form.html',
                'controller': 'EventEditController'
            });
            $stateProvider.state('admin.masters_event.delete', {
                'url': '/:eventId/delete',
                'templateUrl': templateRoot + '/masters/event/delete.html',
                'controller': 'EventDeleteController'
            });
            $stateProvider.state('admin.masters_event.photo', {
                'url': '/:eventId/photo',
                'templateUrl': templateRoot + '/masters/event/photo.html',
                'controller': 'EventPhotoController'
            });
            $stateProvider.state('admin.masters_event.view', {
                'url': '/:eventId/view',
                'templateUrl': templateRoot + '/masters/event/view.html',
                'controller': 'EventViewController'
            });
        })
        .controller('EventViewController', function ($scope, $stateParams, $state) {
            $scope.event = {};
            $scope.event.id = $stateParams.eventId;
            $scope.goBack = function () {
                $state.go('admin.masters_event', {}, {'reload': true});
            };
        })
        .controller('EventPhotoController', function (restRoot, FileUploader, $scope, $stateParams, $state) {
            $scope.enableSaveButton = false;
            $scope.goBack = function () {
                $state.go('admin.masters_event', {}, {'reload': true});
            };
            var uploader = $scope.fileUploader = new FileUploader({
                url: restRoot + '/event/' + $stateParams.eventId + '/attachment',
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
        .controller('EventListController', function (EventService, $scope, $stateParams, $state, paginationLimit) {
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

            $scope.nextEvents = EventService.query({
                'offset': $scope.nextOffset
            });

            $scope.events = EventService.query({
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
        .controller('EventAddController', function (EventService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editableEvent = {};

            $scope.datePicker = {
                opened: false,
                toggle: function () {
                    this.opened = !this.opened;
                }
            };

            $scope.saveEvent = function (event) {
                console.log("event name:", event);
                EventService.save(event, function () {
                    $state.go('admin.masters_event', null, {'reload': true});
                });
            };
            $scope.$watch('editableEvent.name', function (name) {
                console.log("Name :" + name);
                EventService.findByName({'name': name}).$promise.catch(function (response) {
                    if (response.status === 500) {
                        $scope.editableEvent.repeatName = false;
                    } else if (response.status === 404) {
                        $scope.editableEvent.repeatName = false;
                    } else if (response.status === 400) {
                        $scope.editableEvent.repeatName = false;
                    }
                }).then(function (event) {
                    if (event.name !== null) {
                        $scope.editableEvent.repeatName = true;
                    }
                    ;
                });
            });
        })
        .controller('EventEditController', function (EventService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editableEvent = EventService.get({'id': $stateParams.eventId});

            $scope.datePicker = {
                opened: false,
                toggle: function () {
                    this.opened = !this.opened;
                }
            };

            $scope.saveEvent = function (event) {
                event.$save(function () {
                    $state.go('admin.masters_event', null, {'reload': true});
                });
            };
        })
        .controller('EventDeleteController', function (EventService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editableEvent = EventService.get({'id': $stateParams.eventId});
            $scope.deleteEvent = function (event) {
                event.$delete(function () {
                    $state.go('admin.masters_event', null, {'reload': true});
                });
            };
        });