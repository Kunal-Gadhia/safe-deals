angular.module("safedeals.states.bank_portal", [])
        .config(function ($stateProvider, templateRoot) {
            $stateProvider
                    .state('admin.bank_portal', {
                        'url': '/bank_portal/:userId/:companyName',
                        'templateUrl': templateRoot + '/bank/bank_menu.html',
                        'controller': 'BankPortalController'
                    });
        })

        .controller('BankPortalController', function ($scope, $stateParams, BankPortalService, UserService) {
            console.log("Hello Into bank Portal");
            console.log("Are we getting stateparams here?? :%O", $stateParams);

            $scope.bankPortals = BankPortalService.query({
                'offset': $scope.currentOffset
            }
            , function () {
                angular.forEach($scope.bankPortals, function (bankPortal) {
                    bankPortal.user = UserService.get({
                        'id': bankPortal.userId,
                        'companyName': bankPortal.nameOfCompany
                    });
                });
            });
        });