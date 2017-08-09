angular.module("safedeals.states.franchise_portal", [])
        .config(function ($stateProvider, templateRoot) {
            $stateProvider
                    .state('admin.franchise_portal', {
                        'url': '/franchise_portal/:userId/:companyName',
                        'templateUrl': templateRoot + '/franchise/franchise_menu.html',
                        'controller': 'FranchisePortalController'
                    });
        })

        .controller('FranchisePortalController', function ($scope, $stateParams, FranchisePortalService, UserService) {
            console.log("Hello Into Franchise Portal");
            console.log("Are we getting stateparams here?? :%O", $stateParams);

            $scope.franchisePortals = FranchisePortalService.query({
                'offset': $scope.currentOffset
            }
            , function () {
                angular.forEach($scope.franchisePortals, function (franchisePortal) {
                    franchisePortal.user = UserService.get({
                        'id': franchisePortal.userId,
                        'companyName': franchisePortal.nameOfCompany
                    });
                });
            });
        });