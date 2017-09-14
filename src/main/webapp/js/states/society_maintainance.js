angular.module("safedeals.states.society_maintainance", [])
        .config(function ($stateProvider, templateRoot) {
            $stateProvider.state('admin.masters_society_maintainance', {
                'url': '/society_maintainance_master?offset',
                'templateUrl': templateRoot + '/masters/societymaintainance/list.html',
                'controller': 'SocietyMaintainanceListController'
            });
            $stateProvider.state('admin.masters_society_maintainance.add', {
                'url': '/add',
                'templateUrl': templateRoot + '/masters/societymaintainance/form.html',
                'controller': 'SocietyMaintainanceAddController'
            });
            $stateProvider.state('admin.masters_society_maintainance.edit', {
                'url': '/:societyMaintainanceId/edit',
                'templateUrl': templateRoot + '/masters/societymaintainance/form.html',
                'controller': 'SocietyMaintainanceEditController'
            });
            $stateProvider.state('admin.masters_society_maintainance.delete', {
                'url': '/:societyMaintainanceId/delete',
                'templateUrl': templateRoot + '/masters/societymaintainance/delete.html',
                'controller': 'SocietyMaintainanceDeleteController'
            });
        })
        .controller('SocietyMaintainanceListController', function (SocietyMaintainanceService, $scope, $stateParams, $state, paginationLimit) {
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

            $scope.nextSocietyMaintainances = SocietyMaintainanceService.query({
                'offset': $scope.nextOffset
            });

            $scope.societyMaintainances = SocietyMaintainanceService.query({
                'offset': $scope.currentOffset
            }, function (s) {
                console.log("SocietyMaintainanceService ", s);
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
        .controller('SocietyMaintainanceAddController', function (SocietyMaintainanceService, $scope, $stateParams, $state, paginationLimit) {

            $scope.editableSocietyMaintainance = {};
            $scope.saveSocietyMaintainance = function (society_maintainance) {
                console.log("society_maintainance", society_maintainance);
                SocietyMaintainanceService.save(society_maintainance, function () {
                    $state.go('admin.masters_society_maintainance', null, {'reload': true});
                });
            };

        })
        .controller('SocietyMaintainanceEditController', function (SocietyMaintainanceService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editableSocietyMaintainance = SocietyMaintainanceService.get({'id': $stateParams.societyMaintainanceId});

            $scope.saveSocietyMaintainance = function (society_maintainance) {
                society_maintainance.$save(function () {
                    $state.go('admin.masters_society_maintainance', null, {'reload': true});
                });
            };
        })
        .controller('SocietyMaintainanceDeleteController', function (SocietyMaintainanceService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editableSocietyMaintainance = SocietyMaintainanceService.get({'id': $stateParams.societyMaintainanceId});
            console.log("are we here?");
            $scope.deleteSocietyMaintainance = function (society_maintainance) {
                society_maintainance.$delete(function () {
                    $state.go('admin.masters_society_maintainance', null, {'reload': true});
                });
            };
        });
        