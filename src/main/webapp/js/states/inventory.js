angular.module("safedeals.states.inventory", [])
        .config(function ($stateProvider, templateRoot) {
            $stateProvider.state('admin.masters_inventory', {
                'url': '/inventory_master?offset',
                'templateUrl': templateRoot + '/masters/inventory/list.html',
                'controller': 'InventoryListController'
            });
            $stateProvider.state('admin.masters_inventory.add', {
                'url': '/add',
                'templateUrl': templateRoot + '/masters/inventory/form.html',
                'controller': 'InventoryAddController'
            });
            $stateProvider.state('admin.masters_inventory.edit', {
                'url': '/:inventoryId/edit',
                'templateUrl': templateRoot + '/masters/inventory/form.html',
                'controller': 'InventoryEditController'
            });
            $stateProvider.state('admin.masters_inventory.delete', {
                'url': '/:inventoryId/delete',
                'templateUrl': templateRoot + '/masters/inventory/delete.html',
                'controller': 'InventoryDeleteController'
            });
        })
        
        .controller('InventoryListController', function (InventoryService, $scope, $stateParams, $state, paginationLimit) {
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

            $scope.nextInventorys = InventoryService.query({
                'offset': $scope.nextOffset
            });

            $scope.inventorys = InventoryService.query({
                'offset': $scope.currentOffset
            }, function () {
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
        
         .controller('InventoryAddController', function (InventoryService, LocationService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editableInventory = {};

            $scope.saveInventory = function (inventory) {
                console.log("Save ??");
                InventoryService.save(inventory, function () {
                    $state.go('admin.masters_inventory', null, {'reload': true});
                });
            };
        })
        .controller('InventoryEditController', function (InventoryService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editableInventory = InventoryService.get({'id': $stateParams.inventoryId});
            
            $scope.saveInventory = function (inventory) {
                console.log("Edit ??");
                inventory.$save(function () {
                    $state.go('admin.masters_inventory', null, {'reload': true});
                });
            };
        })
        .controller('InventoryDeleteController', function (InventoryService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editableInventory = InventoryService.get({'id': $stateParams.inventoryId});
            console.log("are we here?");
            $scope.deleteInventory = function (inventory) {
                inventory.$delete(function () {
                    $state.go('admin.masters_inventory', null, {'reload': true});
                });
            };
        });


