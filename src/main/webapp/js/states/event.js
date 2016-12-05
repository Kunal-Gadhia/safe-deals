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