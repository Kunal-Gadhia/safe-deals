angular.module("safedeals.states.ready_reckoner", [])
        .config(function ($stateProvider, templateRoot) {
            $stateProvider.state('admin.masters_ready_reckoner', {
                'url': '/ready_reckoner_master?offset',
                'templateUrl': templateRoot + '/masters/readyreckoner/list.html',
                'controller': 'ReadyReckonerListController'
            });
            $stateProvider.state('admin.masters_ready_reckoner.add', {
                'url': '/add',
                'templateUrl': templateRoot + '/masters/readyreckoner/form.html',
                'controller': 'ReadyReckonerAddController'
            });
            $stateProvider.state('admin.masters_ready_reckoner.edit', {
                'url': '/:readyReckonerId/edit',
                'templateUrl': templateRoot + '/masters/readyreckoner/form.html',
                'controller': 'ReadyReckonerEditController'
            });
            $stateProvider.state('admin.masters_ready_reckoner.delete', {
                'url': '/:readyReckonerId/delete',
                'templateUrl': templateRoot + '/masters/readyreckoner/delete.html',
                'controller': 'ReadyReckonerDeleteController'
            });
            $stateProvider.state('admin.masters_ready_reckoner.browse', {
                'url': '/attachment',
                'templateUrl': templateRoot + '/masters/readyreckoner/browse.html',
                'controller': 'ReadyReckonerBrowseController'
            });
        })
        .controller('ReadyReckonerListController', function (ReadyReckonerService, LocationService, /*BankService, LocationService, CityService,*/ $scope, $stateParams, $state, paginationLimit) {
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

            $scope.nextReadyReckoners = ReadyReckonerService.query({
                'offset': $scope.nextOffset
            });

            $scope.readyReckoners = ReadyReckonerService.query({
                'offset': $scope.currentOffset
            }
            , function () {
                angular.forEach($scope.readyReckoners, function (readyReckoner) {
                    readyReckoner.location = LocationService.get({
                        'id': readyReckoner.locationId
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
        .controller('ReadyReckonerAddController', function (ReadyReckonerService, LocationService, /*BankService, CityService, */$scope, $stateParams, $state, paginationLimit) {
            $scope.editableReadyReckoner = {};

            $scope.setLocation = function (location) {
                $scope.editableReadyReckoner.locationId = location.id;
                $scope.editableReadyReckoner.location = location;
            };

            $scope.searchLocations = function (searchTerm) {

                return LocationService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };

            $scope.saveReadyReckoner = function (readyReckoner) {

                ReadyReckonerService.save(readyReckoner, function () {
                    $state.go('admin.masters_ready_reckoner', null, {'reload': true});
                });
            };
        })
        .controller('ReadyReckonerEditController', function (ReadyReckonerService, LocationService, $scope, $stateParams, $state) {
            $scope.editableReadyReckoner = ReadyReckonerService.get({'id': $stateParams.readyReckonerId}, function () {
                $scope.editableReadyReckoner.location = LocationService.get({
                    'id': $scope.editableReadyReckoner.locationId
                });
            });
            $scope.setLocation = function (location) {
                $scope.editableReadyReckoner.locationId = location.id;
                $scope.editableReadyReckoner.location = location;
            };

            $scope.searchLocations = function (searchTerm) {

                return LocationService.findByNameLike({
                    'name': searchTerm
                }).$promise;
            };

            $scope.saveReadyReckoner = function (readyReckoner) {
                readyReckoner.$save(function () {
                    $state.go('^', {'readyReckonerId': $stateParams.readyReckonerId}, {'reload': true});
                });
            };
        })
        .controller('ReadyReckonerDeleteController', function (ReadyReckonerService, LocationService, $scope, $stateParams, $state) {
            $scope.editableReadyReckoner = ReadyReckonerService.get({'id': $stateParams.readyReckonerId}, function () {
                $scope.editableReadyReckoner.location = LocationService.get({
                    'id': $scope.editableReadyReckoner.locationId
                });
            });
            $scope.deleteReadyReckoner = function (readyReckoner) {
                readyReckoner.$delete(function () {
                    $state.go('^', null, {'reload': true});
                });
            };
        })

        .controller('ReadyReckonerBrowseController', function ($scope, $state, ReadyReckonerService, $timeout, FileUploader, restRoot) {

            var uploader = $scope.fileUploader = new FileUploader({
                url: restRoot + '/readyreckoner/attachment',
                autoUpload: true,
                alias: 'attachment'
            });

            uploader.onBeforeUploadItem = function () {
                $scope.uploadInProgress = true;

            };
            uploader.onErrorItem = function () {
                $scope.uploadFailed = true;
                $timeout(function () {
                    $scope.uploadFailed = false;
                }, 2000);
            };
            uploader.onCompleteItem = function () {
                $scope.uploadInProgress = true;
                $timeout(function () {
                    $scope.uploadInProgress = false;
                }, 2000);
            };
            $scope.saveExcelAttachment = function (fileUploader) {

                ReadyReckonerService.saveExcelData(fileUploader, function () {
                    $state.go('admin.masters_ready_reckoner', null, {'reload': true});

                });
            };
        });

			